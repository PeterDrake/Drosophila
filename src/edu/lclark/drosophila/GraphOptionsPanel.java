package edu.lclark.drosophila;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GraphOptionsPanel extends JPanel {

	private static final int DEFAULT_WIDTH = 175;

	private static final int DEFAULT_HEIGHT = 150;

	private AnalyzerPanel analyzerPanel;
	
	private JTextField titleText;
	
	private JLabel titleLabel;
	
	private JTextField xAxisText;
	
	private JLabel xAxisLabel;
	
	private JTextField yAxisText;
	
	private JLabel yAxisLabel;
	
	private JButton applyButton;
	
	private JFrame myFrame;
	
	public GraphOptionsPanel(AnalyzerPanel a, JFrame frame){
		this.analyzerPanel = a;
		myFrame= frame;
		
		GridLayout gl = new GridLayout(4, 2);
		
		this.setLayout(gl);
		
		titleText = new JTextField("Title", 8);
		xAxisText = new JTextField("x-Axis", 8);
		yAxisText = new JTextField("y-Axis", 8);
		titleLabel = new JLabel("Title: ");
		xAxisLabel = new JLabel("x-Axis: ");
		yAxisLabel = new JLabel("y-Axis: ");
		this.add(titleLabel);
		this.add(titleText);
		this.add(xAxisLabel);
		this.add(xAxisText);
		this.add(yAxisLabel);
		this.add(yAxisText);
		applyButton = new JButton("Apply");
		applyButton.addActionListener(new ApplyActionListener());
		this.add(applyButton);
	}
	
	/**
	 * Returns the preferred size of this panel as a Dimension object.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/** Returns the minimum size of this panel as a Dimension object */
	public Dimension getMinimumSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	
	private class ApplyActionListener implements ActionListener {

		
		public ApplyActionListener() { 
			
		}
		
		/**
		 * Opens the file browsing window and passes the selected movie to the
		 * Button Panel when chosen.
		 */
		public void actionPerformed(ActionEvent e) {
			analyzerPanel.setLabels(titleText.getText(), xAxisText.getText(), yAxisText.getText());
			analyzerPanel.repaint();
			myFrame.dispose();
			
		} 
	}
	
	
}
