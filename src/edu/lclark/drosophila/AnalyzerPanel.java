package edu.lclark.drosophila;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class AnalyzerPanel extends JPanel {

	/**
	 * The Data Panel object which this AnalyzerPanel communicates with.
	 */
	private DataTabbs dpanel;
	
	/**
	 * The GraphPanel object which this AnalyzerPanel communicates with.
	 */
	private GraphPanel graphPanel;

	/**
	 * The AnalyzerGui object which this AnalyzerPanel communicates with.
	 */
	private AnalyzerGui gui;
	
	/**
	 * The ImagePanel object which this AnalyzerPanel communicates with.
	 */
	private ImagePanel ipanel;
	
	/**
	 * the JDialog that tells the user the movie is loading
	 */
	private JDialog loadingDialog;
	
	/**
	 * Stores the regions of interest selected
	 * Used by the GraphPanel
	 */
	private int[] regionsOfInterest = new int[1];

	/**
	 * The constructor for AnalyzerPanel, which adds the button panel and image
	 * panel to this panel.
	 * 
	 * @param gui
	 *            the AnalyzerGui which this AnalyzerPanel has to communicate
	 *            with.
	 */
	AnalyzerPanel(AnalyzerGui gui) {
		this.gui = gui;
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();		
		
		ipanel = new ImagePanel(this);
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.anchor = constraints.EAST;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridheight = 3;
		constraints.gridwidth = 1;
		add(ipanel, constraints);
		dpanel = new DataTabbs(this);
		constraints.anchor = constraints.NORTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.weighty=1;
		constraints.insets = new Insets(0, 0, 0, 0);
		constraints.weightx = 1;
		//add(dpanel, constraints);
		constraints.gridheight = 1;
		add(dpanel, constraints);
		
		graphPanel = new GraphPanel(this, false, .10, "TITLE", "Instantaneous velocity (pixels)", "Seconds" );
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		//constraints.insets = new Insets(0, 50, 50, 0);
		add(graphPanel, constraints);
		
		loadingDialog = new JDialog(new Dialog(gui), "Loading", false);
		JPanel panel = new JPanel();
		loadingDialog.setSize(400, 200);
		panel.add(new JLabel("Movie is loading"), BorderLayout.CENTER);
		loadingDialog.add(panel);
	}

	/**
	 * Delegate method to analyze the movie at the given sample rate
	 * @param sampleRate
	 */
	public void analyzeMovie(int sampleRate) {
		gui.analyzeMovie(sampleRate);
		
	}

	/**
	 * Delegate method that asks the model for the average velocity of a group of flies in an arena between frames start and end
	 * @param Arena
	 * @param start
	 * @param end
	 * @return
	 */
	public double calcAverageVelocityforArena(int Arena, int start, int end){
			return gui.calcArenaAverageVelocityinFrame(Arena, start, end);
	}

	/**
	 * Delegate method to clear fly groups
	 */
	public void clearFlyGroups() {
		gui.clearFlyGroups();
		
	}

	/**
	 * Removes the currently attached images and fly data from the Analyzer.
	 */
	public void clearImages() {
		gui.clearImages();
		ipanel.clearYourImages();
	}

	public void contrastThresholdUpdate(int input) {
		gui.contrastThresholdUpdate(input);
		
	}

	/**
	 * Increments the displayed image index in ImagePanel by 1.
	 */
	public void decrementIndex() {
		ipanel.decrementIndex();
	}
	
	/**
	 * Displays the popup that tells the user the movie is loading with the given string
	 * @param s
	 */
	public void displayLoadingPopup(String s) {
		loadingDialog.validate();
		loadingDialog.setVisible(true);
		//JOptionPane.showMessageDialog(null, s, "Loading", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Displays the popup that tells the user an error has occured
	 * @param s the error message to display
	 */
	public void displayMessagePopup(String s) {
		JOptionPane.showMessageDialog(null, s, "Error", 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(getClass().getResource("images/DrawFlyTrajectoriesToggle.png")));
	}

	/**
	 * Hides the loading dialog
	 */
	public void disposeLoadingDialog() {
		loadingDialog.setVisible(false);
	}

	/**
	 * Delegate method to return the average velocity
	 * @return
	 */
	public double[] getAverageVelocity() {
		return gui.getAverageVelocity();
	}

	/**
	 * Delegate method to return the average velocity of specified regions of interest
	 * @param regionsOfInterest2
	 * @return
	 */
	public double[][] getAverageVelocity(int[] regionsOfInterest2) {
		return gui.getAverageVelocity(regionsOfInterest2);
	}

	/** 
	 * Delegate method to return the average velocity of specified regions of interest
	 * in the range of frames provided
	 * @param regionsOfInterest2
	 * @param startFrame
	 * @param endFrame
	 * @return
	 */
	public double[][] getAverageVelocity(int[] regionsOfInterest2,
			int startFrame, int endFrame) {
		return gui.getAverageVelocity(regionsOfInterest2, startFrame, endFrame);
	}

	/**
	 * Delegate method to get data for file
	 * @return
	 */
	public String getDataForFile() {
		return dpanel.getDataForFile();
	}
	
	/**
	 * Delegate method to return the first frame from the movie
	 * @return
	 */
	public BufferedImage getFirstFrameFromMovie() {
		// TODO Auto-generated method stub
		return gui.getFirstFrameFromMovie();
	}

	
	/**
	 * Gets the List of Fly objects, which contain all gathered data, from the
	 * Analyzer.
	 * 
	 * @return a List of identified Fly objects.
	 */
	public List<Fly> getFlyList() {
		if(gui.getFlies()==null){
			return new LinkedList<Fly>();
		}
		return gui.getFlies();
	}
	
	/**
	 * Delegate method to return frame rate
	 * @return
	 */
	public double getFrameRate() {
		// TODO Auto-generated method stub
		return gui.getFrameRate();
	}
	
	/**
	 * Returns a reference to the GUI
	 * @return
	 */
	public AnalyzerGui getGui() {
		return gui;
	}

	/**
	 * Delegate method to return the image contrast
	 * @return
	 */
	public double getImageContrast() {
		return gui.getImageContrast();
	}

	/**
	 * Delegate method to return if the movie has been analyzed
	 * @return
	 */
	public boolean getMovieAnalyzed(){
		return gui.getMovieAnalyzed();
	}

	/**
	 * Delegate method that returns if the movie has been loaded
	 * @return
	 */
	public boolean getMovieLoaded() {
		return gui.getMovieLoaded();
	}
	
	/**
	 * Returns the regions of interest
	 * @return
	 */
	protected int[] getRegionsOfInterest() {
		return regionsOfInterest;
	}
	
	/**
	 * Getter for the total number of frames or images which have been processed
	 * by the Analyzer.
	 * 
	 * @return the total number of frames or images which have been processed.
	 */
	public int getTotalFrames() {
		return gui.getTotalFrames();
	}

	/**
	 * Increments the displayed image index in ImagePanel by 1.
	 */
	public void incrementIndex() {
		ipanel.incrementIndex();
	}

	/**
	 * Delegate method that returns the file at the specific index
	 * @param imageIndex
	 * @return
	 */
	public File passdownFile(int imageIndex) {
		return gui.passdownFile(imageIndex);
	}

	/**
	 * Returns the file path of specified image that is stored in the Analyzer.
	 * 
	 * @param index
	 *            the index of the image that is desired.
	 * @return the String containing the file path of the image specified.
	 */
	public String passdownImage(int index) {
		File file = gui.passDownImage(index);
		if (file != null) {
			return file.getPath();
		}
		return null;
	}

	/**
	 * Delegate method that passes down the points of areas of interest
	 * @param tempFirst
	 * @param tempSecond
	 */
	public void passDownPoints(List<Point> tempFirst, List<Point> tempSecond ){
		ipanel.passDownPoints(tempFirst, tempSecond);
	}

	/**
	 * Passes a File containing an image to the Analyzer, which will identify
	 * any flies within the image.
	 * 
	 * @param file
	 *            the file containing the image that needs to be identified.
	 */
	public void passImage(File file) {
		gui.passImage(file);
	}

	/**
	 * Daisy chain method to pass an opened movie file
	 * @param file
	 */
	public void passMovie(File file) {
		gui.passMovie(file);
	}
	
	/**
	 * Passes the arena along to the gui
	 * @param arena
	 * @param frame
	 */
	public void passUpArenaParameters(int arena, int frame){
		Point point1 = ipanel.getCurrentPoint1();
		Point point2 = ipanel.getCurrentPoint2();
		gui.passupArenaParameters(arena, frame, point1, point2);
	}
	
	/**
	 * Delegate method to save the graph
	 * @param file
	 */
	public void saveGraph(File file) {
		graphPanel.saveGraph(file);
	}

	/**
	 * Sets draw trajectories to draw between given start and end frames
	 * @param startFrame
	 * @param endFrame
	 */
	public void setDrawTrajectories(int startFrame, int endFrame) {
		ipanel.setDrawTrajectories(startFrame, endFrame);
		graphPanel.setDataRange(startFrame - 1, endFrame -1);
	}

	/**
	 * Toggles the identifying dots drawn over the identified flies on the gui.
	 */
	public void setFlydentifiers() {
		ipanel.setFlydentifiers();
	}
		
	/**
	 * Delegate method to set image contrast
	 * @param d
	 */
	public void setImageContrast(double d) {
		gui.setImageContrast(d);		
	}
	
	/**
	 * Delegate method to set movie loading boolean
	 * @param b
	 */
	public void setMovieLoading(boolean b) {
		ipanel.setMovieLoading(b);
		
	}
	
	/**
	 * Delegate method that sets if the movie is playing
	 * @param b
	 */
	public void setMoviePlaying(boolean b){
		ipanel.setMoviePlaying(b);
	}
	
	/**
	 * Sets the regions of interest array to given size
	 * @param listModel
	 */
	public void setRegionsOfInterest(DefaultListModel<Integer> listModel) {
		Object[] regions = listModel.toArray();
		
		int[] tempArray = new int[regions.length];
		for (int i = 0; i < regions.length; i++) {
			tempArray[i]=Integer.parseInt(regions[i].toString());
		}
		regionsOfInterest = tempArray;
	}

	/**
	 * Shows a loaded movie after clicking the "Open a movie" button. 
	 * @param frames
	 * 	         All of the frames of the selected file
	 * @param l
	 * 	      The wait time in milliseconds for a thread to sleep. 
	 */
	public void showMovie(final List<BufferedImage> frames, final long l) {
		System.out.println("l is " + l);
		ipanel.setMoviePlaying(true);
		new Thread(new Runnable() {
			public void run() {
				for (BufferedImage b : frames) {
					final BufferedImage image = b;
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								ipanel.setMoviePlaying(true);
								ipanel.setImage(image);
								ipanel.paintImmediately(ipanel.getVisibleRect());
								Thread.sleep(l);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							ipanel.setMoviePlaying(false);
						}
					});
				}
				
			}
		}
				).start();
	}

	/**
	 * Delegate method to update the size range
	 * @param value
	 */
	public void sizeRangeUpdate(int value) {
		gui.sizeRangeUpdate(value);
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
		gui.sizeThresholdUpdate(input);
	}

	public void setLabels(String text, String text2, String text3) {
		graphPanel.setLabels(text, text2, text3);
	}
}
