package edu.lclark.drosophila;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
	public double averageVelFly(Fly fly, int start, int end){ 
		double avgVel=0;
		double [] vx= fly.getVx(); 
		double [] vy= fly.getVy();
		for (int i = start; i <= end; i++) {
			avgVel+= java.lang.Math.pow(java.lang.Math.pow(vx[i], 2)+ java.lang.Math.pow(vy[i], 2), .5);
		}
		avgVel = avgVel/ (end-(start-1)); 
		return avgVel;
	}
	
	public double totalDistance(Fly fly, int start, int end){
		double dist=0;
		double [] x= fly.getX(); 
		double [] y= fly.getY();
		for (int i = start; i < end; i++) {
			dist+= java.lang.Math.pow((java.lang.Math.pow((x[i]-x[i+1]),2) + (java.lang.Math.pow((y[i]-y[i+1]),2))),.5);
		}
		return dist;
	}
	
	
	public double[] averageVelMultFlies(Fly [] flies, int start, int end){
		double [] avgVel= new double [end-start];
		double tempAvg;
		for (int i = start; i < end; i++) {
			tempAvg =0;
			for (int j = 0; j < flies.length; j++) {
				tempAvg += averageVelFly(flies[j], i, i);
			}
			avgVel[i-start] = tempAvg/flies.length;
		}
		
		return avgVel;
	}
	
//	public static void plotVel(Fly [] flies, int start , int end){
//	XYSeries series = new XYSeries("XYGraph");
//
//	 series.add(1, 1);
//	 series.add(1, 2);
//	 series.add(2, 1);
//	 series.add(3, 9);
//	 series.add(4, 10);
//	 // Add the series to your data set
//	 XYSeriesCollection dataset = new XYSeriesCollection();
//	 dataset.addSeries(series);
//	 // Generate the graph
//	 JFreeChart chart = ChartFactory.createXYLineChart(
//	 "XY Chart", // Title
//	 "x-axis", // x-axis Label
//	 "y-axis", // y-axis Label
//	 dataset, // Dataset
//	 PlotOrientation.VERTICAL, // Plot Orientation
//	 true, // Show Legend
//	 true, // Use tooltips
//	 false // Configure chart to generate URLs?
//	 );
//	 try {
//	 ChartUtilities.saveChartAsJPEG(new File("C:\\chart.jpg"), chart, 500, 300);
//	 } catch (IOException e) {
//	 System.err.println("Problem occurred creating chart.");
//	 }
//	 }


	
}
