package edu.lclark.drosophila;

import java.awt.*;
import java.io.File;
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
	private static final int DEFAULT_HEIGHT = 600;

	/**
	 * The boolean which keeps track of whether or not the identifying dots
	 * drawn over flies is toggled.
	 */
	private boolean flydentifiers;

	/**
	 * The AnalyzerPanel object that this ImagePanel communicates with.
	 */
	private AnalyzerPanel analyzerPanel;

	/**
	 * The index of the image that is currently being displayed. Used with
	 * Analyzer's Files array.
	 */
	private int imageIndex;

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
		imageIndex = 0;
	}

	/**
	 * Decrements the displayed image index by 1.
	 */
	public void decrementIndex() {
		imageIndex--;
	}

	/**
	 * Returns the preferred size of this panel as a Dimension object.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Increments the displayed image index by 1.
	 */
	public void incrementIndex() {
		imageIndex++;
	}

	/**
	 * Draws any components of this panel. It draws the selected image or frame,
	 * and red circles over any identified flies.
	 */
	public void paintComponent(Graphics g) {
		int totalImages = analyzerPanel.getTotalFrames();
		if (imageIndex < 0) {
			imageIndex = 0;
		} else if (imageIndex >= totalImages) {
			imageIndex = totalImages - 1;
		}
		String filePath = analyzerPanel.passdownImage(imageIndex);
		if (filePath != null) {
			Image image = new ImageIcon(filePath).getImage();
			g.drawImage(image, 0, 0, null);
			if (flydentifiers) {
				List<Fly> flies = analyzerPanel.getFlyList();
				int sizeFlies = flies.size();
				for (int i = 0; i < sizeFlies; i++) {
					g.setColor(Color.RED);
					g.drawOval((int) flies.get(i).getX(imageIndex) - 3,
							(int) flies.get(i).getY(imageIndex) - 3, 6, 6);
				}
			}
		}
	}

	/**
	 * Toggles whether or not the identifying red circles are drawn over
	 * identified flies.
	 */
	public void setFlydentifiers() {
		flydentifiers = !flydentifiers;
		analyzerPanel.repaint();
	}

	/**
	 * Sets this ImagePanel's displayed image index to the given
	 * 
	 * @param imageIndex
	 */
	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}
}
