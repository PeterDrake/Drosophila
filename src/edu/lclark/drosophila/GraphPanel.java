package edu.lclark.drosophila;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

public class GraphPanel extends JPanel {

	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 300;

	private AnalyzerPanel analyzerPanel;

	public GraphPanel() {

	}

	public GraphPanel(AnalyzerPanel analyzerPanel) {
		this.analyzerPanel = analyzerPanel;
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public void paintComponent(Graphics g) {
		double averageVelocity[] = { 85, 56, 42, 3, 7, 11, 46, 36 }; // test data! use analyzerPanel.getAverageVelocity 								
		double frameRate = .10; // this may not be the real frameRate
		double videoLength = averageVelocity.length * frameRate;
		
		int GPanelWidth= this.getWidth();
		int GPanelHeight=this.getHeight();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GPanelWidth, GPanelHeight);

		g.setColor(Color.BLACK);

		double maxVelocity = 0;

		for (int i = 0; i < averageVelocity.length; i++) {
			if (averageVelocity[i] > maxVelocity) {
				maxVelocity = averageVelocity[i];
			}
		}
		
		int leftMarg=100;
		int bottomMarg=100;
		int rightMarg=25;
		int topMarg=50; 
		
		
		int xOffSet = leftMarg-3;
		int yOffSet = GPanelHeight - bottomMarg-3;
		
		double widthOffset = (GPanelWidth - (leftMarg+rightMarg))
				/ (double) (averageVelocity.length - 1);
		
		double gridxOffset = (GPanelWidth - (leftMarg+rightMarg)) / 10.0;
		double gridyOffset = (GPanelHeight -(topMarg+bottomMarg))/ 10.0; 
		double heightOffset = (double)(GPanelHeight - (topMarg+bottomMarg)) / maxVelocity;

		DecimalFormat df= new DecimalFormat ("##.##");
		g.setColor(Color.LIGHT_GRAY);
		
//		Graphics2D g2d = (Graphics2D) g;
//		AffineTransform at = new AffineTransform();
//		at.rotate(-Math.PI / 2.0, this.getWidth() / 2, this.getHeight() / 2);
//		g2d.setTransform(at);
//		g2d.drawString("Average Velocity (pixels per frame)", 10, 10);
//		
//		at.setToRotation(0);
//		g2d.setTransform(at);
		
		for (int i = 0; i <= 10; i++) {
			g.drawLine((int)(gridxOffset * i) + leftMarg, GPanelHeight-bottomMarg,
					(int)(gridxOffset * i) + leftMarg, topMarg);
			g.drawLine(leftMarg,(int) (gridyOffset * i) + topMarg, GPanelWidth-rightMarg,
				(int)	(gridyOffset * i) + topMarg);

			g.drawString(""+df.format(maxVelocity-(i*maxVelocity/10.0)), 15, (int)(gridyOffset * i) + topMarg);
			g.drawString(""+df.format(videoLength*i/10.0), (int)(gridxOffset * i) + leftMarg, GPanelHeight-15);

		}

		g.setColor(Color.BLACK);
		g.drawRect(leftMarg, topMarg, GPanelWidth - (leftMarg+rightMarg), GPanelHeight - (topMarg+bottomMarg));
		for (int i = 0; i < averageVelocity.length; i++) {
			g.fillOval((int) (xOffSet + i * widthOffset),
					(int) (yOffSet - averageVelocity[i] * heightOffset), 6, 6);
			if (i != 0) {
				g.drawLine(
						(int) (xOffSet + i * widthOffset + 3),
						(int) (yOffSet - averageVelocity[i] * heightOffset) + 3,
						(int) (xOffSet + (i - 1) * widthOffset + 3),
						(int) (yOffSet - averageVelocity[i - 1] * heightOffset + 3));
			}

		}
		
		

	}
}
