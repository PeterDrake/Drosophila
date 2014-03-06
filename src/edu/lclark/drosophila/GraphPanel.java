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
		double averageVelocity[] = {85,56,42,3,7,11,46,36}; //this is fake data; analyzerPanel.getAverageVelocity is the correct call to put here
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		g.setColor(Color.BLACK);
		
		
		double maxVelocity = 0;
		
		for(int i = 0; i < averageVelocity.length; i++)
		{
			if(averageVelocity[i] > maxVelocity)
			{
				maxVelocity = averageVelocity[i];
			}
		}
		
		g.drawRect(25, 25, DEFAULT_WIDTH - 50, DEFAULT_HEIGHT - 50);
		int xOffSet=22;
		int yOffSet=22+DEFAULT_HEIGHT-50;
		int widthOffset=(DEFAULT_WIDTH - 50)/(averageVelocity.length-1);
		int gridxOffset= (DEFAULT_WIDTH - 50)/10;
		int gridyOffset=0; 
		double heightOffset=(DEFAULT_HEIGHT - 50)/ maxVelocity;
		
		for(int i=0; i< (((double)averageVelocity.length/(DEFAULT_WIDTH - 50))%10)*(DEFAULT_WIDTH - 50); i++){
			g.drawLine((gridxOffset*i)+25,yOffSet+3,(gridxOffset*i)+25,25);
			
		}
		
		for(int i=0; i < averageVelocity.length; i++){
			g.fillOval(xOffSet+i*widthOffset,(int)(yOffSet-averageVelocity[i]*heightOffset),6,6);
			if(i!=0){
				g.drawLine(xOffSet+i*widthOffset+3,(int)(yOffSet-averageVelocity[i]*heightOffset)+3,xOffSet+(i-1)*widthOffset+3,(int)(yOffSet-averageVelocity[i-1]*heightOffset+3))
;			}
			
			
			
			
		}

	
	
	}
}
