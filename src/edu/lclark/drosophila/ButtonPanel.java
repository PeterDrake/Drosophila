package edu.lclark.drosophila;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class ButtonPanel extends JPanel {

	/**
	 * The action listener which decrements the ImagePanel's displayed image
	 * index by 1 when the forward frame button is clicked.
	 */
	private class BackFrameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			analyzerPanel.decrementIndex();
			analyzerPanel.repaint();
		}
	}

	/**
	 * The action listener which keeps track of when the Flydentifiers button is
	 * clicked. It toggles whether or not flydentifiers are drawn.
	 */
	private class DrawFlydentifiersAction implements ActionListener {

		/**
		 * Tells the AnalyzerPanel to toggle the flydentifiers.
		 */
		public void actionPerformed(ActionEvent e) {
			analyzerPanel.setFlydentifiers();
		}
	}

	/**
	 * The action listener which increments the ImagePanel's displayed image
	 * index by 1 when the forward frame button is clicked.
	 */
	private class ForwardFrameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			analyzerPanel.incrementIndex();
			analyzerPanel.repaint();
		}
	}

	/**
	 * The action listener which opens a file browsing window when the get image
	 * button is clicked.
	 */
	private class GetImageAction implements ActionListener {

		private ButtonPanel bpanel;

		public GetImageAction(ButtonPanel bpanel) {
			this.bpanel = bpanel;
		}

		/**
		 * Opens the file browsing window and passes the selected image to the
		 * Button Panel when chosen.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int returnVal = fileChooser.showOpenDialog(getImage);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				bpanel.passImage(file);
				analyzerPanel.repaint();
			}

		}

	}

	/**
	 * The action listener which changes the Analyzer's size threshold when the
	 * size threshold button is clicked.
	 */
	private class SetThresholdAction implements ActionListener {

		/**
		 * Parses the entered number in the text box, and sets the Analyzer's
		 * size threshold when the button is clicked.
		 */
		public void actionPerformed(ActionEvent e) {
			// TODO This just ignores anything that is not an integer. We need
			// to fix that.
			try {
				int testText = Integer.parseInt(thresholdText.getText());
				analyzerPanel.sizeThresholdUpdate(testText);
			} catch (NumberFormatException error) {
				error.printStackTrace();
				System.exit(1);
			}
		}
	}

	/**
	 * The file browser which allows the user to choose a file which contains an
	 * image.
	 */
	private JFileChooser fileChooser;

	/**
	 * The button which lets the user specify an image to display and analyze.
	 */
	private JButton getImage;

	/**
	 * The button which lets the user specify what the Analyzer's size threshold
	 * is.
	 */
	private JButton setThreshold;

	/**
	 * The button which advances the displayed image one frame forward.
	 */
	private JButton forwardFrame;

	/**
	 * The button which advances the displayed images one frame backwards.
	 */
	private JButton backFrame;

	/**
	 * The text field which lets the user specify what the Analyzer's size
	 * threshold is.
	 */
	private JTextField thresholdText;

	/**
	 * The button which lets the user toggle flydentifiers.
	 */
	private JButton drawFlydentifiers;

	/**
	 * The default preferred width of this panel.
	 */
	private static final int DEFAULT_WIDTH = 500;

	/**
	 * The default preferred height of this panel.
	 */
	private static final int DEFAULT_HEIGHT = 500;

	/**
	 * The AnalyzerPanel object that this ImagePanel communicates with.
	 */
	private AnalyzerPanel analyzerPanel;

	/**
	 * The constructor which initializes all fields and adds the buttons to this
	 * panel.
	 * 
	 * @param analyzerPanel
	 *            the AnalyzerPanel object which this panel is attached to.
	 */
	public ButtonPanel(AnalyzerPanel a) {
		this.analyzerPanel = a;
		fileChooser = new JFileChooser();
		getImage = new JButton("Open an Image");
		setThreshold = new JButton("Set fly size threshold (in pixels)");
		drawFlydentifiers = new JButton("Draw fly locations");
		forwardFrame = new JButton("\u25B6");
		backFrame = new JButton("\u25C0");
		add(getImage);
		thresholdText = new JTextField();
		thresholdText.setPreferredSize(new Dimension(100, 50));
		add(setThreshold);
		add(thresholdText);
		add(drawFlydentifiers);
		add(backFrame);
		add(forwardFrame);
		GetImageAction getImageAction = new GetImageAction(this);
		SetThresholdAction setThresholdAction = new SetThresholdAction();
		DrawFlydentifiersAction drawFlydentifiersAction = new DrawFlydentifiersAction();
		ForwardFrameAction forwardFrameAction = new ForwardFrameAction();
		BackFrameAction backFrameAction = new BackFrameAction();
		setThreshold.addActionListener(setThresholdAction);
		getImage.addActionListener(getImageAction);
		drawFlydentifiers.addActionListener(drawFlydentifiersAction);
		forwardFrame.addActionListener(forwardFrameAction);
		backFrame.addActionListener(backFrameAction);
	}

	/**
	 * Returns the preferred size of this panel as a Dimension object.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Draws any components on this panel.
	 */
	public void paintComponent(Graphics g) {
	}

	/**
	 * Passes a File containing an image to the Analyzer, which will identify
	 * any flies within the image.
	 * 
	 * @param file
	 *            the file containing the image that needs to be identified.
	 */
	public void passImage(File file) {
		analyzerPanel.passImage(file);
	}
}
