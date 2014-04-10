package edu.lclark.drosophila;

import java.io.File;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class ButtonToolbar extends JToolBar {

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

		private ButtonToolbar bpanel;

		public GetImageAction(ButtonToolbar bpanel) {
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

		private ButtonToolbar bpanel; 
		
		public OpenMovieAction(ButtonToolbar bpanel) { 
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
	private class SetThresholdAction implements ChangeListener {

		/**
		 * Event which sets the size threshold when the slider is moved
		 * 
		 */
		public void stateChanged(ChangeEvent e) {
			final JSlider Source = (JSlider) e.getSource();
			if (!Source.getValueIsAdjusting()) {
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run(){
				
				analyzerPanel.sizeThresholdUpdate((int) Source.getValue());
				thresholdText.setText("" + (int) Source.getValue());
				analyzerPanel.repaint();
					}
				});
				
			}
			if (Source.getValueIsAdjusting()) {
				thresholdText.setText("" + (int) Source.getValue());
			}
		}
	}

	/**
	 * The action listener that will adjust the Threshold as the textbox is
	 * changed
	 */
	private class SetThresholdEntered implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {
			reportchange(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			reportchange(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			reportchange(e);
		}

		public void reportchange(DocumentEvent e) {
			Document Source = e.getDocument();
			String text = "25";
			try {
				text = Source.getText(0, Source.getLength());
			} catch (BadLocationException e1) {
				e1.getStackTrace();
				System.exit(1);
				}

		
			try {
				int value = Integer.parseInt(text);
				if(value>=MIN_SLIDER_THRESHOLD&&value<=MAX_SLIDER_THRESHHOLD){
				analyzerPanel.sizeThresholdUpdate(value);
				setThreshold.setValue(value);
				}
				else{
					analyzerPanel.sizeThresholdUpdate(DEFAULT_SLIDER_THRESHOLD);
					setThreshold.setValue(DEFAULT_SLIDER_THRESHOLD);
				}
			} catch (NumberFormatException E) {
				E.getStackTrace();
				//does nothing waits for a valid argument

			}
		}
	}

	
	/**
	 * The action listener which changes the Analyzer's size threshold when the
	 * size threshold button is clicked.
	 */
	private class SetImageContrastAction implements ChangeListener {

		/**
		 *Event which sets the size threshold when the slider is moved 
		 *
		 */
		public void stateChanged(ChangeEvent e) {
			JSlider Source = (JSlider)e.getSource();
			if(!Source.getValueIsAdjusting()){
				analyzerPanel.setImageContrast((double)Source.getValue()/10.0);
				//System.err.println(Source.getValue() + " : " + (double)Source.getValue()/10.0);
				analyzerPanel.repaint();
			}
		}
	}

	private class SetContrastThresholdAction implements ChangeListener{


		/**
		 * Event which sets the size threshold when the slider is moved
		 * 
		 */
		public void stateChanged(ChangeEvent e) {
			final JSlider Source = (JSlider) e.getSource();
			if (!Source.getValueIsAdjusting()) {
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run(){
						analyzerPanel.contrastThresholdUpdate((int) Source.getValue());
						contrastThresholdText.setText("" + (int) Source.getValue());
						analyzerPanel.repaint();
					}
				});
			}
			if (Source.getValueIsAdjusting()) {
				contrastThresholdText.setText("" + (int) Source.getValue());
			}
		}

	}
	private class SetContrastEntered implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {
			reportchange(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			reportchange(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			reportchange(e);
		}

		public void reportchange(DocumentEvent e) {
			Document Source = e.getDocument();
			String text = "200";
			try {
				text = Source.getText(0, Source.getLength());
			} catch (BadLocationException e1) {
				e1.getStackTrace();
				System.exit(1);
				}


			try {
				int value = Integer.parseInt(text);
				if(value>=MIN_SLIDER_THRESHOLD&&value<=MAX_SLIDER_THRESHHOLD){
				analyzerPanel.contrastThresholdUpdate(value);
				setContrastThreshold.setValue(value);
				}
				else{
					analyzerPanel.contrastThresholdUpdate(DEFAULT_CONTRAST_THRESHOLD);
					setContrastThreshold.setValue(DEFAULT_CONTRAST_THRESHOLD);
				}
			} catch (NumberFormatException E) {
				E.getStackTrace();
	//does nothing waits for valid argument 

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
	 * The button which lets the user specify what the Analyzer's contrast threshold
	 * is.
	 */
	private JSlider setContrastThreshold;


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
	 * A slider for editing the contrast of the actual image
	 */
	 
	private JSlider setImageContrast;


	/**
	 * The text field which lets the user specify what the Analyzer's size
	 * threshold is.
	 */
	private JTextField thresholdText;
	
	/**
	 * The text field which lets the user specify what the Analyzer's contrast
	 * threshold is.
	 */
	private JTextField contrastThresholdText;

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
	private static final int DEFAULT_WIDTH = 1000;

	/**
	 * The default preferred height of this panel.
	 */
	private static final int DEFAULT_HEIGHT = 20;
	/**
	 * the default pixel threshold for the slider;
	 */
	private static final int DEFAULT_SLIDER_THRESHOLD = 25;
	/**
	 * The Highest possible value for the pixel threshold;
	 */
	private static final int MAX_SLIDER_THRESHHOLD = 255;
	/**
	 * The lowest possible value for the pixel theshold;
	 */
	private static final int MIN_SLIDER_THRESHOLD = 0;
	/**
	 * The label for the Slider
	 */
	private static final int DEFAULT_CONTRAST_THRESHOLD=200;
	
	private JLabel SliderLabel;
	
	private JLabel ContrastLabel;
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
	public ButtonToolbar(AnalyzerPanel a) {
		this.analyzerPanel = a;
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		fileChooser = new JFileChooser();
		getImage = new JButton("Open an Image");
		constraints.fill = constraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 1;
		add(getImage, constraints);
		GetImageAction getImageAction = new GetImageAction(this);
		getImage.addActionListener(getImageAction);
		
		fileChooser = new JFileChooser(); // unsure if we need a new variable to open a movie vs. opening an image
		openMovie = new JButton("Open a movie"); 
		constraints.gridx = 1; 
		add(openMovie, constraints); 
		OpenMovieAction openMovieAction = new OpenMovieAction(this); 
		openMovie.addActionListener(openMovieAction); 
		

		setThreshold = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER_THRESHOLD,
				MAX_SLIDER_THRESHHOLD, DEFAULT_SLIDER_THRESHOLD);
		setThreshold.setMajorTickSpacing(50);
		setThreshold.setMinorTickSpacing(10);
		setThreshold.setPaintLabels(true);
		setThreshold.setPaintTicks(true);
		setThreshold.setToolTipText("Sets the Pixel Threshold");
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(setThreshold, constraints);
		SetThresholdAction setThresholdAction = new SetThresholdAction();
		setThreshold.addChangeListener(setThresholdAction);
		analyzerPanel.sizeThresholdUpdate(DEFAULT_SLIDER_THRESHOLD);
		
		SliderLabel = new JLabel("Pixel Threshold");
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.weightx = 1;
		constraints.gridwidth = 1;
		add(SliderLabel,constraints);
		
		ContrastLabel = new JLabel("Contrast Threshold");
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.weightx = 1;
		constraints.gridwidth = 1;
		add(ContrastLabel,constraints);
		
		thresholdText = new JTextField("0");
		thresholdText.setPreferredSize(new Dimension(100, 500));
		thresholdText.setText("" + DEFAULT_SLIDER_THRESHOLD);
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.ipadx = 75;
		constraints.ipady = 10;
		constraints.gridwidth = 1;
		SetThresholdEntered setThresholdEntered = new SetThresholdEntered();
		thresholdText.getDocument().addDocumentListener(setThresholdEntered);
		add(thresholdText, constraints);
		
		setImageContrast = new JSlider(JSlider.HORIZONTAL, 10, 30, 10);
		setImageContrast.setToolTipText("Sets Image Contrast");
		setImageContrast.setMajorTickSpacing(5);
//		setImageContrast.setPaintLabels(true);
//		setImageContrast.setPaintTicks(false);
		constraints.gridx = 6;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(setImageContrast, constraints);
		constraints.gridx = 7;
		constraints.gridwidth = 1;
		add(new JLabel("Image Contrast"), constraints);
		SetImageContrastAction setImageContrastAction = new SetImageContrastAction();
		setImageContrast.addChangeListener(setImageContrastAction);
		//analyzerPanel.sizeImageContrastUpdate(1.0);

		setContrastThreshold = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER_THRESHOLD,MAX_SLIDER_THRESHHOLD,DEFAULT_CONTRAST_THRESHOLD);
		setContrastThreshold.setMajorTickSpacing(50);
		setContrastThreshold.setMinorTickSpacing(10);
		setContrastThreshold.setPaintLabels(true);
		setContrastThreshold.setPaintTicks(true);
		setContrastThreshold.setToolTipText("Sets the contrast threshold");
		constraints.gridx = 8;
		constraints.gridy = 0;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridwidth = 1;
		add(setContrastThreshold, constraints);
		SetContrastThresholdAction setContrastThresholdAction = new SetContrastThresholdAction();
		setContrastThreshold.addChangeListener(setContrastThresholdAction);
		
		contrastThresholdText = new JTextField("200");
		contrastThresholdText.setPreferredSize(new Dimension(100, 500));
		constraints.gridx = 9;
		constraints.gridy = 0;
		constraints.ipadx = 75;
		constraints.ipady = 10;
		constraints.gridwidth = 1;
		add(contrastThresholdText, constraints);
		SetContrastEntered setContrastEntered = new SetContrastEntered();
		contrastThresholdText.getDocument().addDocumentListener(setContrastEntered);
		
		drawFlydentifiers = new JButton("Draw fly locations");
		constraints.fill = constraints.HORIZONTAL;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridx = 10;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(drawFlydentifiers, constraints);
		DrawFlydentifiersAction drawFlydentifiersAction = new DrawFlydentifiersAction();
		drawFlydentifiers.addActionListener(drawFlydentifiersAction);

		drawTrajectories = new JButton("Draw fly trajectories");
		constraints.ipadx = 100;
		constraints.gridx = 11;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(drawTrajectories, constraints);
		DrawTrajectoriesAction drawTrajectoriesAction = new DrawTrajectoriesAction();
		drawTrajectories.addActionListener(drawTrajectoriesAction);

		firstFrame = new JTextField("First frame");
		firstFrame.setPreferredSize(new Dimension(100, 50));
		constraints.fill = constraints.NONE;
		constraints.gridx = 12;
		constraints.gridwidth = 1;
		constraints.ipadx = 100;
		constraints.ipady = 10;
		add(firstFrame, constraints);

		lastFrame = new JTextField("Last frame");
		lastFrame.setPreferredSize(new Dimension(100, 50));
		constraints.gridx = 13;
		constraints.weightx = 1;
		add(lastFrame, constraints);

		backFrame = new JButton("\u25C0");
		constraints.fill = constraints.HORIZONTAL;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		constraints.gridx = 14;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		add(backFrame, constraints);
		BackFrameAction backFrameAction = new BackFrameAction();
		backFrame.addActionListener(backFrameAction);

		forwardFrame = new JButton("\u25B6");
		constraints.fill = constraints.HORIZONTAL;
		constraints.gridx = 15;
		add(forwardFrame, constraints);
		ForwardFrameAction forwardFrameAction = new ForwardFrameAction();
		forwardFrame.addActionListener(forwardFrameAction);

		clearImages = new JButton("Clear all images");
		constraints.gridx = 16;
		constraints.gridwidth = 2;
		constraints.gridy = 0;
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
//	public Dimension getPreferredSize() {
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//	}

	/** Returns the minimum size of this panel as a Dimension object. */
//	public Dimension getMinimumSize() {
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//	}

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
