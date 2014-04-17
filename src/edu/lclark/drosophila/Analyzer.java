package edu.lclark.drosophila;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import com.xuggle.xuggler.IContainer;

public class Analyzer {

	private int sizeThreshold;

	private static AnalyzerGui gui;

	private int contrastThreshold = 200;

	public static void main(String[] args) {
		gui = new AnalyzerGui(new Analyzer());
		gui.run();
	}

	/**
	 * The List of Fly objects in which fly data is stored.
	 */
	private List<Fly> flies;

	/**
	 * Boolean for if the image snap listener should just find the first frame
	 * of the movie for option setting purposes
	 */
	private boolean loadingMovie;

	/**
	 * The total number of frames in the movie being analyzed. Or, the number of
	 * images being analyzed.
	 */
	private int totalFrames;

	/**
	 * True if the currently loaded file is a movie. Otherwise, it is false.
	 * <p>
	 * This is used so that the proper constructor is called for making a new
	 * Fly at the end of Flydentify.
	 */
	private boolean movieLoaded;

	/**
	 * Stores all of the images being analyzed.
	 */
	private File[] images;

	/**
	 * The name of the movie file that we have loaded
	 */
	private File movieFile;

	/**
	 * The first frame in the movie, for displaying
	 */
	private BufferedImage firstMovieFrame;

	/**
	 * The number of seconds between frames in a movie file
	 */
	private double SECONDS_BETWEEN_FRAMES;

	/**
	 * The number of frames per second in a movie file.
	 */
	private double framesPerSecond;

	/**
	 * The number of microseconds between frames in a movie file
	 */
	private long MICRO_SECONDS_BETWEEN_FRAMES;

	// Time of last frame write
	// PTS means ... picture time stamp?
	private long mLastPtsWrite;

	/**
	 * The List of frames in a movie
	 */
	private List<BufferedImage> frames;

	/**
	 * A value for editing the contrast of the actual image
	 */
	private double imageContrast = 1.0;


	private double duration;

	private int sampleRate;


	public Analyzer() {
		movieLoaded = false;
		totalFrames = 0;
		flies = new LinkedList<Fly>();
		frames = new ArrayList<BufferedImage>();
		images = new File[20];
	}

	public void flydentify(BufferedImage image) {
		// just for a single image
		flies = new LinkedList<Fly>();
		totalFrames = 1;
		flydentify(image, 0);

		System.out.println("number of flies: " + flies.size());
		images = new File[20];

		mLastPtsWrite = Global.NO_PTS;
	}

	/**
	 * this method calls the above method on an array of flies and computes the
	 * average
	 * 
	 * @param flies
	 * @param start
	 * @param end
	 * @return average velocity of flies from start to end
	 */

	public double[] averageVelMultFlies(List<Fly> flies, int start, int end) {
		double[] avgVel = new double[end - start];
		double tempAvg;
		for (int i = start; i < end; i++) {
			tempAvg = 0;
			for (int j = 0; j < flies.size(); j++) {
				tempAvg += flies.get(j).averageVelFly(i, i);
			}
			avgVel[i - start] = tempAvg / flies.size();
		}

		return avgVel;
	}

	/**
	 * Removes the currently attached images and fly data from this Analyzer.
	 */
	public void clearImages() {
		movieLoaded = false;
		totalFrames = 0;
		flies = new LinkedList<Fly>();
		images = new File[20];
		loadingMovie = false;
	}

