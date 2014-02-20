package edu.lclark.drosophila;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFrame;

public class AnalyzerGui extends JFrame{
	
	private Analyzer a;
	private AnalyzerPanel analyzerpanel;
	
	public AnalyzerGui(Analyzer a) {
		this.a = a;
		this.analyzerpanel=new AnalyzerPanel(this);
	}
	
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("Flydentifier");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);				
				frame.add(analyzerpanel);
				frame.pack();
			}
		});
		analyzerpanel.repaint();
	}
	public File passDownImage(){
		return a.getImage();
	}
	
	public void passImage(File file) {
		a.passImage(file);
		
	}
	
}
