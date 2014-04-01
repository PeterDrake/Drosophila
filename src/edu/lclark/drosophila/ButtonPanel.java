package edu.lclark.drosophila;

import java.io.File;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

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
	 * The action listener which clears the Analyzer's stored images and fly
	 * data.
	 */
	private class ClearImageAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			analyzerPanel.clearImages();
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
	 * The action listener that keeps track of when the DrawTrajectories button
	 * is clocked. Toggles whether or not fly trajectories are drawn.
	 */
	private class DrawTrajectoriesAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int startFrame = Integer.parseInt(firstFrame.getText());
				int endFrame = Integer.parseInt(lastFrame.getText());
				analyzerPanel.setDrawTrajectories(startFrame, endFrame);
			} catch (NumberFormatException error) {
				error.printStackTrace();
				System.exit(1);
			}
		}

	}

	/*
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
	private class SetThresholdAction implements ChangeListener {

		/**
		 *Event which sets the size threshold when the slider is moved 
		 *
		 */
		public void stateChanged(ChangeEvent e) {
			JSlider Source = (JSlider)e.getSource();
			if(!Source.getValueIsAdjusting()){
				analyzerPanel.sizeThresholdUpdate((int)Source.getValue());
			}
			if(Source.getValueIsAdjusting()){
				thresholdText.setText(""+(int)Source.getValue());				
			}
	}
	}
	/**
	 * The action listener that will adjust the Threshold as the textbox is changed
	 */
	private class setThresholdEntered implements TextListener{
		public void textValueChanged(TextEvent e){
			JTextField Source = (JTextField)e.getSource();
			String Text= Source.getText();
			try{
				int value=Integer.parseInt(Text);
				analyzerPanel.sizeThresholdUpdate(value);
				setThreshold.setValue(value);
			}
			catch(NumberFormatException E){
				E.getStackTrace();
				analyzerPanel.sizeThresholdUpdate(DEFAULT_SLIDER_THRESHOLD);
				setThreshold.setValue(DEFAULT_SLIDER_THRESHOLD);
			
				
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
	private JSlider setThreshold;

	/**
	 * The button which advances the displayed image one frame forward.
	 */
	private JButton forwardFrame;

	/**
	 * The button which advances the displayed images one frame backwards.
	 */
	private JButton backFrame;

	/**
	 * The button which detaches all added files from the analyzer.
	 */
	private JButton clearImages;

	/**
	 * The text field which lets the user specify what the Analyzer's size
	 * threshold is.
	 */
	private JTextField thresholdText;

	/**
	 * The button which lets the user toggle flydentifiers.
	 */
	private JButton drawFlydentifiers;

	/** Button that lets user toggle drawing trajectories */
	private JButton drawTrajectories;

	/** First frame to draw trajectories for */
	private JTextField firstFrame;

	/** Last frame to draw trajectories for */
	private JTextField lastFrame;

	/**
	 * The default preferred width of this panel.
	 */
	private static final int DEFAULT_WIDTH = 400;

	/**
	 * The default preferred height of this panel.
	 */
	private static final int DEFAULT_HEIGHT = 400;
	/**
	 * the default pixel threshold for the slider;
	 */
	private static final int DEFAULT_SLIDER_THRESHOLD = 25;
	/**
	 * The Highest possible value for the pixel threshold;
	 */
	private static final int MAX_SLIDER_THRESHHOLD= 200;
	/**
	 * The lowest possible value for the pixel theshold;
	 */
	private static final int MIN_SLIDER_THRESHOLD = 0;

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
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		fileChooser = new JFileChooser();
		getImage = new JButton("Open an Image");
		constraints.fill = constraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.weightx = 1;
		add(getImage, constraints);
		GetImageAction getImageAction = new GetImageAction(this);
		getImage.addActionListener(getImageAction);

		setThreshold = new JSlider(JSlider.HORIZONTAL,MIN_SLIDER_THRESHOLD,MAX_SLIDER_THRESHHOLD,DEFAULT_SLIDER_THRESHOLD);
		setThreshold.setMajorTickSpacing(50);
		setThreshold.setPaintLabels(true);
		setThreshold.setPaintTicks(true);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		add(setThreshold, constraints);
		SetThresholdAction setThresholdAction = new SetThresholdAction();
		setThreshold.addChangeListener(setThresholdAction);
		analyzerPanel.sizeThresholdUpdate(DEFAULT_SLIDER_THRESHOLD);

		thresholdText = new JTextField();
		thresholdText.setPreferredSize(new Dimension(100, 500));
		thresholdText.setText(""+DEFAULT_SLIDER_THRESHOLD);
		constraints.gridx = 3;
		constraints.gridy = 1;
		constraints.ipadx = 75;
		constraints.ipady = 10;
		constraints.gridwidth = 1;
		add(thresholdText, constraints);

		drawFlydentifiers = new JButton("Draw fly locations");
		constraints.fill = constraints.HORIZONTAL;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 4;
		add(drawFlydentifiers, constraints);
		DrawFlydentifiersAction drawFlydentifiersAction = new DrawFlydentifiersAction();
		drawFlydentifiers.addActionListener(drawFlydentifiersAction);

		drawTrajectories = new JButton("Draw fly trajectories");
		constraints.ipadx = 100;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		add(drawTrajectories, constraints);
		DrawTrajectoriesAction drawTrajectoriesAction = new DrawTrajectoriesAction();
		drawTrajectories.addActionListener(drawTrajectoriesAction);

		firstFrame = new JTextField("First frame");
		firstFrame.setPreferredSize(new Dimension(100, 50));
		constraints.fill = constraints.NONE;
		constraints.gridx = 2;
		constraints.gridwidth = 1;
		constraints.ipadx = 100;
		constraints.ipady = 10;
		add(firstFrame, constraints);

		lastFrame = new JTextField("Last frame");
		lastFrame.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 3;
		constraints.weightx = 1;
		add(lastFrame, constraints);

		backFrame = new JButton("\u25C0");
		constraints.fill = constraints.HORIZONTAL;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		add(backFrame, constraints);
		BackFrameAction backFrameAction = new BackFrameAction();
		backFrame.addActionListener(backFrameAction);

		forwardFrame = new JButton("\u25B6");
		constraints.fill = constraints.HORIZONTAL;
		constraints.gridx = 3;
		add(forwardFrame, constraints);
		ForwardFrameAction forwardFrameAction = new ForwardFrameAction();
		forwardFrame.addActionListener(forwardFrameAction);
		
		clearImages = new JButton("Clear all images");
		constraints.gridx = 0;
		constraints.gridwidth = 2;
		constraints.gridy = 4;
		constraints.fill = constraints.HORIZONTAL;
		add(clearImages, constraints);
		ClearImageAction clearImageAction = new ClearImageAction();
		clearImages.addActionListener(clearImageAction);

	}

	/**
	 * Returns the preferred size of this panel as a Dimension object.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/** Returns the minimum size of this panel as a Dimension object. */
	public Dimension getMinimumSize() {
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
