package edu.lclark.drosophila;

public class Fly {

	private double[] x, y, vx, vy;

	public Fly(double initialX, double initialY, int numFrames)
	{
		x = new double[numFrames];
		y = new double[numFrames];
		vx = new double[numFrames];
		vy = new double[numFrames];
		
		x[0] = initialX;
		y[0] = initialY;
	}
	
	public Fly(int numFrames) {
		x = new double[numFrames];
		y = new double[numFrames];
		vx = new double[numFrames];
		vy = new double[numFrames];
	}

	public void addFrameInfo(int frameNumber, double x, double y) {
		this.x[frameNumber] = x;
		this.y[frameNumber] = y;
	}

	public double[] getVx() {
		return vx;
	}

	public double[] getVy() {
		return vy;
	}

	public double getX(int frameNumber) {
		return x[frameNumber];
	}

	public double getY(int frameNumber) {
		return y[frameNumber];
	}

	public void setVx(double[] vx) {
		this.vx = vx;
	}

	public void setVy(double[] vy) {
		this.vy = vy;
	}

	protected void setX(double[] x) {
		this.x = x;
	}

	protected void setY(double[] y) {
		this.y = y;
	}

}
