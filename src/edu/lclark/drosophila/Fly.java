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

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public double[] getY() {
		return y;
	}

	public void setY(double[] y) {
		this.y = y;
	}

	public double[] getVx() {
		return vx;
	}

	public void setVx(double[] vx) {
		this.vx = vx;
	}

	public double[] getVy() {
		return vy;
	}

	public void setVy(double[] vy) {
		this.vy = vy;
	}

}
