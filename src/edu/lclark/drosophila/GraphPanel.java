package edu.lclark.drosophila;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

public class GraphPanel extends JPanel {

	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 600;

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
		int rightMarg=35;
		int topMarg=50; 
		
		
		int xOffSet = leftMarg-3;
		int yOffSet = GPanelHeight - bottomMarg-3;
		
		double widthOffset = (GPanelWidth - (leftMarg+rightMarg))
				/ (double) (averageVelocity.length - 1);
		
		double gridxOffset = (GPanelWidth - (leftMarg+rightMarg)) / 10.0;
		double gridyOffset = (GPanelHeight -(topMarg+bottomMarg))/ 10.0; 
		double heightOffset = (double)(GPanelHeight - (topMarg+bottomMarg)) / maxVelocity;
		Graphics2D g2d = (Graphics2D) g;
		Font f =new Font("SansSarif", Font.PLAIN, 12);
		Rectangle2D bounds; 
		g.setFont(f);
		int stringWidth=0;
		
		DecimalFormat df= new DecimalFormat ("##.##");
		g.setColor(Color.LIGHT_GRAY);
		
		stringWidth=(int) f.getStringBounds("Time (in frames)", g2d.getFontRenderContext()).getWidth();
		g.drawString("Time (in frames)", (GPanelWidth-stringWidth+leftMarg-rightMarg)/2 , GPanelHeight-(bottomMarg/3));
		
		stringWidth=(int) f.getStringBounds("Average Velocity vs Time", g2d.getFontRenderContext()).getWidth();
		g.drawString("Average Velocity vs Time",(GPanelWidth-stringWidth)/2 , topMarg/3);
		
		for (int i = 0; i <= 10; i++) {
			g.drawLine((int)(gridxOffset * i) + leftMarg, GPanelHeight-bottomMarg,
					(int)(gridxOffset * i) + leftMarg, topMarg);
			g.drawLine(leftMarg,(int) (gridyOffset * i) + topMarg, GPanelWidth-rightMarg,
				(int)	(gridyOffset * i) + topMarg);

			g.drawString(""+df.format(maxVelocity-(i*maxVelocity/10.0)), leftMarg/2, (int)(gridyOffset * i) + topMarg);
			g.drawString(""+df.format(videoLength*i/10.0), (int)(gridxOffset * i) + leftMarg, GPanelHeight-(bottomMarg/2));

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
		g.setColor(Color.LIGHT_GRAY);
		JLabel butts = new JLabel("Average Velocity (in pixels per frame)");
		
		((Graphics2D)butts.getGraphics()).rotate(-Math.PI / 2, butts.getWidth()/2, butts.getHeight()/2);
		//VertDrawString("Average Velocity (in pixels per frame)", leftMarg/3,-(GPanelHeight+topMarg-bottomMarg)/2, g);
	}

	public void VertDrawString(String string, int x, int y, Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI /2, 0, 0);
		g2d.setTransform(at);
		
		Font f =new Font("SansSarif", Font.PLAIN, 12);
		Rectangle2D bounds; 
		g.setFont(f);
		String temp;
		int stringWidth=(int) f.getStringBounds(string, g2d.getFontRenderContext()).getWidth();
		
		for(int i= 0; i<=string.length()-1; i++){
			temp= string.substring(i,i+1);
			bounds=f.getStringBounds(temp, g2d.getFontRenderContext());
			g.drawString(temp, y-stringWidth/2, x);
			y+=bounds.getWidth();
		}
		
		at.setToRotation(0);
		g2d.setTransform(at);
		
	}
}
