package edu.lclark.drosophila;

import java.awt.Point;
import java.util.Hashtable;
import java.util.List;
public class Fly {

	/** Id number to be able to tell flies apart */
	private int id;
	
	/** The number of frames this fly stores info for */
	private int numFrames;
	/**
	 * Stores all x positions of the fly for all frames. For example, x[4]
	 * stores the fly's x position in frame 4.
	 */
	private double[] x;

	/**
	 * Stores all y positions of the fly for all frames. For example, y[4]
	 * stores the fly's y position in frame 4.
	 */
	private double[] y;

	/**
	 * Stores the instantaneous x velocity of the fly for all frames. For
	 * example, vx[3] stores the velocity of this fly in frame 3.
	 */
	private double[] vx;

	/**
	 * Stores the instantaneous y velocity of the fly for all frames. For
	 * example, vy[3] stores the velocity of this fly in frame 3.
	 */
	private double[] vy;
	/**
	 * A integer to check which region of interest this fly exists in.
	 */
	private int Arena;
	public int getArena() {
		return Arena;
	}

	/**
	 * A constructor for the Fly class, if an image or images are loaded. All
	 * arrays are initialized to size 20
	 */
	
	
	public Fly() {
		id = -1;
		numFrames = 20;
		x = new double[numFrames];
		y = new double[numFrames];
		vx = new double[numFrames];
		vy = new double[numFrames];
		for (int i = 0; i < numFrames; i++) {
			x[i] = -1;
			y[i] = -1;
		}
	}

	/**
	 * A constructor for the Fly class, if a movie is loaded.
	 * <p>
	 * Field arrays are as large as there are number of frames specified.
	 * 
	 * @param numFrames
	 *            the number of frames the fly must store data for.
	 */
	public Fly(int numFrames) {
		id = -1;
		this.numFrames = numFrames;
		x = new double[numFrames];
		y = new double[numFrames];
		vx = new double[numFrames];
		vy = new double[numFrames];
		for (int i = 0; i < numFrames; i++) {
			x[i] = -1;
			y[i] = -1;
		}
	}
	/**
	 * A constructor only for testing purposes.
	 */
	public Fly(int x0,int y0,int numFrames) {
		id=-1;
		x = new double[numFrames];
		y = new double[numFrames];
		vx = new double[numFrames];
		vy = new double[numFrames];
			x[0] = x0;
			y[0] = y0;
		}

	/**
	 *  this calculates total distance of one fly over multiple frames
	 * @param fly
	 * @param start
	 * @param end
	 * @return total distance traveled from start frame to end frame
	 */
	public double totalDistance(int start, int end){
		double dist=0;
		for (int i = start; i < end; i++) {
			dist+= java.lang.Math.pow((java.lang.Math.pow((getX(i)-getX(i+1)),2) + (java.lang.Math.pow((getY(i)-getY(i+1)),2))),.5);
		}
		return dist;
	}
	
	/**
	 * Calculates the mean velocity of the given fly within the time specified
	 * by the starting and ending frames.
	 * 
	 * @param fly
	 *            the fly whose average velocity is desired.
	 * @param start
	 *            the first frame you want the average velocity calculated from.
	 * @param end
	 *            the last frame you want the average velocity calculated from.
	 * @return the mean velocity of the given fly within the given time frame.
	 */
	public double averageVelFly(int start, int end) {
		double avgVel=0;
		for (int i = start; i <= end; i++) {
			avgVel+= getVelocityatFrame(i);
		}
		avgVel = avgVel/ (end-(start-1)); 
		return avgVel;
	}
	
	/**
	 * Stores the x and y position for this fly in the given frame.
	 * 
	 * @param frameNumber
	 *            the frame the given data corresponds to. 0 corresponds to
	 *            position 0 in the field's array.
	 * @param x
	 *            the x position of this fly in this frame.
	 * @param y
	 *            the y position of this fly in this frame.
	 */
	public void addFrameInfo(int frameNumber, double x, double y) {
		this.x[frameNumber] = x;
		this.y[frameNumber] = y;
		if(frameNumber==0){
			vx[frameNumber] = 0;
			vy[frameNumber] = 0;
		}
		else{
			vx[frameNumber] = Math.abs(this.x[frameNumber]-this.x[frameNumber-1]);
			vy[frameNumber] = Math.abs(this.y[frameNumber]-this.y[frameNumber-1]);
		}
	}
	
	/** Copies the data from this fly to a new Fly */
	public Fly copyThisFly(){
		Fly newFly = new Fly(numFrames);
		for(int i = 0; i < numFrames; i++){
			if(x[i] == -1){
				break;
			}
			newFly.addFrameInfo(i,  x[i], y[i]);
		}
		return newFly;
	}

	public double[] getVx() {
		return vx;
	}

	public double[] getVy() {
		return vy;
	}
	/**
	 * returns the 2-dimensional velocity at a particular frame;
	 * @param frame
	 * @return double velocity
	 */
	public double getVelocityatFrame(int frame){
		return (Math.sqrt((vx[frame]*vx[frame]+vy[frame]*vy[frame])));
	}
	/**
	 * Getter for this fly's x position.
	 * 
	 * @param frameNumber
	 *            which frame you want the fly's x position in.
	 * @return the x coordinate of this fly in the given frame.
	 */
	public double getX(int frameNumber) {
		return x[frameNumber];
	}

	/**
	 * Getter for this fly's y position.
	 * 
	 * @param frameNumber
	 *            which frame you want the fly's y position in.
	 * @return the y coordinate of this fly in the given frame.
	 */
	public double getY(int frameNumber) {
		return y[frameNumber];
	}

	/**
	 * Returns the identification number of this fly.
	 * @return the identification number of this fly.
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Sets the id of this fly to the given integer.
	 * @param id the integer which this fly's id number will be set to.
	 */
	public void setId(int id){
		this.id = id;
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
	protected void setArena(int Arena){
		this.Arena=Arena;
	}
	protected void setArena(List<RegionMaker.PointArena> arenamap,int frame){
		Fly f = this;
		for(RegionMaker.PointArena  i : arenamap){
			if (f.getX(frame) >= i.getTopleft().x && f.getX(frame) <= i.getBottomright().x
					&& f.getY(frame) >= i.getTopleft().y
					&& f.getY(frame) <= i.getBottomright().y) {
				f.setArena(i.getArena());
				System.out.println("set fly " + f + " at " + f.getX(frame)
						+ " and " + f.getY(frame) + " to Arena " + i.getArena());
			}
			
		}
	}
	public String toString(){
		String str= ""+getId();
		return str;
	}

}
