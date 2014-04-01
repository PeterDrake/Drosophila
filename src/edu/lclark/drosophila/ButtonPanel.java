package edu.lclark.drosophila;

import java.io.File;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	 * The action listener which opens a file browsing window when the open movie
	 * button is clicked.
	 */
	private class OpenMovieAction implements ActionListener {

		private ButtonPanel bpanel; 
		
		public OpenMovieAction(ButtonPanel bpanel) { 
			this.bpanel = bpanel; 
		}
		
		/**
		 * Opens the file browsing window and passes the selected movie to the
		 * Button Panel when chosen.
		 */
		public void actionPerformed(ActionEvent e) {
			int returnVal = fileChooser.showOpenDialog(openMovie); 
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				bpanel.passMovie(file); 
				//analyzerPanel.repaint(); Maybe we need this? We shall see...  
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
	 * The button which detaches all added files from the analyzer.
	 */
	private JButton clearImages;
	
	/**
	 * This button opens and plays a movie.
	 */
	private JButton openMovie; 

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
		constraints.gridwidth = 2;
		constraints.weightx = 1;
		add(getImage, constraints);
		GetImageAction getImageAction = new GetImageAction(this);
		getImage.addActionListener(getImageAction);
		
		fileChooser = new JFileChooser(); // unsure if we need a new variable to open a movie vs. opening an image
		openMovie = new JButton("Open a movie"); 
		constraints.gridx = 2; 
		add(openMovie, constraints); 
		OpenMovieAction openMovieAction = new OpenMovieAction(this); 
		openMovie.addActionListener(openMovieAction); 
		
		
		setThreshold = new JButton("Set fly size threshold (in pixels)");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 3;
		add(setThreshold, constraints);
		SetThresholdAction setThresholdAction = new SetThresholdAction();
		setThreshold.addActionListener(setThresholdAction);

		thresholdText = new JTextField();
		thresholdText.setPreferredSize(new Dimension(100, 500));
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
	 * Daisy chain method to pass an opened movie file
	 * @param file
	 */
	public void passMovie(File file) {
		analyzerPanel.passMovie(file);
		
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
