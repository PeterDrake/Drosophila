package edu.lclark.drosophila;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Analyzer {
	
	private static AnalyzerGui gui;
	
	private File currentImage;
	
	private static final int CONTRAST_THRESHOLD = 150;
	
	private int totalX;
	private int totalY;
	private int numPixels;
	
	private LinkedList<Fly> flies;
	
	public static void main(String[] args) {
		gui = new AnalyzerGui(new Analyzer());
		gui.run();
		
	}
	
	public Analyzer() {
		flies = new LinkedList<Fly>();
	}

	public void passImage(File file) {
		currentImage = file;
		try {
			BufferedImage image = ImageIO.read(file);
			System.out.println(image.toString());
			flydentify(image);
		} catch (IOException e) {
			System.err.println("EVERYTHING IS HORRIBLE");
			e.printStackTrace();
		}
	}
	
	protected void searchPixel(int x, int y, boolean[][] searchArray, BufferedImage image) {
		totalX += x; totalY += y; numPixels += 1;
		searchArray[x][y] = true;
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
	public void flydentify(BufferedImage image) {
		int imgHeight = image.getHeight();
		int imgWidth = image.getWidth();
		boolean searchArray[][] = new boolean[imgWidth][imgHeight];
		for(int i = 0; i < imgWidth; i++) {
			for(int j = 0; j < imgHeight; j++) {
				if(!searchArray[i][j]) {
					int rgb = image.getRGB(i, j);
					int red = (rgb >> 16) & 0xFF;
					int green = (rgb >> 8) & 0xFF;
					int blue = rgb & 0xFF;
					double avg = red * 0.2989 + green * .587 + blue * .114;
					if((int)(Math.round(avg)) <= CONTRAST_THRESHOLD) {
						totalX = 0; totalY = 0; numPixels = 0;
						searchPixel(i, j, searchArray, image);
					} else {						
						searchArray[i][j] = true;
					}
				}
			}
		}
	}
	
}
