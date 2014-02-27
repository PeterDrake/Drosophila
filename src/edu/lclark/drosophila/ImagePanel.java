package edu.lclark.drosophila;

import java.awt.*;
import java.util.EventListener;

import javax.swing.*;

public class ImagePanel extends JPanel {

	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	private int numberOfImages;
	private AnalyzerPanel a;

	public ImagePanel(AnalyzerPanel a) {
		this.a = a;
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public void paintComponent(Graphics g) {
		int index = 0;
		while (a.passdownImage(index) != null) {
			System.err.println("I am index" + index);
			Image image = new ImageIcon(a.passdownImage(index))
					.getImage();
			g.drawImage(image,(index * image.getWidth(null))+10, 0, null);
			index++;
		}
	}
}
