package edu.lclark.drosophila;

import java.awt.EventQueue;

import javax.swing.*;

public class GraphPanelTest extends JFrame {

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
              JFrame frame = new GraphPanelTest();
              frame.setTitle("Graph Panel Test");
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
              frame.pack();
           }
        });
	}
	
	private GraphPanel graphPanel;
	
	public GraphPanelTest()
	{
		graphPanel = new GraphPanel();
		add(graphPanel);
		graphPanel.setDrawPoints(true);
	}
	
}