	/**
	 * Checks to see if a boolean array of any size contains a false value.
	 * <p>
	 * Used by {@link #flydentify} to check if any flies have not been assigned
	 * to flies on other frames.
	 * 
	 * @param array
	 *            The boolean array being searched for any false values. 
	 * @return True if there are any false values in the array.
	 */
	public boolean containsFalse(boolean[] array) {
		for (boolean b : array) {
			if (!b) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Identifies any flies within the given image and adds to the information
	 * within the flies List for the given frame. In order to retrieve
	 * information, use the flies List in this Analyzer, through getFlies().
	 * 
	 * @param image
	 *            the image which is being analyzed.
	 * @param frameNumber
	 *            which frame out of all frames the given image is.
	 */
	public void flydentify(BufferedImage image, int frameNumber) {
		if (frameNumber == 0) {
			flies = new LinkedList<Fly>();
		}
		int imgHeight = image.getHeight();
		int imgWidth = image.getWidth();
		// a temporary array of the flies found in this image. It just stores
		// their x and y
		LinkedList<double[]> tempFlies = new LinkedList<double[]>();
		// an array of which pixels have been searched
		boolean searchArray[][] = new boolean[imgWidth][imgHeight];
		int stack[][] = new int[imgWidth * imgHeight][2];
		int curIdx;
		for (int i = 0; i < imgWidth; i++) {
			for (int j = 0; j < imgHeight; j++) {
				if (!searchArray[i][j]) { // if pixel hasn't been searched
					// find gray scale value of the pixel. TODO we aren't
					// completely sure if this works
					int rgb = image.getRGB(i, j);
					int red = (rgb >> 16) & 0xFF;
					int green = (rgb >> 8) & 0xFF;
					int blue = rgb & 0xFF;
					red *= imageContrast;
					green *= imageContrast;
					blue *= imageContrast;
					if (red > 255) {
						red = 255;
					}
					if (green > 255) {
						green = 255;
					}
					if (blue > 255) {
						blue = 255;
					}
					double avg = red * 0.2989 + green * .587 + blue * .114;
					if ((int) (Math.round(avg)) <= contrastThreshold) { // if
																		// the
																		// color
																		// is
																		// dark
																		// enough
						int totalX = 0;
						int totalY = 0;
						int numPixels = 0;
						// initialize values to find the center of mass of the
						// fly.
						// searchPixel(i, j, searchArray, image);
						curIdx = 0;
						stack[curIdx][0] = i;
						stack[curIdx][1] = j;
						curIdx++;
						while (curIdx > 0) {
							curIdx--;
							int tempx = stack[curIdx][0];
							int tempy = stack[curIdx][1];
							searchArray[tempx][tempy] = true;
							totalX += tempx;
							totalY += tempy;
							numPixels++;
							if ((tempx > 0) && !searchArray[tempx - 1][tempy]) {
								if (isDarkEnough(image.getRGB(tempx - 1, tempy))) {
									stack[curIdx][0] = tempx - 1;
									stack[curIdx][1] = tempy;
									curIdx++;
								}
							}
							if ((tempy > 0) && !searchArray[tempx][tempy - 1]) {
								if (isDarkEnough(image.getRGB(tempx, tempy - 1))) {
									stack[curIdx][0] = tempx;
									stack[curIdx][1] = tempy - 1;
									curIdx++;
								}
							}
							if ((tempx < imgWidth - 1)
									&& !searchArray[tempx + 1][tempy]) {
								if (isDarkEnough(image.getRGB(tempx + 1, tempy))) {
									stack[curIdx][0] = tempx + 1;
									stack[curIdx][1] = tempy;
									curIdx++;
								}
							}
							if ((tempy < imgHeight - 1)
									&& !searchArray[tempx][tempy + 1]) {
								if (isDarkEnough(image.getRGB(tempx, tempy + 1))) {
									stack[curIdx][0] = tempx;
									stack[curIdx][1] = tempy + 1;
									curIdx++;
								}
							}
						}

						if (numPixels >= sizeThreshold) {
							// if the blob is large enough to be a fly

							// create a new temporary fly object
							double tempLocation[] = new double[2];
							tempLocation[0] = (double) totalX / numPixels;
							tempLocation[1] = (double) totalY / numPixels;
							tempFlies.add(tempLocation);
							// System.out.println("size: " + numPixels);
						}
					} else {
						// we searched this already!
						searchArray[i][j] = true;
					}
				}
			}
		}
		if (frameNumber == 0) {
			// The first frame just creates all found flies.
			for (double[] d : tempFlies) {
				Fly f;
				if (movieLoaded) {
					f = new Fly(totalFrames);
				} else {
					f = new Fly();
				}
				f.addFrameInfo(frameNumber, d[0], d[1]);
				f.setId(flies.size());
				flies.add(f);

			}
		} else {
			// If not the first frame, we do the checking algorithms.
			// (Algorithm sounds too proper for the duct-taped together thing
			// that this is.)
			Fly[] fullPrevFlies = new Fly[flies.size()];
			int index = 0;
			boolean[] prevFliesMarked = new boolean[flies.size()];
			for (Fly fly : flies) {
				fullPrevFlies[index] = fly;
				index++;
			}
			// Stores coordinates in indices 0 and 1 of array, stores id in
			// index 2
			double[][] fullTempFlies = new double[tempFlies.size()][3];
			boolean[] tempFliesMarked = new boolean[tempFlies.size()];
			for (int i = 0; i < fullTempFlies.length; i++) {
				fullTempFlies[i][0] = tempFlies.get(i)[0];
				fullTempFlies[i][1] = tempFlies.get(i)[1];
				fullTempFlies[i][2] = i;
			}
			List<double[]> newFlies = new LinkedList<double[]>();
			while (containsFalse(tempFliesMarked)
					|| containsFalse(prevFliesMarked)) {
				// Searches through the temp flies list to connect the closest
				// fly.
				if (containsFalse(tempFliesMarked)) {
					for (int i = 0; i < tempFliesMarked.length; i++) {
						if (!tempFliesMarked[i]) {
							double dist = Double.MAX_VALUE;
							int closestFlyIndex = -1;
							double currentX = fullTempFlies[i][0];
							double currentY = fullTempFlies[i][1];
							for (int j = 0; j < fullPrevFlies.length; j++) {
								double thisDist = Math
										.sqrt(Math.pow(
												currentX
														- fullPrevFlies[j]
																.getX(frameNumber - 1),
												2)
												+ Math.pow(
														currentY
																- fullPrevFlies[j]
																		.getY(frameNumber - 1),
														2));
								if (thisDist < dist) {
									dist = thisDist;
									closestFlyIndex = j;
								}
							}
							if (!prevFliesMarked[closestFlyIndex]) {
								prevFliesMarked[closestFlyIndex] = true;
								tempFliesMarked[i] = true;
								for (Fly f : flies) {
									if (f.getId() == closestFlyIndex) {
										f.addFrameInfo(frameNumber, currentX,
												currentY);
									}
								}
							} else if (!containsFalse(prevFliesMarked)) {
								// If No previous flies are found:
								newFlies.add(new double[] { currentX, currentY,
										closestFlyIndex });
								tempFliesMarked[i] = true;
							}
						}
					}
				}
				// Search through the previous flies for the closest current
				// flies.
				if (containsFalse(prevFliesMarked)) {
					for (int i = 0; i < fullPrevFlies.length; i++) {
						if (!prevFliesMarked[i]) {
							double dist = Double.MAX_VALUE;
							int closestFlyIndex = -1;
							double pastX = fullPrevFlies[i]
									.getX(frameNumber - 1);
							double pastY = fullPrevFlies[i]
									.getY(frameNumber - 1);
							if (!containsFalse(tempFliesMarked)) {
								for (int j = 0; j < fullTempFlies.length; j++) {
									double thisDist = Math.sqrt(Math.pow(pastX
											- fullTempFlies[j][0], 2)
											+ Math.pow(pastY
													- fullTempFlies[j][1], 2));
									if (thisDist < dist) {
										dist = thisDist;
										closestFlyIndex = j;
									}
								}
							} else {
								for (int j = 0; j < fullTempFlies.length; j++) {
									if (!tempFliesMarked[j]) {
										double thisDist = Math.sqrt(Math.pow(
												pastX - fullTempFlies[j][0], 2)
												+ Math.pow(pastY
														- fullTempFlies[j][1],
														2));
										if (thisDist < dist) {
											dist = thisDist;
											closestFlyIndex = j;
										}
									}
								}
							}
							prevFliesMarked[i] = true;
							tempFliesMarked[closestFlyIndex] = true;
							for (Fly f : flies) {
								if (f.getId() == i) {
									f.addFrameInfo(frameNumber,
											fullTempFlies[closestFlyIndex][0],
											fullTempFlies[closestFlyIndex][1]);
								}
							}
						}
					}
				}
			}
			for (double[] d : newFlies) {
				Fly aNewFly = flies.get((int) d[2]).copyThisFly();
				aNewFly.addFrameInfo(frameNumber, d[0], d[1]);
				aNewFly.setId(flies.size());
				flies.add(aNewFly);
			}
		}

	}

	/**
	 * Adds to the list of Fly objects with their information gathered from the
	 * single given image. Also, adds the given file to the list of files stored
	 * within this Analyzer. In order to retrieve information, use the flies
	 * List in this Analyzer, through getFlies().
	 * 
	 * @param file
	 *            the file containing the image which is being analyzed.
	 */
	public void flydentify(File file) {
		try {
			images[totalFrames] = file;
			totalFrames++;
			BufferedImage image = ImageIO.read(file);
			flydentify(image, totalFrames - 1);
		} catch (IOException e) {
			System.err.println("EVERYTHING IS HORRIBLE");
			e.printStackTrace();
		}
	}

	/**
	 * This is the method called when analyzing a movie. IT IS INCOMPLETE.
	 * <p>
	 * This is just to show the format of how flydentifyMovie should work.
	 * MovieLoaded needs to be set to true for flydentify to call the proper Fly
	 * constructor. TotalFrames should be set to the total number of frames
	 * within the movie. Afterwards, call flydentify on all frames to set up fly
	 * data.
	 */
	public void flydentifyMovie() {
		movieLoaded = true;
		totalFrames = 0;
		images = new File[totalFrames];
	}

	/**
	 * Gives the List of Fly objects, which store any information gained from
	 * analyzed images.
	 * 
	 * @return the List of Fly objects, which contain information for individual
	 *         flies in the analyzed image or movie.
	 */
	public List<Fly> getFlies() {
		return flies;
	}

	/**
	 * Gives a file containing an image from the array of images in this
	 * Analyzer.
	 * 
	 * @param index
	 * @return
	 */
	public File getImage(int index) {
		if (!(index < 0) && index < images.length) {
			return images[index];
		}
		return null;
	}

	/**
	 * Getter for the total number of frames or images which have been processed
	 * by this Analyzer.
	 * 
	 * @return the total number of frames or images which have been processed.
	 */
	public int getTotalFrames() {
		return totalFrames;
	}

	/**
	 * Checks if the rgb value given is dark enough to be identified as a fly.
	 * 
	 * @param rgb
	 *            the integer rgb value to be judged if dark enough.
	 * @return true if the rgb value is dark enough, false if the rgb value is
	 *         too light.
	 */
	public boolean isDarkEnough(int rgb) {
		int red;
		int green;
		int blue;
		double avg;
		red = (rgb >> 16) & 0xFF;
		green = (rgb >> 8) & 0xFF;
		blue = rgb & 0xFF;
		red *= imageContrast;
		green *= imageContrast;
		blue *= imageContrast;
		if (red > 255) {
			red = 255;
		}
		if (green > 255) {
			green = 255;
		}
		if (blue > 255) {
			blue = 255;
		}
		avg = red * 0.2989 + green * .587 + blue * .114;
		boolean found = ((int) (Math.round(avg)) <= contrastThreshold);
		return found;
	}

	/**
	 * Updates the size threshold field. This is used to determine if an object
	 * identified within an image is large enough to be considered a fly. This
	 * will also analyze all stored images again.
	 * 
	 * @param input
	 *            the value which size threshold will be set to.
	 */
	public void sizeThresholdUpdate(int input) {
		sizeThreshold = input;
		if (totalFrames > 0) {
			updateImages();
		}
	}

	/**
	 * Updates the contrast threshold field. This is used to tell how dark a
	 * spot has to be to be considered a fly. This will also analyze all stored
	 * image again
	 * 
	 * @param input
	 *            the value which contrast threshold will be set to.
	 */
	public void contrastThresholdUpdate(int input) {
		if (input > 255) {
			input = 255;
		}
		if (input < 0) {
			input = 0;
		}
		if (input >= 0 && input <= 255) {
			contrastThreshold = input;
			if (totalFrames > 0) {
				updateImages();
			}
		}
	}

	/**
	 * This runs flydentify on all stored images.
	 */
	public void updateImages() {
		if (movieLoaded) {
			flydentify(firstMovieFrame, 0);
		} else {
			try {
				for (int i = 0; i < totalFrames; i++) {
					// TODO this should probably create a new flies List, since
					// it
					// is re running flydentify on all images.
					BufferedImage image = ImageIO.read(images[i]);
					flydentify(image, i);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public int getFramesInMovie(String filename) {
		IContainer container = IContainer.make();
		if (container.open(filename, IContainer.Type.READ, null) < 0) {
			throw new IllegalArgumentException("Could not open file:"
					+ filename);
		}
		duration = container.getDuration()/1000000.0;
		framesPerSecond = container.getStream(0).getFrameRate()
				.getDouble();
		container.close();
		return (int) (duration * framesPerSecond);
	}

	/**
	 * Plays a selected movie in a new moviePanel
	 * 
	 * @param file
	 */
	public void openMovie(File file) {
		clearImages();
		mLastPtsWrite = Global.NO_PTS;
		movieLoaded = true;
		movieFile = file;
		firstMovieFrame=null;
		IMediaReader mediaReader = ToolFactory.makeReader(file
				.getAbsolutePath());
		// stipulate that we want BufferedImages created in BGR 24bit color
		// space
		mediaReader
				.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		totalFrames = getFramesInMovie(file.getAbsolutePath());
		SECONDS_BETWEEN_FRAMES = duration/totalFrames;
		MICRO_SECONDS_BETWEEN_FRAMES = (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
		mediaReader.addListener(new ImageSnapListener(true));
		// read out the contents of the media file and
		// dispatch events to the attached listener
		
		loadingMovie = true;
		while (mediaReader.readPacket() == null) {
			if (firstMovieFrame != null) {
				mediaReader.close();
				break;
			}
		}
		
		gui.repaint();
		// gui.showMovie(frames, 100
		// MICRO_SECONDS_BETWEEN_FRAMES / 1000
		// );
	}

	public void analyzeMovie(int sampleRate) {
		this.sampleRate = sampleRate;
		IMediaReader mediaReader = ToolFactory.makeReader(movieFile
				.getAbsolutePath());
		// stipulate that we want BufferedImages created in BGR 24bit color
		// space
		mediaReader
				.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
		totalFrames = getFramesInMovie(movieFile.getAbsolutePath()) / sampleRate;
		System.err.println("Total Frames is: " + totalFrames);
		System.err.println("Duration is " + duration);
		SECONDS_BETWEEN_FRAMES = duration/totalFrames;
		System.err.println("Seconds between frames is: " + SECONDS_BETWEEN_FRAMES);
		MICRO_SECONDS_BETWEEN_FRAMES = (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
		System.err.println("micro seconds between frames is: " + MICRO_SECONDS_BETWEEN_FRAMES);



		mediaReader.addListener(new ImageSnapListener(false));
		// read out the contents of the media file and
		// dispatch events to the attached listener
		int i=0;
		while (mediaReader.readPacket() == null) {
			// Wait
			System.err.println("This is the " + i +"th time through the loop.");
			i++;
		}
		gui.repaint();
	}

	private class ImageSnapListener extends MediaListenerAdapter {

		private int increment;

		public ImageSnapListener(boolean b) {
			loadingMovie = b;
		}

		public void onVideoPicture(IVideoPictureEvent event) {
			// if uninitialized, back date mLastPtsWrite to get the very first
			// frame
			if (loadingMovie) {
				firstMovieFrame = event.getImage();
				flydentify(event.getImage(), 0);
			} else {
				if (mLastPtsWrite == Global.NO_PTS) {
					mLastPtsWrite = event.getTimeStamp()
							- MICRO_SECONDS_BETWEEN_FRAMES;
				}
				// if it's time to write the next frame
				if (event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES) {
					double seconds = ((double) event.getTimeStamp())
							/ Global.DEFAULT_PTS_PER_SECOND;
					// frames.add(event.getImage());

					if(increment % sampleRate == 0)
					{
						flydentify(event.getImage(), increment / sampleRate);
						System.out.printf(
								"at elapsed time of %6.3f seconds wrote: %s\n",
								seconds, "<imaginary file>");
						// update last write time
						mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES * sampleRate;
					}
					increment++;
				}
			}
		}
	}

	public void setImageContrast(double d) {
		imageContrast = d;
		updateImages();
	}

	public double getImageContrast() {
		return imageContrast;
	}

	public File passdownFile(int imageIndex) {
		return images[imageIndex];
	}

	public BufferedImage getFirstFrameFromMovie() {
		return firstMovieFrame;
	}

	public boolean getMovieLoaded() {
		return movieLoaded;
	}

}
