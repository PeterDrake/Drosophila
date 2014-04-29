package edu.lclark.drosophila;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class GraphPanel extends JPanel {

	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 600;

	private AnalyzerPanel analyzerPanel;
	
	private double[][] averageVelocity;
	
	private double frameRate;
	
	private double videoLength;
	
	private String title;
	
	private String yLabel;
	
	private String xLabel;
	
	private Color color;
	
	
	private boolean drawPoints;
	private int startFrame;
	private int endFrame;

//	public GraphPanel() {
//		drawPoints = false;
//		double tempAverageVelocity[] = { 85, 56, 42, 3, 7, 11, 46, 36 }; // test data! use analyzerPanel.getAverageVelocity 			
//		averageVelocity = tempAverageVelocity;
//		frameRate = .10; // this may not be the real frameRate
//		videoLength = averageVelocity.length * frameRate;
//		xLabel = "Time in frames";
//		yLabel = "Average Velocity (in pixels per frame)";
//		title = "Average Velocity vs Time";
//		color = Color.BLACK;
//	}

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
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		return new Dimension((int)(analyzerPanel.getWidth() * (3.0 / 8.0)), (int)(analyzerPanel.getHeight() * 2.0 / 3.0));
	}
	
	public void setDrawPoints(boolean drawPoints) {
		this.drawPoints = drawPoints;
	}
	
	/** Returns the minimum size of this panel as a Dimension object */
	public Dimension getMinimumSize() {
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		return new Dimension((int)(analyzerPanel.getWidth() * (3.0 / 8.0)), (int)(analyzerPanel.getHeight() * 2.0 / 3.0));
	}
	
	public Dimension getMaximumSize() {
		return new Dimension(1000, 1000);
	}
	
	public void paintComponent(Graphics g) {	
		if(analyzerPanel.getFlyList().size() <= 0){
			return;
		}
		g.setColor(Color.WHITE);
		int GPanelWidth= this.getWidth();
		int GPanelHeight=this.getHeight();
		g.fillRect(0, 0, GPanelWidth, GPanelHeight);
		if(startFrame == 0 && endFrame == 0)
		{
			averageVelocity = analyzerPanel.getAverageVelocity(analyzerPanel.getRegionsOfInterest());
		}
		else
		{
			averageVelocity = analyzerPanel.getAverageVelocity(analyzerPanel.getRegionsOfInterest(), startFrame, endFrame);
		}
		videoLength = averageVelocity[0].length * analyzerPanel.getFrameRate();
		g.setColor(Color.BLACK);

		double maxVelocity = 0;

		for (int i = 0; i < averageVelocity.length; i++) {
			for(int j = 0; j<averageVelocity[i].length; j++){
				
				if (averageVelocity[i][j] > maxVelocity) {
				maxVelocity = averageVelocity[i][j];
				}
			}
		}
		
		int leftMarg=100;
		int bottomMarg=100;
		int rightMarg=35;
		int topMarg=50; 
		
		
		int xOffSet = leftMarg-3;
		int yOffSet = GPanelHeight - bottomMarg-3;
		
		double widthOffset = (GPanelWidth - (leftMarg+rightMarg))
				/ (double) (averageVelocity[0].length - 1);
		
		double gridxOffset = 40.0;//(GPanelWidth - (leftMarg+rightMarg)) / 10.0;
		double gridyOffset = 40.0;//(GPanelHeight -(topMarg+bottomMarg))/ 10.0; 
		double heightOffset = (double)(GPanelHeight - (topMarg+bottomMarg)) / maxVelocity;
		Graphics2D g2d = (Graphics2D) g;
		Font f =new Font("SansSarif", Font.PLAIN, 12);
		Rectangle2D bounds; 
		g.setFont(f);
		int stringWidth=0;
		
		DecimalFormat df= new DecimalFormat ("##.##");
//		String.forma
		g.setColor(Color.LIGHT_GRAY);
		
		stringWidth=(int) f.getStringBounds(xLabel, g2d.getFontRenderContext()).getWidth();
		g.drawString(xLabel, (GPanelWidth-stringWidth+leftMarg-rightMarg)/2 , GPanelHeight-(bottomMarg/3));
		
		stringWidth=(int) f.getStringBounds(title, g2d.getFontRenderContext()).getWidth();
		g.drawString(title,(GPanelWidth-stringWidth)/2 , topMarg/3);
		
		// drawing the grid lines and the data for the grid lines
		// Y - AXIS
		double d = videoLength/((GPanelWidth-(leftMarg+rightMarg))/gridxOffset);
		for (int i = 0; i*gridxOffset <= GPanelWidth - (leftMarg + rightMarg); i++) {
			g.drawLine((int)(gridxOffset * i) + leftMarg, GPanelHeight-bottomMarg,
					(int)(gridxOffset * i) + leftMarg, topMarg);
			
			String tempXValue = String.format("%.2f", d*(i + startFrame) / 1000);		
			stringWidth=(int) f.getStringBounds(tempXValue, g2d.getFontRenderContext()).getWidth();		
			g.drawString(tempXValue, (int)(gridxOffset * i) + leftMarg - stringWidth / 2, GPanelHeight-(bottomMarg/2));
		}
		// Y - AXIS
		d = maxVelocity/((GPanelHeight-(topMarg+bottomMarg))/gridyOffset);
		for( int i = 0; i*gridyOffset <= GPanelHeight - (topMarg + bottomMarg); i++) {
			g.drawLine(leftMarg,(int) -(gridyOffset * i) + GPanelHeight - bottomMarg, GPanelWidth-rightMarg,
					(int)	-(gridyOffset * i) + GPanelHeight - bottomMarg);
			
			String tempYValue =String.format("%.2f", d*i);
			int stringHeight=(int) f.getStringBounds(tempYValue, g2d.getFontRenderContext()).getHeight();	
			g.drawString(String.format("%.2f", i * d), leftMarg/2, (int)(-gridyOffset * i) + GPanelHeight - bottomMarg + stringHeight/2);
		}

		g.setColor(Color.BLACK);
		g.drawRect(leftMarg, topMarg, GPanelWidth - (leftMarg+rightMarg), GPanelHeight - (topMarg+bottomMarg));
		g.setColor(color);
		for (int i = 0; i < averageVelocity.length; i++) {
			for(int j = 0; j< averageVelocity[i].length; j++){
			
			// draw the points
			if(drawPoints) {
				g.fillOval((int) (xOffSet + j * widthOffset),(int) (yOffSet - averageVelocity[i][j] * heightOffset), 6, 6);
			}
			if (j != 0) {
				g.drawLine(
						(int) (xOffSet + j * widthOffset + 3),
						(int) (yOffSet - averageVelocity[i][j] * heightOffset) + 3,
						(int) (xOffSet + (j - 1) * widthOffset + 3),
						(int) (yOffSet - averageVelocity[i][j-1] * heightOffset + 3));
			}
			}
			g.setColor(new Color((int) ((double)(i * 255)/ averageVelocity.length), (int) (Math.random() * 255), 50));
		}
		g.setColor(Color.LIGHT_GRAY);
		g2d.rotate(-Math.PI/2.0);
		g2d.translate(-GPanelHeight,0);
		stringWidth=(int) f.getStringBounds(yLabel, g2d.getFontRenderContext()).getWidth();
		VertDrawString(yLabel,(GPanelHeight-topMarg+bottomMarg-stringWidth)/2 ,leftMarg/3, g2d);

	}

	public void VertDrawString(String string, int x, int y, Graphics2D g2d){		
		Font f =new Font("SansSarif", Font.PLAIN, 12);
		Rectangle2D bounds; 
		g2d.setFont(f);
		String temp;
		for(int i= 0; i<=string.length()-1; i++){
			temp= string.substring(i,i+1);
			bounds=f.getStringBounds(temp, g2d.getFontRenderContext());
			g2d.drawString(temp, x, y);
			x+=bounds.getWidth();
		}

	}

	public void setLabels(String titleText, String xAxisText, String yAxisText) {
		title=titleText;
		xLabel=xAxisText;
		yLabel=yAxisText;
	}

	public void saveGraph(File file) {
		Container c = this;
		BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		c.paint(im.getGraphics());
		try {
			ImageIO.write(im, "PNG", file);
		} catch (IOException e) {
			System.err.println("Invalid IO Exception");
			e.printStackTrace();
		}
	}

	public void setDataRange(int start, int end) {
		startFrame = start;
		endFrame = end;
	}
}
