package edu.lclark.drosophila;

public class Fly {

	private double[] x, y, vx, vy;

	public Fly(int numFrames) {
		x = new double[numFrames];
		y = new double[numFrames];
		vx = new double[numFrames];
		vy = new double[numFrames];
	}

	public double getX(int frameNumber) {
		return x[frameNumber];
	}

	public double getY(int frameNumber) {
		return y[frameNumber];
	}

	public void addFrameInfo(int frameNumber, double x, double y) {
		this.x[frameNumber] = x;
		this.y[frameNumber] = y;
	}

	protected void setX(double[] x) {
		this.x = x;
	}

	protected void setY(double[] y) {
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
