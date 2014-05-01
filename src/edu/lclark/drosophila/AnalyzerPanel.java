package edu.lclark.drosophila;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class AnalyzerPanel extends JPanel {

	/**
	 * The AnalyzerGui object which this AnalyzerPanel communicates with.
	 */
	private AnalyzerGui gui;
	
	/**
	 * The GraphPanel object which this AnalyzerPanel communicates with.
	 */
	private GraphPanel graphPanel;

	/**
	 * The ImagePanel object which this AnalyzerPanel communicates with.
	 */
	private ImagePanel ipanel;
	
	private int[] regionsOfInterest = new int[1];
	/**
	 * The Data Panel object which this AnalyzerPanel communicates with.
	 */
	private DataTabbs dpanel;

	private JDialog loadingDialog;

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

	public double[] getAverageVelocity() {
		return gui.getAverageVelocity();


	}

	/**
	 * Removes the currently attached images and fly data from the Analyzer.
	 */
	public void clearImages() {
		gui.clearImages();
		ipanel.clearYourImages();
	}

	/**
	 * Increments the displayed image index in ImagePanel by 1.
	 */
	public void decrementIndex() {
		ipanel.decrementIndex();
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
	
	public void displayMessagePopup(String s) {
		JOptionPane.showMessageDialog(null, s, "Error", 
				JOptionPane.INFORMATION_MESSAGE, 
				new ImageIcon(getClass().getResource("images/DrawFlyTrajectoriesToggle.png")));
	}
	
	public void displayLoadingPopup(String s) {
		loadingDialog.validate();
		loadingDialog.setVisible(true);
		//JOptionPane.showMessageDialog(null, s, "Loading", JOptionPane.INFORMATION_MESSAGE);
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
	 * Passes a File containing an image to the Analyzer, which will identify
	 * any flies within the image.
	 * 
	 * @param file
	 *            the file containing the image that needs to be identified.
	 */
	public void passImage(File file) {
		gui.passImage(file);
	}

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
	
	/**
	 * Daisy chain method to pass an opened movie file
	 * @param file
	 */
	public void passMovie(File file) {
		gui.passMovie(file);
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
//		ipanel.setMoviePlaying(false);
	}
	
	public void setMoviePlaying(boolean b){
		ipanel.setMoviePlaying(b);
	}
	
	public void passDownPoints(List<Point> tempFirst, List<Point> tempSecond ){
		
		ipanel.passDownPoints(tempFirst, tempSecond);
	}

	public void setImageContrast(double d) {
		gui.setImageContrast(d);		
	}

	public double getImageContrast() {
		return gui.getImageContrast();
	}

	public File passdownFile(int imageIndex) {
		return gui.passdownFile(imageIndex);
	}
	
	public void contrastThresholdUpdate(int input) {
		gui.contrastThresholdUpdate(input);
		
	}
	public void passUpArenaParameters(int arena, int frame){
		Point point1 = ipanel.getCurrentPoint1();
		Point point2 = ipanel.getCurrentPoint2();
		gui.passupArenaParameters(arena, frame, point1, point2);
	}

	public void clearFlyGroups() {
		gui.clearFlyGroups();
		
	}

	public BufferedImage getFirstFrameFromMovie() {
		// TODO Auto-generated method stub
		return gui.getFirstFrameFromMovie();
	}

	public void setMovieLoading(boolean b) {
		ipanel.setMovieLoading(b);
		
	}

	public boolean getMovieLoaded() {
		// TODO Auto-generated method stub
		return gui.getMovieLoaded();
	}

	public void analyzeMovie(int sampleRate) {
		gui.analyzeMovie(sampleRate);
		
	}

	public AnalyzerGui getGui() {
		return gui;
	}
	
	public void disposeLoadingDialog() {
		loadingDialog.setVisible(false);
	}


	public double getFrameRate() {
		// TODO Auto-generated method stub
		return gui.getFrameRate();
	}

	public void setLabels(String titleText, String xAxisText, String yAxisText) {
		graphPanel.setLabels(titleText, xAxisText, yAxisText);
	}

	public void saveGraph(File file) {
		graphPanel.saveGraph(file);
	}
		
		
	public String getDataForFile() {
		return dpanel.getDataForFile();
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


	public void sizeRangeUpdate(int value) {
		gui.sizeRangeUpdate(value);
	}

	public void setRegionsOfInterest(DefaultListModel<Integer> listModel) {
		Object[] regions = listModel.toArray();
		
		int[] tempArray = new int[regions.length];
		for (int i = 0; i < regions.length; i++) {
			tempArray[i]=Integer.parseInt(regions[i].toString());
		}
		regionsOfInterest = tempArray;
	}

	protected int[] getRegionsOfInterest() {
		return regionsOfInterest;
	}

	public double[][] getAverageVelocity(int[] regionsOfInterest2) {
		return gui.getAverageVelocity(regionsOfInterest2);
	}

	public double[][] getAverageVelocity(int[] regionsOfInterest2,
			int startFrame, int endFrame) {
		return gui.getAverageVelocity(regionsOfInterest2, startFrame, endFrame);
	}

	
	public boolean getMovieAnalyzed(){
		return gui.getMovieAnalyzed();
	}
}
