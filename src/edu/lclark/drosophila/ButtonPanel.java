package edu.lclark.drosophila;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class ButtonPanel extends JPanel {

	private class DrawFlydentifiersAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			a.setFlydentifiers();
		}
	}
	private class DrawTrajectoriesAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e){
			try {
				int startFrame = Integer.parseInt(firstFrame.getText());
				int endFrame = Integer.parseInt(lastFrame.getText());
				a.setDrawTrajectories(startFrame, endFrame);
			} catch (NumberFormatException error) {
				error.printStackTrace();
				System.exit(1);
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
	private class SetThresholdAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			try {
				int testText = Integer.parseInt(thresholdText.getText());
				a.sizeThresholdUpdate(testText);
			} catch (NumberFormatException error) {
				error.printStackTrace();
				System.exit(1);
			}

		}
	}
	private JFileChooser fc;
	private JButton getImage;
	private JButton setThreshold;
	private JTextField thresholdText;

	private JButton drawFlydentifiers;
	private JButton drawTrajectories;

	/** First frame to draw trajectories for */
	private JTextField firstFrame;

	/** Last frame to draw trajectories for */
	private JTextField lastFrame;

	private static final int DEFAULT_WIDTH = 500;

	private static final int DEFAULT_HEIGHT = 500;

	private AnalyzerPanel a;

	public ButtonPanel(AnalyzerPanel a) {
		this.a = a;
		fc = new JFileChooser();
		getImage = new JButton("Open an Image");
		setThreshold = new JButton("Set fly size threshold (in pixels)");
		drawFlydentifiers = new JButton("Draw fly locations");
		drawTrajectories = new JButton("Draw fly trajectories");
		firstFrame = new JTextField("First frame");
		firstFrame.setPreferredSize(new Dimension(100, 50));
		lastFrame = new JTextField("Last frame");
		lastFrame.setPreferredSize(new Dimension(100, 50));
		add(getImage);
		thresholdText = new JTextField();
		thresholdText.setPreferredSize(new Dimension(100, 50));
		add(setThreshold);
		add(thresholdText);
		add(drawFlydentifiers);
		add(drawTrajectories);
		add(firstFrame);
		add(lastFrame);

		GetImageAction getImageAction = new GetImageAction(this);
		SetThresholdAction setThresholdAction = new SetThresholdAction();
		DrawFlydentifiersAction drawFlydentifiersAction = new DrawFlydentifiersAction();
		DrawTrajectoriesAction drawTrajectoriesAction = new DrawTrajectoriesAction();

		setThreshold.addActionListener(setThresholdAction);
		getImage.addActionListener(getImageAction);
		drawFlydentifiers.addActionListener(drawFlydentifiersAction);
		drawTrajectories.addActionListener(drawTrajectoriesAction);
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public void paintComponent(Graphics g) {

	}
	
	public void passImage(File file) {
		a.passImage(file);
	}
}
