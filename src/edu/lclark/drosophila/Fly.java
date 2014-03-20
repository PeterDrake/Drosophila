package edu.lclark.drosophila;

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
	
	public int getId(){
		return id;
	}

	public double[] getVx() {
		return vx;
	}

	public double[] getVy() {
		return vy;
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
	
	/**Sets fly ID to given int */
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

}
