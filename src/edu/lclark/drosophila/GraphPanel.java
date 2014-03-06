package edu.lclark.drosophila;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {

	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	
	private AnalyzerPanel analyzerPanel;
	
	public GraphPanel(AnalyzerPanel analyzerPanel)
	{
		this.analyzerPanel = analyzerPanel;
	}
	
	public void paintComponent(Graphics g)
	{
		double averageVelocity[] = analyzerPanel.getAverageVelocity();
	}
	
	
}
