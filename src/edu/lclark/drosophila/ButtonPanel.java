package edu.lclark.drosophila;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class ButtonPanel extends JPanel {

	private JFileChooser fc;
	private JButton getImage;
	private JButton setThreshold; 
	private JTextField thresholdText;

	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;

	private AnalyzerPanel a;

	public ButtonPanel(AnalyzerPanel a) {
		this.a = a;
		fc = new JFileChooser();
		getImage = new JButton("Open an Image");
		setThreshold = new JButton("Set fly size threshold (in pixels)");
		add(getImage);
		thresholdText = new JTextField();
		thresholdText.setPreferredSize(new Dimension(100, 50));
		add(setThreshold); 
		add(thresholdText);
		GetImageAction getImageAction = new GetImageAction(this);
		SetThresholdAction setThresholdAction = new SetThresholdAction();
		setThreshold.addActionListener(setThresholdAction);
		getImage.addActionListener(getImageAction);

	}

	public void passImage(File file) {
		a.passImage(file);
	}

	public void paintComponent(Graphics g) {

	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	private class SetThresholdAction implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			try {
				int testText= Integer.parseInt(thresholdText.getText());
			a.sizeThresholdUpdate(testText);
			}
			catch(NumberFormatException error){
				
			}
		}
	}
	
	private class GetImageAction implements ActionListener {

		private ButtonPanel bpanel;

		public GetImageAction(ButtonPanel bpanel) {
			this.bpanel = bpanel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int returnVal = fc.showOpenDialog(getImage);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				bpanel.passImage(file);
				a.repaint();
			}

		}	

	}
}

