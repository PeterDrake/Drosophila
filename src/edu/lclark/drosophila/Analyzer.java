package edu.lclark.drosophila;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

public class Analyzer {

	private int sizeThreshold;

	private static AnalyzerGui gui;

	private static final int CONTRAST_THRESHOLD = 200;

	private List<Fly> flies;

	private int totalFrames;

	private File[] images;

	private int numImages;

	public Analyzer() {
		totalFrames = 5;
		flies = new LinkedList<Fly>();
		images = new File[totalFrames];
		numImages = 0;
	}

	public void sizeThresholdUpdate(int input) {
		sizeThreshold = input;
		if (numImages > 0) {
			updateImages();
		}
	}

	public static void main(String[] args) {
		gui = new AnalyzerGui(new Analyzer());
		gui.run();
	}

	public File getImage(int index) {
		return images[index];
	}

	public void updateImages() {
		try {
			for (int i = 0; i < numImages; i++) {
				BufferedImage image = ImageIO.read(images[i]);
				flydentify(image, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void flydentify(File file) {
		try {
			images[numImages] = file;
			numImages++;
			BufferedImage image = ImageIO.read(file);
			flydentify(image, numImages - 1);
		} catch (IOException e) {
			System.err.println("EVERYTHING IS HORRIBLE");
			e.printStackTrace();
		}
	}

	/**
	 * TODO this is not adjusted for tracking flies between multiple frames.
	 * People who have worked on this and therefore you can ask questions about:
	 * Brandon Christian
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
					double avg = red * 0.2989 + green * .587 + blue * .114;
					if ((int) (Math.round(avg)) <= CONTRAST_THRESHOLD) { // if
																			// the
																			// color
																			// is
																			// dark
																			// enough
						int totalX = 0;
						int totalY = 0;
						int numPixels = 0; // initialize values to find center
											// of
											// mass of fly
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
						if (numPixels >= sizeThreshold) // if the blob is large
														// enough to be a fly
						{
							// create a new temporary fly object
							double tempLocation[] = new double[2];
							tempLocation[0] = (double) totalX / numPixels;
							tempLocation[1] = (double) totalY / numPixels;
							tempFlies.add(tempLocation);
							System.out.println("size: " + numPixels);
						}
					} else {
						// we searched this already!
						searchArray[i][j] = true;
					}
				}
			}
		}
		// create this variable because flies.size() is linear
		int sizeFlies = flies.size();
		for (int i = 0; i < sizeFlies && !tempFlies.isEmpty(); i++) {
			// going through each existing fly and matching it to the closest
			// temporary counterpart
			Fly pastFly = flies.get(i);
			double pastX = pastFly.getX(0);
			double pastY = pastFly.getY(0);
			double dist = Math.sqrt(Math.pow(pastX - tempFlies.get(0)[0], 2)
					+ Math.pow(pastY - tempFlies.get(0)[1], 2));
			int closestFlyIndex = 0;
			int sizeTempFlies = tempFlies.size();
			for (int j = 1; j < sizeTempFlies; j++) {
				double thisDist = Math.sqrt(Math.pow(pastX
						- tempFlies.get(j)[0], 2)
						+ Math.pow(pastY - tempFlies.get(j)[1], 2));
				if (thisDist < dist) {
					dist = thisDist;
					closestFlyIndex = j;
				}
			}
			// augmenting the existing fly to contain information about location
			// from the image
			pastFly.addFrameInfo(frameNumber,
					tempFlies.get(closestFlyIndex)[0],
					tempFlies.get(closestFlyIndex)[1]);
			// remove the temporary fly so two existing flies can't map to the
			// same temporary one
			tempFlies.remove(closestFlyIndex);
		}
		// in case there are more temporary flies than existing flies
		sizeFlies = tempFlies.size();
		while (!tempFlies.isEmpty()) {
			// create a new fly for each fly left
			Fly aNewFly = new Fly(totalFrames);
			aNewFly.addFrameInfo(frameNumber, tempFlies.get(0)[0],
					tempFlies.get(0)[1]);
			flies.add(aNewFly);
			tempFlies.remove(0);
		}
	}

	public boolean isDarkEnough(int rgb) {
		int red;
		int green;
		int blue;
		double avg;
		red = (rgb >> 16) & 0xFF;
		green = (rgb >> 8) & 0xFF;
		blue = rgb & 0xFF;
		avg = red * 0.2989 + green * .587 + blue * .114;
		boolean found = ((int) (Math.round(avg)) <= CONTRAST_THRESHOLD);
		return found;
	}

	public void flydentify(BufferedImage image) {
		// just for a single image
		flies = new LinkedList<Fly>();
		totalFrames = 1;
		flydentify(image, 0);

		System.out.println("number of flies: " + flies.size());
	}

	public double averageVelFly(Fly fly, int start, int end) {
		double avgVel = 0;
		double[] vx = fly.getVx();
		double[] vy = fly.getVy();
		for (int i = start; i <= end; i++) {
			avgVel += vx[i] + vy[i];
		}
		avgVel = avgVel / (end - (start - 1));
		return avgVel;
	}

	public List<Fly> getFlies() {
		return flies;
	}
}
