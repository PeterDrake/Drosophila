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
	
	private File[] images;
	private int numImages;
	
	public Analyzer(){
		images= new File[5];
	}
	public void sizeThresholdUpdate(int input){
		sizeThreshold= input;
		System.out.println("working");
	}
	
	public static void main(String[] args) {
		gui = new AnalyzerGui(new Analyzer());
		gui.run();
	}
	public File getImage(int index){
		if(index>=0&&index<images.length)return images[index];
		throw new ArrayIndexOutOfBoundsException();
	}
	

	public void passImage(File file) {
		images[numImages] = file;
		System.out.println("added a file to index"+numImages);
		numImages++;
		try {
			BufferedImage image = ImageIO.read(file);
			//System.out.println(image.toString());
		} catch (IOException e) {
			System.err.println("EVERYTHING IS HORRIBLE");
			e.printStackTrace();
		}
	}
	public double averageVelFly(Fly fly, int start, int end){ 
		double avgVel=0;
		double [] vx= fly.getVx(); 
		double [] vy= fly.getVy();
		for (int i = start; i <= end; i++) {
			avgVel+= vx[i]+vy[i];
		}
		avgVel = avgVel/ (end-(start-1)); 
		return avgVel;
	}
}
