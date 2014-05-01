package edu.lclark.drosophila;

import java.awt.EventQueue;
import java.awt.Point;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * This class (a JFrame) coordinates between the Analyzer and the rest of the
 * panels in the GUI
 */
public class AnalyzerGui extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * The Analyzer object which this AnalyzerGui communicates with.
	 */
	private Analyzer analyzer;

	/**
	 * The AnalyzerPanel object which this AnalyzerGui communicates with.
	 */
	private AnalyzerPanel analyzerPanel;

	/**
	 * The menu bar that holds the buttons for the GUI
	 */
	private JMenuBar ButtonMenuBar;

	/**
	 * The constructor for AnalyzerGui.
	 * 
	 * @param a
	 *            the Analyzer object which this AnalyzerGui communicates with.
	 */
	public AnalyzerGui(Analyzer a) {
		this.analyzer = a;
		this.analyzerPanel = new AnalyzerPanel(this);
		this.ButtonMenuBar = new ButtonToolbar(analyzerPanel);
	}

	/**
	 * Delegate method to tell the analyzer to sample the movie at the specified rate
	 * @param sampleRate
	 */
	public void analyzeMovie(int sampleRate) {
		analyzer.analyzeMovie(sampleRate);
	}

	/**
	 * Delegate method for the analyzer to calcuate the average velocity in an arena
	 * @param Arena
	 * @param start
	 * @param end
	 * @return
	 */
	public double calcArenaAverageVelocityinFrame(int Arena, int start, int end) {
		return analyzer.calcArenaAverageVelocityinFrame(Arena, start, end);
	}

	/**
	 * Delegate method for the analyzer to clear fly groups
	 */
	public void clearFlyGroups() {
		analyzer.clearFlyGroups();

	}

	/**
	 * Removes the currently attached images and fly data from the Analyzer.
	 */
	public void clearImages() {
		analyzer.clearImages();
	}

	/**
	 * Delegate method for the analyzer to update the contrast threshold
	 * @param input
	 */
	public void contrastThresholdUpdate(int input) {
		analyzer.contrastThresholdUpdate(input);
	}

	/**
	 * Delegate method for the analyzer to return the average velocity
	 * @return
	 */
	public double[] getAverageVelocity() {
		return analyzer.averageVelMultFlies(analyzer.getFlies(), 0, analyzer
				.getFlies().get(0).getVx().length);
	}

	/**
	 * Delegate method for the analyzer to return the average velocity for given regions of interest
	 * @param regionsOfInterest
	 * @return
	 */
	public double[][] getAverageVelocity(int[] regionsOfInterest) {
		double[][] tempRegionsFlies = new double[regionsOfInterest.length][];
		for (int i = 0; i < regionsOfInterest.length; i++) {
			tempRegionsFlies[i] = analyzer.averageVelMultFlies(
					analyzer.getFlies(regionsOfInterest[i]), 0, analyzer
							.getFlies().get(0).getVx().length);
		}

		return tempRegionsFlies;
	}

	/**
	 * Delegate method to return the average velocity for given regions of interest
	 * over the given range of movie frames
	 * @param regionsOfInterest
	 * @param startFrame
	 * @param endFrame
	 * @return
	 */
	public double[][] getAverageVelocity(int[] regionsOfInterest,
			int startFrame, int endFrame) {
		double[][] tempRegionsFlies = new double[regionsOfInterest.length][];
		for (int i = 0; i < regionsOfInterest.length; i++) {
			tempRegionsFlies[i] = analyzer.averageVelMultFlies(
					analyzer.getFlies(regionsOfInterest[i]), startFrame,
					endFrame);
		}

		return tempRegionsFlies;
	}

	/**
	 * Delegate method to return the first frame of the movie
	 * @return
	 */
	public BufferedImage getFirstFrameFromMovie() {
		return analyzer.getFirstFrameFromMovie();
	}

	/**
	 * Gets the List of Fly objects, which contain all gathered data, from the
	 * Analyzer.
	 * 
	 * @return a List of identified Fly objects.
	 */
	public List<Fly> getFlies() {
		return analyzer.getFlies();
	}

	/**
	 * Delegate method to return the frame rate
	 * @return
	 */
	public double getFrameRate() {
		return analyzer.getFrameRate();
	}

	/**
	 * Delegate method to return the image contrast
	 * @return
	 */
	public double getImageContrast() {
		return analyzer.getImageContrast();
	}

	/**
	 * Delegate method to return if the movie has been analyzed
	 * @return
	 */
	public boolean getMovieAnalyzed() {
		return analyzer.getMovieAnalyzed();
	}

	/**
	 * Delegate method to return if the movie has been loaded
	 * @return
	 */
	public boolean getMovieLoaded() {
		return analyzer.getMovieLoaded();
	}

	/**
	 * Getter for the total number of frames or images which have been processed
	 * by the Analyzer.
	 * 
	 * @return the total number of frames or images which have been processed.
	 */
	public int getTotalFrames() {
		return analyzer.getTotalFrames();
	}

	/**
	 * Delegate method to return the file of the given index
	 * @param imageIndex
	 * @return
	 */
	public File passdownFile(int imageIndex) {
		return analyzer.passdownFile(imageIndex);
	}

	/**
	 * Returns the File of a specified image that is stored in the Analyzer.
	 * 
	 * @param index
	 *            the index of the image that is desired.
	 * @return the File containing the image specified.
	 */
	public File passDownImage(int index) {
		return analyzer.getImage(index);
	}

	/**
	 * Delegate method to return the corners of arenas of interest
	 * @param tempFirst
	 * @param tempSecond
	 */
	public void passDownPoints(List<Point> tempFirst, List<Point> tempSecond) {

		analyzerPanel.passDownPoints(tempFirst, tempSecond);
	}

	/**
	 * Passes a File containing an image to the Analyzer, which will identify
	 * any flies within the image.
	 * 
	 * @param file
	 *            the file containing the image that needs to be identified.
	 */
	public void passImage(File file) {
		analyzer.flydentify(file);
	}

	/**
	 * Daisy chain method to pass an opened movie file
	 * 
	 * @param file
	 */
	public void passMovie(File file) {
		analyzer.openMovie(file);

	}

	/**
	 * delegate method to return the arena parameters
	 * @param arena
	 * @param frame
	 * @param point1
	 * @param point2
	 */
	public void passupArenaParameters(int arena, int frame, Point point1,
			Point point2) {
		analyzer.setFliestoArena(point1, point2, arena, frame);
	}

	@Override
	public void repaint() {
		analyzerPanel.repaint();
	}

	/**
	 * Runs the gui. Is called to start the gui.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("Flydentifier");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.add(analyzerPanel);
				frame.setJMenuBar(ButtonMenuBar);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				// frame.pack();
			}
		});
		analyzerPanel.repaint();
	}

	/**
	 * Delegate method to set the image contrast in Analyzer
	 * @param d
	 */
	public void setImageContrast(double d) {
		analyzer.setImageContrast(d);
	}

	/**
	 * Delegate method to set if movie is loading in Analyzer
	 * @param b if the movie is loading
	 */
	public void setMovieLoading(boolean b) {
		analyzerPanel.setMovieLoading(b);

	}

	/**
	 * Delegate method to update the pixel size range
	 * @param value
	 */
	public void sizeRangeUpdate(int value) {
		analyzer.sizeRangeUpdate(value);
	}

	/**
	 * Updates the size threshold in Analyzer. This is used to determine if an
	 * object identified within an image is large enough to be considered a fly.
	 * This will also analyze all stored images again.
	 * 
	 * @param input
	 *            the value which size threshold will be set to.
	 */
	public void sizeThresholdUpdate(int input) {
		analyzer.sizeThresholdUpdate(input);
	}

}
