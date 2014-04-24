package edu.lclark.drosophila;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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

public class ButtonToolbar extends JMenuBar {

	/**
	 * The action listener which decrements the ImagePanel's displayed image
	 * index by 1 when the forward frame button is clicked.
	 */
	private class BackFrameAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			analyzerPanel.decrementIndex();
			analyzerPanel.repaint();
			imageIndex--;
		}
	}

	/**
	 * The action listener which clears the Analyzer's stored images and fly
	 * data.
	 */
	private class ClearImageAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			analyzerPanel.clearFlyGroups();
			analyzerPanel.clearImages();
			analyzerPanel.repaint();
			saveGraph.setEnabled(false);
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
	

	private class ClearFlyRegionsAction implements ActionListener {

		/**
		 * Tells the AnalyzerPanel clear fly regions
		 */
		public void actionPerformed(ActionEvent e) {
			System.out.println("this gets fired");
			analyzerPanel.clearFlyGroups();
			analyzerPanel.repaint();
		}
	}

	private class AnalyzeMovieAction implements ActionListener {

		/**
		 * Tells the AnalyzerPanel to toggle the flydentifiers.
		 */
		public void actionPerformed(ActionEvent e) {
			analyzerPanel.displayLoadingPopup("Movie is loading");
			new Thread(
				new Runnable() {
					public void run() {
						analyzerPanel.analyzeMovie(Integer.parseInt(sampleRate.getText()));		
						analyzerPanel.disposeLoadingDialog();
					}
				}
			).start();
			
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
				if(startFrame > endFrame) {
					analyzerPanel.displayMessagePopup(
							"Start frame must be smaller than end frame.");
					drawTrajectories.setSelected(false);
				} else if(startFrame <= 0 || endFrame <= 0){
					analyzerPanel.displayMessagePopup(
							"Frame numbers must be larger than 0.");
					drawTrajectories.setSelected(false);
				} else {
					analyzerPanel.setDrawTrajectories(startFrame, endFrame);
					drawTrajectories.setSelected(true);
				}
			} catch (NumberFormatException error) {
				analyzerPanel.displayMessagePopup(
						"Only enter whole numbers into the start and end frame boxes.");
				drawTrajectories.setSelected(false);
//				error.printStackTrace();
//				System.exit(1);
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
			imageIndex++;
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
				if(file.getName().endsWith(".png")||file.getName().endsWith(".jpg")
						||file.getName().endsWith(".jpeg")||file.getName().endsWith(".gif")
						||file.getName().endsWith(".bmp")) {					
					bpanel.passImage(file);
					analyzerPanel.repaint();
				} else {
					analyzerPanel.displayMessagePopup("Image must show file extension, and "
							+ "be a .png, .jpg, .jpeg, .gif, or .bmp.");
				}
			}
		}
	}
	
	/**
	 * The action listener which will set the arena ID of flies in the current
	 * rectangular selection.
	 */
	private class SetRegionsAction implements ActionListener {

		private ButtonToolbar bpanel;
		
		public SetRegionsAction(ButtonToolbar bpanel){
			this.bpanel = bpanel;
		}
		
		public void actionPerformed(ActionEvent e) {
			bpanel.analyzerPanel.passUpArenaParameters(Integer.parseInt(arenaID.getText()), imageIndex);
			bpanel.analyzerPanel.repaint();
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
				if(file.getName().endsWith(".mov")) {					
					bpanel.passMovie(file); 
				} else {
					analyzerPanel.displayMessagePopup("Movie must show file extension, "
							+ "and be a .mov file.");
				}
				//analyzerPanel.repaint(); Maybe we need this? We shall see...  
			}
			
		} 

		
	}
	
	private class SaveDataAction implements ActionListener {
		
		private ButtonToolbar bpanel;
		
		public SaveDataAction(ButtonToolbar bpanel){
			this.bpanel = bpanel;
		}
		
		public void actionPerformed(ActionEvent e) {
			String sample = bpanel.getDataForFile();
			JFileChooser fileChooser = new JFileChooser();
			int returnVal = fileChooser.showSaveDialog(saveData);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				try {
					FileWriter fileWriter = new FileWriter(fileChooser.getSelectedFile() + ".csv");
					fileWriter.write(sample.toString());
					fileWriter.close();
				}catch(Exception ex){
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
	private class GraphOptionsAction implements ActionListener {

		
		public GraphOptionsAction() { 
			
		}
		
		/**
		 * Opens the file browsing window and passes the selected movie to the
		 * Button Panel when chosen.
		 */
		public void actionPerformed(ActionEvent e) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					JFrame frame = new JFrame();
					frame.setTitle("Edit Graph");
					frame.setVisible(true);
					frame.add(new GraphOptionsPanel(analyzerPanel, frame));
					frame.setResizable(false);
					frame.pack();
				}
			});
		} 
	}
	
	private class SaveGraphAction implements ActionListener {

		
		public SaveGraphAction() { 
			
		}
		
		/**
		 * Opens the file browsing window and passes the selected movie to the
		 * Button Panel when chosen.
		 */
		public void actionPerformed(ActionEvent e) {
			int returnVal = fileChooser.showSaveDialog(saveGraph); 
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				analyzerPanel.saveGraph(file);
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
	
	private class SetRangeAction implements ChangeListener {

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
				
				analyzerPanel.sizeRangeUpdate((int) Source.getValue());
				rangeText.setText("" + (int) Source.getValue());
				analyzerPanel.repaint();
					}
				});
				
			}
			if (Source.getValueIsAdjusting()) {
				rangeText.setText("" + (int) Source.getValue());
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
				if(value>=MIN_SLIDER_THRESHOLD&&value<=MAX_PIXEL_THRESHOLD){
				setThreshold.setValue(value);
				}
				else{
					setThreshold.setValue(DEFAULT_SLIDER_THRESHOLD);
				}
			} catch (NumberFormatException E) {
				E.getStackTrace();
				//does nothing waits for a valid argument

			}
		}
	}

	
	private class SetRangeEntered implements DocumentListener {
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
				if(value>=MIN_SLIDER_THRESHOLD&&value<=MAX_PIXEL_THRESHOLD){
				setRange.setValue(value);
				}
				else{
					setRange.setValue(DEFAULT_SLIDER_THRESHOLD);
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
				if(value>=MIN_SLIDER_THRESHOLD&&value<=MAX_SLIDER_THRESHOLD){
				setContrastThreshold.setValue(value);
				}
				else{
					setContrastThreshold.setValue(DEFAULT_CONTRAST_THRESHOLD);
				}
			} catch (NumberFormatException E) {
				E.getStackTrace();
	//does nothing waits for valid argument 

			}
		}
	}

	private JMenu menuFile;
	
		/**
	 * The file browser which allows the user to choose a file which contains an
	 * image.
	 */
	private JFileChooser fileChooser;

	/**
	 * The button which lets the user specify an image to display and analyze.
	 */
	private JMenuItem getImage;

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
	private JMenuItem clearImages;
	
	/**
	 * This button opens and plays a movie.
	 */
	private JMenuItem openMovie; 
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
	private JCheckBoxMenuItem drawFlydentifiers;
	
	/** Button that lets user set areas of interest */
	private JMenuItem setRegions;
	
	/** Button that lets user clear all existing fly arenas*/
	private JMenuItem clearFlyRegions;
	
	/** Arena identification number */
	private JTextField arenaID;

	private JCheckBoxMenuItem drawTrajectories;

	/** First frame to draw trajectories for */
	private JTextField firstFrame;

	/** Last frame to draw trajectories for */
	private JTextField lastFrame;
	
	/**
	 * Current image index
	 */
	private int imageIndex;
	
	/**
	 * Button for analyzing the movie already opened
	 */
	private JMenuItem analyzeMovie;
	


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
	private static final int MAX_SLIDER_THRESHOLD = 255;
	
	private static final int MAX_PIXEL_THRESHOLD = 1000;
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
	
	private JMenu fileMenu;
	
	private JMenu editMenu;
	
	private JMenu drawMenu;
	
	private JMenu GroupsMenu;
	
	/**
	 * The AnalyzerPanel object that this ImagePanel communicates with.
	 */
	private AnalyzerPanel analyzerPanel;


	private JLabel totalFrames;

	private JLabel sampleRateLabel;

	private JTextField sampleRate;

	private JMenuItem graphOptions;

	private JMenuItem saveGraph;

	/**
	 * The constructor which initializes all fields and adds the buttons to this
	 * panel.
	 * @param editMenu 
	 * 
	 * @param analyzerPanel
	 *            the AnalyzerPanel object which this panel is attached to.
	 */

	private JMenuItem saveData;

	private JSlider setRange;

	private JTextField rangeText;
	public ButtonToolbar(AnalyzerPanel a) {
		this.analyzerPanel = a;
//		this.setLayout(new GridBagLayout());
//		GridBagConstraints constraints = new GridBagConstraints();

		fileMenu = new JMenu("File");
		this.add(fileMenu);//, constraints);
			
		
		fileChooser = new JFileChooser();
		getImage = new JMenuItem("Open an image");
		fileMenu.add(getImage);
		GetImageAction getImageAction = new GetImageAction(this);
		getImage.addActionListener(getImageAction);
	
		
		fileChooser = new JFileChooser(); // unsure if we need a new variable to open a movie vs. opening an image
		openMovie = new JMenuItem("Open a movie");
		fileMenu.add(openMovie);
		OpenMovieAction openMovieAction = new OpenMovieAction(this); 
		openMovie.addActionListener(openMovieAction); 
		
		analyzeMovie = new JMenuItem("Analyze the movie");
		fileMenu.add(analyzeMovie);
		AnalyzeMovieAction analyzeMovieAction = new AnalyzeMovieAction();
		analyzeMovie.addActionListener(analyzeMovieAction);

		fileMenu.addSeparator();
		
		saveGraph = new JMenuItem("Save graph to file");
		fileMenu.add(saveGraph);
		SaveGraphAction saveGraphAction = new SaveGraphAction();
		saveGraph.addActionListener(saveGraphAction);

		saveData = new JMenuItem("Save chart data to file");
		SaveDataAction saveDataAction = new SaveDataAction(this);
		saveData.addActionListener(saveDataAction);
		
		fileMenu.add(saveData);
		
		clearImages = new JMenuItem("Clear all images");
		ClearImageAction clearImageAction = new ClearImageAction();
		clearImages.addActionListener(clearImageAction);
		
		fileMenu.addSeparator();
		fileMenu.add(clearImages);

		editMenu = new JMenu("Edit");
		this.add(editMenu);
		
		SliderLabel = new JLabel("Pixel Threshold");
		editMenu.add(SliderLabel);
		
		setThreshold = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER_THRESHOLD,
				MAX_PIXEL_THRESHOLD, DEFAULT_SLIDER_THRESHOLD);
		setThreshold.setMajorTickSpacing(250);
		setThreshold.setMinorTickSpacing(50);
		setThreshold.setPaintLabels(true);
		setThreshold.setPaintTicks(true);
		setThreshold.setToolTipText("Sets the Pixel Threshold");
		editMenu.add(setThreshold);
		SetThresholdAction setThresholdAction = new SetThresholdAction();
		setThreshold.addChangeListener(setThresholdAction);
		analyzerPanel.sizeThresholdUpdate(DEFAULT_SLIDER_THRESHOLD);
		
		thresholdText = new JTextField("0");
		thresholdText.setPreferredSize(new Dimension(100, 25));
		thresholdText.setText("" + DEFAULT_SLIDER_THRESHOLD);
		SetThresholdEntered setThresholdEntered = new SetThresholdEntered();
		thresholdText.getDocument().addDocumentListener(setThresholdEntered);
		
		editMenu.add(thresholdText);
		
		editMenu.add(new JLabel("Pixel Range"));
		setRange = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER_THRESHOLD,
				MAX_PIXEL_THRESHOLD, DEFAULT_SLIDER_THRESHOLD);
		setRange.setMajorTickSpacing(250);
		setRange.setMinorTickSpacing(50);
		setRange.setPaintLabels(true);
		setRange.setPaintTicks(true);
		setRange.setToolTipText("Sets the Pixel Range");
		editMenu.add(setRange);
		SetRangeAction setRangeAction = new SetRangeAction();
		setRange.addChangeListener(setRangeAction);
		
		rangeText = new JTextField("0");
		rangeText.setPreferredSize(new Dimension(100, 25));
		rangeText.setText("" + DEFAULT_SLIDER_THRESHOLD);
		SetRangeEntered setRangeEntered = new SetRangeEntered();
		rangeText.getDocument().addDocumentListener(setRangeEntered);
		
		editMenu.add(rangeText);
		
		editMenu.addSeparator();
		
		ContrastLabel = new JLabel("Contrast Threshold");
		
		editMenu.add(ContrastLabel);
		
		setContrastThreshold = new JSlider(JSlider.HORIZONTAL, MIN_SLIDER_THRESHOLD,MAX_SLIDER_THRESHOLD,DEFAULT_CONTRAST_THRESHOLD);
		setContrastThreshold.setMajorTickSpacing(50);
		setContrastThreshold.setMinorTickSpacing(10);
		setContrastThreshold.setPaintLabels(true);
		setContrastThreshold.setPaintTicks(true);
		setContrastThreshold.setToolTipText("Sets the contrast threshold");
		SetContrastThresholdAction setContrastThresholdAction = new SetContrastThresholdAction();
		setContrastThreshold.addChangeListener(setContrastThresholdAction);
		
		editMenu.add(setContrastThreshold);
		
		contrastThresholdText = new JTextField("200");
		contrastThresholdText.setPreferredSize(new Dimension(100, 25));
		SetContrastEntered setContrastEntered = new SetContrastEntered();
		contrastThresholdText.getDocument().addDocumentListener(setContrastEntered);
		
		editMenu.add(contrastThresholdText);
		
		editMenu.addSeparator();
		
		editMenu.add(new JLabel("Image Contrast"));
		//analyzerPanel.sizeImageContrastUpdate(1.0);
		
		setImageContrast = new JSlider(JSlider.HORIZONTAL, 10, 30, 10);
		setImageContrast.setToolTipText("Sets Image Contrast");
		setImageContrast.setMajorTickSpacing(5);
		SetImageContrastAction setImageContrastAction = new SetImageContrastAction();
		setImageContrast.addChangeListener(setImageContrastAction);
		
		editMenu.add(setImageContrast);

		editMenu.addSeparator();
		
		sampleRateLabel = new JLabel("Sample Rate");
		
		editMenu.add(sampleRateLabel);
		
		sampleRate = new JTextField("1");
		sampleRate.setPreferredSize(new Dimension(100, 25));
		sampleRate.setToolTipText("Sample every nth frame of the movie");
		sampleRate.setEnabled(false);
		
		editMenu.add(sampleRate);
		
		editMenu.addSeparator();
		graphOptions = new JMenuItem("Edit the graph axes");
		GraphOptionsAction graphOptionsAction = new GraphOptionsAction();
		graphOptions.addActionListener(graphOptionsAction);
		editMenu.add(graphOptions);
		
		
		drawMenu = new JMenu("Draw");
		this.add(drawMenu);
		
		drawFlydentifiers = new JCheckBoxMenuItem(new ImageIcon(getClass().getResource("images/flydentify.png")));
		drawFlydentifiers.setToolTipText("Draw fly locations");
		DrawFlydentifiersAction drawFlydentifiersAction = new DrawFlydentifiersAction();
		drawFlydentifiers.addActionListener(drawFlydentifiersAction);
		drawMenu.add(drawFlydentifiers);
		
		GroupsMenu = new JMenu("Areas");
		this.add(GroupsMenu);
		
		setRegions = new JMenuItem("Set Area of Interest");
		setRegions.setToolTipText("sets the currently selecteded area as an Arena, setting those flies to the group in the box");
		setRegions.addActionListener(new SetRegionsAction(this));
		GroupsMenu.add(setRegions);
		
	
		
		clearFlyRegions = new JMenuItem("Clear All Areas of Interest");
		clearFlyRegions.setToolTipText("Clears all regions of interest, and sets all flies to group 0");
		clearFlyRegions.addActionListener(new ClearFlyRegionsAction());
		GroupsMenu.add(clearFlyRegions);
		
		arenaID = new JTextField("1");
		arenaID.setToolTipText("insert a number to label all the flies in the region with");
		GroupsMenu.add(arenaID);
		
		
		drawTrajectories = new JCheckBoxMenuItem(new ImageIcon(getClass().getResource("images/DrawFlyTrajectoriesToggle.png")));
		drawTrajectories.setToolTipText("Draw fly trajectories");
		DrawTrajectoriesAction drawTrajectoriesAction = new DrawTrajectoriesAction();
		drawTrajectories.addActionListener(drawTrajectoriesAction);
		drawMenu.add(drawTrajectories);

		firstFrame = new JTextField("First frame");
		firstFrame.setPreferredSize(new Dimension(75, 25));
		firstFrame.setMaximumSize(new Dimension(75, 25));
		firstFrame.setToolTipText("First frame to draw trajectories");
		this.add(firstFrame);

		lastFrame = new JTextField("Last frame");
		lastFrame.setPreferredSize(new Dimension(75, 25));
		lastFrame.setMaximumSize(new Dimension(75, 25));
		lastFrame.setToolTipText("Last frame to draw trajectories");
		this.add(lastFrame);

		backFrame = new JButton("\u25C0");
		backFrame.setToolTipText("Move back one frame");
		BackFrameAction backFrameAction = new BackFrameAction();
		backFrame.addActionListener(backFrameAction);
		this.add(backFrame);

		forwardFrame = new JButton("\u25B6");
		forwardFrame.setToolTipText("Move forward one frame");
		ForwardFrameAction forwardFrameAction = new ForwardFrameAction();
		forwardFrame.addActionListener(forwardFrameAction);
		this.add(forwardFrame);

		totalFrames = new JLabel("0");
		totalFrames.setPreferredSize(new Dimension(75, 25));
		totalFrames.setMaximumSize(new Dimension(75, 25));
		totalFrames.setToolTipText("Total frames in the movie");
		this.add(totalFrames);

	}

	
	public String getDataForFile() {
		return analyzerPanel.getDataForFile();
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
		analyzeMovie.setEnabled(analyzerPanel.getMovieLoaded());
		totalFrames.setText(""+analyzerPanel.getTotalFrames());
		sampleRate.setEnabled(analyzerPanel.getMovieLoaded());
		saveGraph.setEnabled(analyzerPanel.getFlyList().size() > 0);
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

