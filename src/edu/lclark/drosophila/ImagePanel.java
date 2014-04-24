package edu.lclark.drosophila;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel {

	/**
	 * The default preferred width of this panel.
	 */
	private static final int DEFAULT_WIDTH = 800;

	/**
	 * The default preferred height of this panel.
	 */
	private static final int DEFAULT_HEIGHT = 800;

	/**
	 * The boolean which keeps track of whether or not the identifying dots
	 * drawn over flies is toggled.
	 */
	private boolean flydentifiers;

	/** Boolean that keeps track of whether or not the trajectories are drawn. */
	private boolean drawTrajectories;

	/** First frame to draw trajectories for */
	private int firstFrame;
	/** Last frame to draw trajectories for */
	private int lastFrame;

	private int numberOfImages;

	private Point currentpoint1;
	private Point currentpoint2;
	private double scale;

	/**
	 * Boolean for if we are trying to load the first frame from a movie.
	 */
	private boolean movieLoading;

	/**
	 * The AnalyzerPanel object that this ImagePanel communicates with.
	 */
	private AnalyzerPanel analyzerPanel;

	/**
	 * The index of the image that is currently being displayed. Used with
	 * Analyzer's Files array.
	 */
	private int imageIndex;

	/**
	 * The current frame of the movie
	 */
	private BufferedImage image;

	/**
	 * Past first points of region rectangles
	 */
	private List<Point> pastFirstPoints;

	/**
	 * Past second points of region rectangles
	 */
	private List<Point> pastSecondPoints;

	private boolean moviePlaying;

	public void setMoviePlaying(boolean a) {
		moviePlaying = a;
	}

	private double oldImageContrast;

	private BufferedImage oldImage;

	/**
	 * Image saved without changing contrast so we can compare it to a new image
	 * we load
	 */
	private BufferedImage savedImage;

	/**
	 * The constructor which sets the AnalyzerPanel this ImagePanel is connected
	 * to.
	 * 
	 * @param anayzerPanel
	 *            the AnalyzerPanel which this ImagePanel is connected to.
	 */
	public ImagePanel(AnalyzerPanel a) {
		this.analyzerPanel = a;
		flydentifiers = false;
		imageIndex = 0;
		addMouseListener(new MouseHandler());
		pastFirstPoints= new LinkedList<Point>();
		pastSecondPoints= new LinkedList<Point>();
		
	}

	/**
	 * Decrements the displayed image index by 1.
	 */
	public void decrementIndex() {
		imageIndex--;
	}

	/**
	 * Returns the preferred size of this panel as a Dimension object.
	 */
	public Dimension getPreferredSize() {
		return new Dimension((int) (analyzerPanel.getWidth() * (5.0 / 8.0)), analyzerPanel.getHeight());
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/** Returns the minimum size of this panel as a Dimension object */
	public Dimension getMinimumSize() {
		return new Dimension((int) (analyzerPanel.getWidth() * (5.0 / 8.0)), analyzerPanel.getHeight());
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Increments the displayed image index by 1.
	 */
	public void incrementIndex() {
		imageIndex++;
	}

	public int getImageIndex() {
		return imageIndex;
	}

	/**
	 * Draws any components of this panel. It draws the selected image or frame,
	 * and red circles over any identified flies.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (moviePlaying) {
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);
			
			double xScale = this.getWidth() / (double) imgWidth;
			double yScale = this.getHeight() / (double) imgHeight;
			scale = Math.min(xScale, yScale);
			g.drawImage(image, 0, 0, (int) (imgWidth * scale),
					(int) (imgHeight * scale), null);
		} else {

//			int totalImages = analyzerPanel.getTotalFrames();
//			if (imageIndex < 0) {
//				imageIndex = 0;
//			} else if (imageIndex >= totalImages) {
//				imageIndex = totalImages - 1;
//			}
//			// String filePath = analyzerPanel.passdownImage(imageIndex);
//			File file = null;
//			if (imageIndex >= 0) {
//				file = analyzerPanel.passdownFile(imageIndex);
//			} else {
//				return;
//			}
//			if (file != null) {
//				// Image image = new ImageIcon(filePath).getImage();
//				BufferedImage image = null;
//				try {
//					image = ImageIO.read(file);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					System.exit(0);
//				}

			if (!analyzerPanel.getMovieLoaded()) {

				int totalImages = analyzerPanel.getTotalFrames();
				if (imageIndex < 0) {
					imageIndex = 0;
				} else if (imageIndex >= totalImages) {
					imageIndex = totalImages - 1;
				}
				// String filePath = analyzerPanel.passdownImage(imageIndex);
				File file = null;
				if (imageIndex >= 0) {
					file = analyzerPanel.passdownFile(imageIndex);

				} else {
					return;
				}
				if (file != null) {

					// Image image = new ImageIcon(filePath).getImage();
					image = null;
					try {

						image = ImageIO.read(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(0);
					}
				}
			} else {
				BufferedImage tempImage = analyzerPanel
						.getFirstFrameFromMovie();
				image = new BufferedImage(tempImage.getColorModel(),
						tempImage.copyData(null), tempImage.getColorModel()
								.isAlphaPremultiplied(), null);
				imageIndex = 0;
			}
			if (image != null) {

				int imgWidth = image.getWidth(null);
				int imgHeight = image.getHeight(null);

				double xScale = this.getWidth() / (double) imgWidth;
				double yScale = this.getHeight() / (double) imgHeight;

				scale = Math.min(xScale, yScale);
				System.out.println("scale is " + scale);

				double imageContrast = analyzerPanel.getImageContrast();
			//	if (imageContrast != oldImageContrast) {
					for (int i = 0; i < image.getWidth(null); i++) {
						for (int j = 0; j < image.getHeight(null); j++) {
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
							rgb = 255 << 24;
							rgb += red << 16;
							rgb += green << 8;
							rgb += blue;
							image.setRGB(i, j, rgb);

						}
					}
					oldImage = image;
					oldImageContrast = imageContrast;
				//}

				oldImage = image;
				oldImageContrast = imageContrast;
				// }


				g.drawImage(oldImage, 0, 0, (int) (imgWidth * scale),
						(int) (imgHeight * scale), null);
				// g.drawImage(image, 0, 0, null);


				if (flydentifiers) {
					List<Fly> flies = analyzerPanel.getFlyList();
					int sizeFlies = flies.size();
					for (int i = 0; i < sizeFlies; i++) {
						if (flies.get(i).getX(imageIndex) != -1) {
							g.setColor(new Color(Color.HSBtoRGB(
									(float) ((i * 1.0) / sizeFlies),
									(float) 0.75, (float) 0.95)));
							g.fillOval(
									(int) ((flies.get(i).getX(imageIndex) - 6) * scale),
									(int) ((flies.get(i).getY(imageIndex) - 6) * scale),
									(int) (12 * scale), (int) (12 * scale));
						}
					}
				}
				if (drawTrajectories) {
					g.setColor(Color.RED);
					List<Fly> flies = analyzerPanel.getFlyList();
					int flyNumber = 0;
					int sizeFlies = flies.size();
					for (Fly fly : flies) {
						g.setColor(new Color(Color.HSBtoRGB(
								(float) ((flyNumber * 1.0) / sizeFlies),
								(float) 0.75, (float) 0.95)));
						for (int i = firstFrame; i < lastFrame; i++) {
							int x1 = (int) fly.getX(i);
							int y1 = (int) fly.getY(i);
							int x2 = (int) fly.getX(i + 1);
							int y2 = (int) fly.getY(i + 1);
							if (!((x1 == 0 && y1 == 0) || (x2 == 0 && y2 == 0))) {// doesn't
																					// draw
																					// flies
																					// that
																					// don't
																					// appear
																					// in
																					// both
																					// frames
								g.drawLine((int) (x1 * scale),
										(int) (y1 * scale), (int) (x2 * scale),
										(int) (y2 * scale));
							}
						}
						flyNumber++;
					}
				}
			}
		}
		if (currentpoint1 != null && currentpoint2 != null) {
			System.out.println("drawing square");
			g.setColor(Color.BLACK);
			// Point first[] = (Point[]) pastFirstPoints.toArray();
			// Point second[] = (Point[]) pastSecondPoints.toArray();
			if (!pastFirstPoints.isEmpty()) {
				for (int i = 0; i < pastFirstPoints.size(); i++) {
					System.out.println(pastFirstPoints.get(i));
					g.drawRect((int)(pastFirstPoints.get(i).x*scale),
							(int)(pastFirstPoints.get(i).y*scale), (int)(Math.abs(pastFirstPoints.get(i).x
									- pastSecondPoints.get(i).x)*scale),
							(int)(Math.abs(pastSecondPoints.get(i).y
									- pastFirstPoints.get(i).y)*scale));
				}
			}
			g.setColor(Color.RED);
			int xPoints[] = { currentpoint1.x, currentpoint2.x,
					currentpoint2.x, currentpoint1.x };
			int yPoints[] = { currentpoint1.y, currentpoint1.y,
					currentpoint2.y, currentpoint2.y };
			g.drawPolygon(xPoints, yPoints, 4);
			}
		}

	

	/**
	 * Toggles whether or not fly trajectories are drawn over identified flies,
	 * will draw their paths from startFrame to endFrame. Input is frame values
	 * starting at 1 for ease of use, this method converts that to frame values
	 * starting at 0 to match implementation.
	 */
	public void setDrawTrajectories(int startFrame, int endFrame) {
		drawTrajectories = !drawTrajectories;
		firstFrame = startFrame - 1;
		lastFrame = endFrame - 1;
		analyzerPanel.repaint();
	}

	/**
	 * Toggles whether or not the identifying red circles are drawn over
	 * identified flies.
	 */
	public void setFlydentifiers() {
		flydentifiers = !flydentifiers;
		analyzerPanel.repaint();
	}

	public void passDownPoints(List<Point> tempFirst, List<Point> tempSecond) {
		System.out.println("list is being set");
		pastFirstPoints = tempFirst;
		pastSecondPoints = tempSecond;
	}

	/**
	 * Sets this ImagePanel's displayed image index to the given
	 * 
	 * @param imageIndex
	 */
	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	public void setImage(BufferedImage b) {
		image = b;
	}


	public Point getCurrentPoint1() {
		double x = (double) currentpoint1.x;
		double scalemath = x / scale;
		System.out.println("scale" + scale);
		System.out.println("scaled math " + scalemath);
		Point sent = new Point((int) (currentpoint1.x / scale),
				(int) (currentpoint1.y / scale));
		return sent;
	}

	public Point getCurrentPoint2() {
		Point sent = new Point((int) (currentpoint2.x / scale),
				(int) (currentpoint2.y / scale));
		return sent;
	}

	public class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			System.out.println("point" + event.getPoint());
			currentpoint1 = event.getPoint();
		}

		public void mouseClicked(MouseEvent event) {
		}

		public void mouseReleased(MouseEvent event) {
			System.out.println("release point" + event.getPoint());
			currentpoint2 = event.getPoint();
			// analyzerPanel.passUpArenaParameters();
			repaint();
		}

		public class MouseMotionHandler implements MouseMotionListener {

			@Override
			public void mouseDragged(MouseEvent event) {
			}

			@Override
			public void mouseMoved(MouseEvent event) {

			}

		}

	}
	public void setMovieLoading(boolean b) {
		movieLoading = b;
	}
	
}
