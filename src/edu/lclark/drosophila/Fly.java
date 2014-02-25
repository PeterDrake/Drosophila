package edu.lclark.drosophila;

public class Fly {
	
	private double[] x, y, vx, vy;
	private int arenaIdx;
	
	public Fly(int numFrames)
	{
		x = new double[numFrames];
		y = new double[numFrames];
		vx = new double[numFrames];
		vy = new double[numFrames];
	}
	
	public double getX(int frameNumber)
	{
		return x[frameNumber];
	}
	public double getY(int frameNumber)
	{
		return y[frameNumber];
	}

	public void addFrameInfo(int frameNumber, double x, double y) {
		this.x[frameNumber] = x;
		this.y[frameNumber] = y;
	}

}
