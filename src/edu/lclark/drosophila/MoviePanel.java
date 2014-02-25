package edu.lclark.drosophila;

import java.io.File;
import java.awt.*;
import javax.swing.*;

public class MoviePanel extends JPanel{
	
	private File vlcInstallPath = new File("users/isabel/Applications/vlc");
	
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 600;
	
	private AnalyzerPanel a;
	public MoviePanel(AnalyzerPanel a){
		this.a = a;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public void paintComponent(Graphics g){
		if(a.passdownImage()!=null){
			
		}
	}	
}
