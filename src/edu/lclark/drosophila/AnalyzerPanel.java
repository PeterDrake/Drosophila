package edu.lclark.drosophila;

import java.awt.*;
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
	/**
	 * The Data Panel object which this AnalyzerPanel communicates with.
	 */
	private DataPanel dpanel;

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
		ButtonPanel bpanel = new ButtonPanel(this);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.insets = new Insets(0, 0, 0, 50);
		constraints.anchor = constraints.NORTH;
		add(bpanel, constraints);
		ipanel = new ImagePanel(this);
		constraints.anchor = constraints.EAST;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridheight = 2;
		add(ipanel, constraints);
		dpanel = new DataPanel(this);
		constraints.anchor = constraints.NORTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth= 1;
		constraints.weightx = 1;
		//add(dpanel, constraints);
		
		graphPanel = new GraphPanel(this, false, .10, "TITLE", "vertical label now this is longer ", "Xkljhfdsalkjfhasdkljfh" );
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridheight = 1;
		//constraints.insets = new Insets(0, 50, 50, 0);
		add(graphPanel, constraints);
	}

	public double[] getAverageVelocity() {
		return gui.getAverageVelocity();


	}

	/**
	 * Removes the currently attached images and fly data from the Analyzer.
	 */
	public void clearImages() {
		gui.clearImages();

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
}
