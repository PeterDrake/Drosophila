package edu.lclark.drosophila;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Analyzer {
	private int sizeThreshold;
	private static AnalyzerGui gui;
	
	private File currentImage;
	
	private static final int CONTRAST_THRESHOLD = 250;
	
	private int totalX;
	private int totalY;
	private int numPixels;
	
	private LinkedList<Fly> flies;

	private int totalFrames;

	public void sizeThresholdUpdate(int input){
		sizeThreshold= input;
		if(currentImage != null)
		{
			passImage(currentImage);
		}
		System.out.println("working");
	}
	
	public static void main(String[] args) {
		gui = new AnalyzerGui(new Analyzer());
		gui.run();
		
	}
	
	public Analyzer() {
		flies = new LinkedList<Fly>();
	}
	public File getImage(){
		return currentImage;
	}
	

	public void passImage(File file) {
		currentImage = file;
		try {
			BufferedImage image = ImageIO.read(file);
			//System.out.println(image.toString());
			flydentify(image);
		} catch (IOException e) {
			System.err.println("EVERYTHING IS HORRIBLE");
			e.printStackTrace();
		}
	}
	
	protected void searchPixel(int x, int y, boolean[][] searchArray, BufferedImage image) {
		// adding to totalX and totalY so we can find the center of mass later
		// numPixels because we need to check it to see whether the blob is big enough to be a fly
		totalX += x; totalY += y; numPixels += 1;
		//System.out.println(numPixels);
		
		// we're searching here now
		searchArray[x][y] = true;
		
		// for each adjacent pixel that hasn't been searched find the gray scale value,
		// and if it's dark enough, search it
		if((x > 0) && !searchArray[x - 1][y]) {
			int rgb = image.getRGB(x - 1, y);
			int red = (rgb >> 16) & 0xFF;
			int green = (rgb >> 8) & 0xFF;
			int blue = rgb & 0xFF;
			double avg = red * 0.2989 + green * .587 + blue * .114;
			if((int)(Math.round(avg)) <= CONTRAST_THRESHOLD) {
				searchPixel(x - 1, y, searchArray, image);
			}
		}
		if((y > 0) && !searchArray[x][y - 1]) {
			int rgb = image.getRGB(x, y - 1);
			int red = (rgb >> 16) & 0xFF;
			int green = (rgb >> 8) & 0xFF;
			int blue = rgb & 0xFF;
			double avg = red * 0.2989 + green * .587 + blue * .114;
			if((int)(Math.round(avg)) <= CONTRAST_THRESHOLD) {
				searchPixel(x, y - 1, searchArray, image);
			}
		}
		if((x < searchArray.length - 1) && !searchArray[x + 1][y]) {
			int rgb = image.getRGB(x + 1, y);
			int red = (rgb >> 16) & 0xFF;
			int green = (rgb >> 8) & 0xFF;
			int blue = rgb & 0xFF;
			double avg = red * 0.2989 + green * .587 + blue * .114;
			if((int)(Math.round(avg)) <= CONTRAST_THRESHOLD) {
				searchPixel(x + 1, y, searchArray, image);
			}
		}
		if((y < searchArray[0].length - 1) && !searchArray[x][y + 1]) {
			int rgb = image.getRGB(x, y + 1);
			int red = (rgb >> 16) & 0xFF;
			int green = (rgb >> 8) & 0xFF;
			int blue = rgb & 0xFF;
			double avg = red * 0.2989 + green * .587 + blue * .114;
			if((int)(Math.round(avg)) <= CONTRAST_THRESHOLD) {
				searchPixel(x, y + 1, searchArray, image);
			}
		}
	}
	
	/**
	 * TODO this is not adjusted for tracking flies between multiple frames.
	 * People who have worked on this and therefore you can ask questions about:
	 * Brandon
	 * Christian
	 */
	public void flydentify(BufferedImage image, int frameNumber) {
		int imgHeight = image.getHeight();
		int imgWidth = image.getWidth();
		// a temporary array of the flies found in this image. It just stores their x and y
		LinkedList<double[]> tempFlies = new LinkedList<double[]>();
		// an array of which pixels have been searched
		boolean searchArray[][] = new boolean[imgWidth][imgHeight];
		for(int i = 0; i < imgWidth; i++) {
			for(int j = 0; j < imgHeight; j++) {
				if(!searchArray[i][j]) { // if pixel hasn't been searched
					// find gray scale value of the pixel. TODO we aren't completely sure if this works
					int rgb = image.getRGB(i, j);
					int red = (rgb >> 16) & 0xFF;
					int green = (rgb >> 8) & 0xFF;
					int blue = rgb & 0xFF;
					double avg = red * 0.2989 + green * .587 + blue * .114;
					if((int)(Math.round(avg)) <= CONTRAST_THRESHOLD) { // if the color is dark enough
						totalX = 0; totalY = 0; numPixels = 0; // initialize values to find center of mass of fly
						searchPixel(i, j, searchArray, image); // depth first search on surrounding pixels to find area of fly
						if(numPixels >= sizeThreshold) // if the blob is large enough to be a fly
						{
							// create a new temporary fly object
							double tempLocation[] = new double[2];
							tempLocation[0] = (double) totalX / numPixels;
							tempLocation[1] = (double) totalY / numPixels;
							tempFlies.add(tempLocation);
							System.out.println("size: " + numPixels);
						}
					} else {
						// we searched this already!
						searchArray[i][j] = true;
					}
				}
			}
		}
		// create this variable because flies.size() is linear
		int sizeFlies = flies.size();
		for(int i = 0; i < sizeFlies && !tempFlies.isEmpty(); i++)
		{
			// going through each existing fly and matching it to the closest temporary counterpart
			Fly pastFly = flies.get(i);
			double pastX = pastFly.getX(0);
			double pastY = pastFly.getY(0);
			double dist = Math.sqrt(Math.pow(pastX - tempFlies.get(0)[0], 2) + Math.pow(pastY - tempFlies.get(0)[1], 2));
			int closestFlyIndex = 0;
			int sizeTempFlies = tempFlies.size();
			for(int j = 1; j < sizeTempFlies; j++)
			{
				double thisDist = Math.sqrt(Math.pow(pastX - tempFlies.get(j)[0], 2) + Math.pow(pastY - tempFlies.get(j)[1], 2));
				if(thisDist < dist)
				{
					dist = thisDist;
					closestFlyIndex = j;
				}
			}
			// augmenting the existing fly to contain information about location from the image
			pastFly.addFrameInfo(frameNumber, tempFlies.get(closestFlyIndex)[0], tempFlies.get(closestFlyIndex)[1]);
			// remove the temporary fly so two existing flies can't map to the same temporary one
			tempFlies.remove(closestFlyIndex);
		}
		// in case there are more temporary flies than existing flies
		sizeFlies = tempFlies.size();
		while(!tempFlies.isEmpty())
		{
			// create a new fly for each fly left
			Fly aNewFly = new Fly(totalFrames);
			aNewFly.addFrameInfo(frameNumber, tempFlies.get(0)[0], tempFlies.get(0)[1]);
			flies.add(aNewFly);
			tempFlies.remove(0);
		}
	}
	public void flydentify(BufferedImage image)
	{
		// just for a single image
		flies = new LinkedList<Fly>();
		totalFrames = 1;
		flydentify(image, 0);
		
		System.out.println("number of flies: " + flies.size());
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

	public LinkedList<Fly> getFlies() {
		return flies;
	}
}
