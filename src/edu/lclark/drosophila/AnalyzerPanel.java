package edu.lclark.drosophila;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.*;

public class AnalyzerPanel extends JPanel {

	/**
	 * The AnalyzerGui object which this AnalyzerPanel communicates with.
	 */
	private AnalyzerGui gui;

	/**
	 * The ImagePanel object which this AnalyzerPanel communicates with.
	 */
	private ImagePanel ipanel;

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
		add(bpanel, constraints);
		ipanel = new ImagePanel(this);
		constraints.anchor = constraints.EAST;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		add(ipanel, constraints);
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

	
	/**
	 * Daisy chain method to pass an opened movie file
	 * @param file
	 */
	public void passMovie(File file) {
		gui.passMovie(file);
	}

	
	
	public void showMovie(final List<BufferedImage> frames, final long l) {
		System.out.println("l is " + l);
		ipanel.setMoviePlaying(true);
		new Thread(new Runnable() {
			public void run() {
				for (BufferedImage b : frames) {
					final BufferedImage image = b;
					System.out.println("About to create a runnable");
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								System.out.println("About to run");
								ipanel.setImage(image);
								ipanel.paintImmediately(ipanel.getVisibleRect());
								System.out.println("I painted!");
								System.out.println("About to sleep");
								Thread.sleep(l);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				}
				
			}
		}
				).start();

	}
	
}
