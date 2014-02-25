package edu.lclark.drosophila;

import java.awt.*;
import java.util.EventListener;

import javax.swing.*;

public class ImagePanel extends JPanel{
	
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 600;
	
	private AnalyzerPanel a;
	public ImagePanel(AnalyzerPanel a){
		this.a = a;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public void paintComponent(Graphics g){
		if(a.passdownImage()!=null){
		Image image = new ImageIcon(a.passdownImage()).getImage();
		g.drawImage(image, 0, 0, null);
		}
	}	
}
