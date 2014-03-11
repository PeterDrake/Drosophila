package edu.lclark.drosophila;

import java.awt.EventQueue;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;

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
	 * The constructor for AnalyzerGui.
	 * 
	 * @param a
	 *            the Analyzer object which this AnalyzerGui communicates with.
	 */
	public AnalyzerGui(Analyzer a) {
		this.analyzer = a;
		this.analyzerPanel = new AnalyzerPanel(this);
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
				frame.pack();
			}
		});
		analyzerPanel.repaint();
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
