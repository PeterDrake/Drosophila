package edu.lclark.drosophila;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {

	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 300;
	
	private AnalyzerPanel analyzerPanel;
	
	public GraphPanel()
	{
		
	}
	
	public GraphPanel(AnalyzerPanel analyzerPanel)
	{
		this.analyzerPanel = analyzerPanel;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public void paintComponent(Graphics g)
	{
//		double averageVelocity[] = analyzerPanel.getAverageVelocity();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		g.setColor(Color.BLACK);
		double maxVelocity = 0;
		
//		for(int i = 0; i < averageVelocity.length; i++)
//		{
//			if(averageVelocity[i] > maxVelocity)
//			{
//				maxVelocity = averageVelocity[i];
//			}
//		}
		
		g.drawRect(25, 25, DEFAULT_WIDTH - 50, DEFAULT_HEIGHT - 50);
	}
}
