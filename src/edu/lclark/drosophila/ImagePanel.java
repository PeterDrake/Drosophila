package edu.lclark.drosophila;

import java.awt.*;
import java.util.List;

import javax.swing.*;

public class ImagePanel extends JPanel {

	/**
	 * The default preferred width of this panel.
	 */
	private static final int DEFAULT_WIDTH = 800;

	/**
	 * The default preferred height of this panel.
	 */
	private static final int DEFAULT_HEIGHT = 800;

	/**
	 * The boolean which keeps track of whether or not the identifying dots
	 * drawn over flies is toggled.
	 */
	private boolean flydentifiers;

	/** Boolean that keeps track of whether or not the trajectories are drawn. */
	private boolean drawTrajectories;

	/** First frame to draw trajectories for */
	private int firstFrame;
	/** Last frame to draw trajectories for */
	private int lastFrame;

	private int numberOfImages;

	/**
	 * The AnalyzerPanel object that this ImagePanel communicates with.
	 */
	private AnalyzerPanel analyzerPanel;

	/**
	 * The constructor which sets the AnalyzerPanel this ImagePanel is connected
	 * to.
	 * 
	 * @param anayzerPanel
	 *            the AnalyzerPanel which this ImagePanel is connected to.
	 */
	public ImagePanel(AnalyzerPanel a) {
		this.analyzerPanel = a;
		flydentifiers = false;
	}

	/**
	 * Returns the preferred size of this panel as a Dimension object.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Draws any components of this panel. It draws the selected image or frame,
	 * and red circles over any identified flies.
	 */
	public void paintComponent(Graphics g) {
		int index = 0;
		int imageOffset = 0;
		while (analyzerPanel.passdownImage(index) != null) {
			Image image = new ImageIcon(analyzerPanel.passdownImage(index))
					.getImage();
			g.drawImage(image, imageOffset, 0, null);
			if (flydentifiers) {
				List<Fly> flies = analyzerPanel.getFlyList();
				int sizeFlies = flies.size();
				for (int i = 0; i < sizeFlies; i++) {
					g.setColor(Color.RED);
					g.drawOval(imageOffset
							+ ((int) flies.get(i).getX(index) - 3), (int) flies
							.get(i).getY(index) - 3, 6, 6);
				}
			}
			if (drawTrajectories) {
				g.setColor(Color.RED);
				List<Fly> flies = analyzerPanel.getFlyList();
				for (Fly fly : flies) {
					for (int i = firstFrame; i < lastFrame; i++) {
						int x1 = (int) fly.getX(i) - 3;
						int y1 = (int) fly.getY(i) - 3;
						int x2 = (int) fly.getX(i + 1) - 3;
						int y2 = (int) fly.getY(i + 1) - 3;
						//if(x1 > -3 && y1 > -3 && x2 > -3 && y2 > -3){//doesn't draw flies that don't appear in both frames
							g.drawLine(x1, y1, x2, y2);
						//}
						/*
						 * g.drawLine((int) fly.getX(i) - 3, (int)
						 * fly.getY(i)-3, (int) fly.getX(i + 1)-3, (int)
						 * fly.getY(i + 1)-3);
						 */
					}
				}
			}
			index++;
			imageOffset += image.getWidth(null) + 10;
		}
	}

	/**
	 * Toggles whether or not fly trajectories are drawn over identified flies,
	 * will draw their paths from startFrame to endFrame. Input is frame values
	 * starting at 1 for ease of use, this method converts that to frame values
	 * starting at 0 to match implementation.
	 */
	public void setDrawTrajectories(int startFrame, int endFrame) {
		drawTrajectories = !drawTrajectories;
		firstFrame = startFrame - 1;
		lastFrame = endFrame - 1;
		analyzerPanel.repaint();
	}

	/**
	 * Toggles whether or not the identifying red circles are drawn over
	 * identified flies.
	 */
	public void setFlydentifiers() {
		flydentifiers = !flydentifiers;
		analyzerPanel.repaint();
	}
}
