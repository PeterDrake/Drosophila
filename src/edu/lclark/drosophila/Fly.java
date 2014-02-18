package edu.lclark.drosophila;

public class Fly {
	
	private double[] x, y, vx, vy;
	private int arenaIdx;
	
	public Fly(double initialX, double initialY, int numFrames)
	{
		x = new double[numFrames];
		y = new double[numFrames];
		vx = new double[numFrames];
		vy = new double[numFrames];
		
		x[0] = initialX;
		y[0] = initialY;
	}

}
