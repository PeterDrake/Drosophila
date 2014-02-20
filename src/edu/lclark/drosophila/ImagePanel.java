package edu.lclark.drosophila;

import java.awt.*;
import java.util.EventListener;

import javax.swing.*;

public class ImagePanel extends JPanel{
	
	private AnalyzerPanel a;
	public ImagePanel(AnalyzerPanel a){
		this.a = a;
	}
	public void paintComponent(Graphics g){
		System.out.println(a.passdownImage());
		if(a.passdownImage()!=null){
		Image image = new ImageIcon(a.passdownImage()).getImage();
		g.drawImage(image, 0, 0, null);
		}
	}	
}
