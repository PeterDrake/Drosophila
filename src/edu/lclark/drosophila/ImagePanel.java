package edu.lclark.drosophila;

import java.awt.*;
import java.util.EventListener;
import java.util.LinkedList;

import javax.swing.*;

public class ImagePanel extends JPanel{
	
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 600;
	
	private boolean flydentifiers = false;
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
			if(flydentifiers)
			{
				LinkedList<Fly> flies = a.getFlyList();
				int sizeFlies = flies.size();
				for(int i = 0; i < sizeFlies; i++)
				{
					g.setColor(Color.RED);
					g.drawOval((int)flies.get(i).getX()[0]-3, (int)flies.get(i).getY()[0]-3, 6, 6);
				}
			}
		}
	}

	public void setFlydentifiers() {
		flydentifiers = !flydentifiers;
		a.repaint();
	}	
}
