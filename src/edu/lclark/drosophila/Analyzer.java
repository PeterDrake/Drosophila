package edu.lclark.drosophila;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Analyzer {
	private int sizeThreshold;
	private static AnalyzerGui gui;
	
	private File currentImage;
	
	public void sizeThresholdUpdate(int input){
		sizeThreshold= input;
		System.out.println("working");
	}
	
	public static void main(String[] args) {
		gui = new AnalyzerGui(new Analyzer());
		gui.run();
	}
	public File getImage(){
		return currentImage;
	}
	

	public void passImage(File file) {
		currentImage = file;
		try {
			BufferedImage image = ImageIO.read(file);
			//System.out.println(image.toString());
		} catch (IOException e) {
			System.err.println("EVERYTHING IS HORRIBLE");
			e.printStackTrace();
		}
	}
	
}
