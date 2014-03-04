package edu.lclark.drosophila;

import java.awt.*;
import java.util.EventListener;
import java.util.List;

import javax.swing.*;

public class ImagePanel extends JPanel {

	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;

	private boolean flydentifiers = false;

	private int numberOfImages;

	private AnalyzerPanel a;

	public ImagePanel(AnalyzerPanel a) {
		this.a = a;
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public void setFlydentifiers() {
		flydentifiers = !flydentifiers;
		a.repaint();
	}

	public void paintComponent(Graphics g) {
		int index = 0;
		int imageOffset=0;
		while (a.passdownImage(index) != null) {
			Image image = new ImageIcon(a.passdownImage(index)).getImage();
			g.drawImage(image, imageOffset, 0, null);
			if (flydentifiers) {
				List<Fly> flies = a.getFlyList();
				int sizeFlies = flies.size();
				for (int i = 0; i < sizeFlies; i++) {
					g.setColor(Color.RED);
					g.drawOval(imageOffset + ((int) flies.get(i).getX(index) - 3),
							(int) flies.get(i).getY(index) - 3, 6, 6);
				}
			}
			index++;
			imageOffset+=image.getWidth(null)+10;
		}
	}
}
