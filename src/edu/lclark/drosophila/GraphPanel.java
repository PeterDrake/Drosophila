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
	
	private double[] averageVelocity;
	
	private double frameRate;
	
	private double videoLength;
	
	private String title;
	
	private String yLabel;
	
	private String xLabel;
	
	private Color color;
	
	
	private boolean drawPoints;

	public GraphPanel() {
		drawPoints = false;
		double tempAverageVelocity[] = { 85, 56, 42, 3, 7, 11, 46, 36 }; // test data! use analyzerPanel.getAverageVelocity 			
		averageVelocity = tempAverageVelocity;
		frameRate = .10; // this may not be the real frameRate
		videoLength = averageVelocity.length * frameRate;
		xLabel = "Time in frames";
		yLabel = "Average Velocity (in pixels per frame)";
		title = "Average Velocity vs Time";
		color = Color.BLACK;
	}

	public GraphPanel(AnalyzerPanel analyzerPanel, boolean drawPoints, double frameRate, String title, String yLabel, String xLabel) {
		this.analyzerPanel = analyzerPanel;
		this.drawPoints = drawPoints;
		this.frameRate = frameRate;
		this.title = title;
		this.yLabel = yLabel;
		this.xLabel = xLabel;
		color = Color.RED;
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public void setDrawPoints(boolean drawPoints) {
		this.drawPoints = drawPoints;
	}
	
	/** Returns the minimum size of this panel as a Dimension object */
	public Dimension getMinimumSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public void paintComponent(Graphics g) {	
		g.setColor(Color.WHITE);
		int GPanelWidth= this.getWidth();
		int GPanelHeight=this.getHeight();
		g.fillRect(0, 0, GPanelWidth, GPanelHeight);
		if(analyzerPanel.getFlyList().size() <= 0){
			return;
		}
		averageVelocity = analyzerPanel.getAverageVelocity();
		
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
		
		double gridxOffset = 40.0;//(GPanelWidth - (leftMarg+rightMarg)) / 10.0;
		double gridyOffset = 40.0;//(GPanelHeight -(topMarg+bottomMarg))/ 10.0; 
		double heightOffset = (double)(GPanelHeight - (topMarg+bottomMarg)) / maxVelocity;
		Graphics2D g2d = (Graphics2D) g;
		Font f =new Font("SansSarif", Font.PLAIN, 12);
		Rectangle2D bounds; 
		g.setFont(f);
		int stringWidth=0;
		
		DecimalFormat df= new DecimalFormat ("##.##");
		g.setColor(Color.LIGHT_GRAY);
		
		stringWidth=(int) f.getStringBounds(xLabel, g2d.getFontRenderContext()).getWidth();
		g.drawString(xLabel, (GPanelWidth-stringWidth+leftMarg-rightMarg)/2 , GPanelHeight-(bottomMarg/3));
		
		stringWidth=(int) f.getStringBounds(title, g2d.getFontRenderContext()).getWidth();
		g.drawString(title,(GPanelWidth-stringWidth)/2 , topMarg/3);
		
		// drawing the grid lines and the data for the grid lines
		double d = videoLength/((GPanelWidth-(leftMarg+rightMarg))/gridxOffset);
		for (int i = 0; i*gridxOffset <= GPanelWidth - (leftMarg + rightMarg); i++) {
			g.drawLine((int)(gridxOffset * i) + leftMarg, GPanelHeight-bottomMarg,
					(int)(gridxOffset * i) + leftMarg, topMarg);
			
			g.drawString(""+df.format(d*i), (int)(gridxOffset * i) + leftMarg, GPanelHeight-(bottomMarg/2));
		}
		
		d = maxVelocity/((GPanelHeight-(topMarg+bottomMarg))/gridyOffset);
		for( int i = 0; i*gridyOffset <= GPanelHeight - (topMarg + bottomMarg); i++) {
			g.drawLine(leftMarg,(int) -(gridyOffset * i) + GPanelHeight - bottomMarg, GPanelWidth-rightMarg,
					(int)	-(gridyOffset * i) + GPanelHeight - bottomMarg);
			
			g.drawString(""+df.format(d*i), leftMarg/2, (int)(-gridyOffset * i) + GPanelHeight - bottomMarg);
		}

		g.setColor(Color.BLACK);
		g.drawRect(leftMarg, topMarg, GPanelWidth - (leftMarg+rightMarg), GPanelHeight - (topMarg+bottomMarg));
		g.setColor(color);
		for (int i = 0; i < averageVelocity.length; i++) {
			
			// draw the points
			if(drawPoints) {
				g.fillOval((int) (xOffSet + i * widthOffset),(int) (yOffSet - averageVelocity[i] * heightOffset), 6, 6);
			}
			if (i != 0) {
				g.drawLine(
						(int) (xOffSet + i * widthOffset + 3),
						(int) (yOffSet - averageVelocity[i] * heightOffset) + 3,
						(int) (xOffSet + (i - 1) * widthOffset + 3),
						(int) (yOffSet - averageVelocity[i - 1] * heightOffset + 3));
			}

		}
		g.setColor(Color.LIGHT_GRAY);
		//JLabel butts = new JLabel("Average Velocity (in pixels per frame)");
		
		//((Graphics2D)butts.getGraphics()).rotate(-Math.PI / 2, butts.getWidth()/2, butts.getHeight()/2);
	//	VertDrawString(yLabel, leftMarg/3,-(GPanelHeight+topMarg-bottomMarg)/2, g);
		//Graphics2D g2d = (Graphics2D) g;
		//AffineTransform at = new AffineTransform();
//		at.setToRotation(-Math.PI/2.0, GPanelWidth/2.0, GPanelHeight/2.0);
//		g2d.setTransform(at);
		g2d.rotate(-Math.PI/2.0);
		g2d.translate(-GPanelHeight,0);
		stringWidth=(int) f.getStringBounds(yLabel, g2d.getFontRenderContext()).getWidth();
		//g.drawString(yLabel, (GPanelWidth-stringWidth+leftMarg-rightMarg)/2 , GPanelHeight-(bottomMarg/3));
		g2d.drawString(yLabel,(GPanelHeight-topMarg+bottomMarg-stringWidth)/2, leftMarg/3);
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
