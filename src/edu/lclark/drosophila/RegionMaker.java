package edu.lclark.drosophila;

import java.awt.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that takes care of setting flies to Arenas in the model.
 * 
 * @author jpoley
 * 
 */
public class RegionMaker {
	/**
	 * Class to represent a single arena
	 */
	public class PointArena {
		private int Arena;
		private Point bottomright;

		private Point topleft;

		public PointArena(Point tl, Point br, int arena) {
			topleft = tl;
			bottomright = br;
			Arena = arena;
		}

		public int getArena() {
			return Arena;
		}

		public Point getBottomright() {
			return bottomright;
		}

		public Point getTopleft() {
			return topleft;
		}
	}
	public final static int MAXSQUARES = 50;
	Analyzer analyzer;
	List<PointArena> ArenaAssignment;
	List<Fly> flies;
	List<Point> pastfirstpoints;

	List<Point> pastsecondpoints;

	public RegionMaker(Analyzer a) {
		analyzer = a;
		ArenaAssignment = new LinkedList<PointArena>();
		pastfirstpoints = new LinkedList<Point>();
		pastsecondpoints = new LinkedList<Point>();
	}

	/**
	 * Clears data about the regions
	 */
	public void clearData() {
		pastfirstpoints.clear();
		pastsecondpoints.clear();
		ArenaAssignment.clear();
	}

	/**
	 * Returns the arena assigment
	 * 
	 * @return
	 */
	public List<PointArena> getArenaAssignment() {
		return ArenaAssignment;
	}

	/**
	 * Gets bottom right of image panel
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	private Point getbottomright(Point point1, Point point2) {
		int x1 = point1.x;
		int y1 = point1.y;
		int x2 = point2.x;
		int y2 = point2.y;
		int fx;
		int fy;
		if (x1 > x2) {
			fx = x1;
		} else {
			fx = x2;
		}
		if (y1 > y2) {
			fy = y1;
		} else {
			fy = y2;
		}
		return new Point(fx, fy);
	}

	/**
	 * Returns past first points
	 * 
	 * @return
	 */
	public List<Point> getPastfirstpoints() {
		return pastfirstpoints;
	}

	/**
	 * Returns past second points
	 * 
	 * @return
	 */
	public List<Point> getPastsecondpoints() {
		return pastsecondpoints;
	}

	/**
	 * gets the top left corner of the image panel
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	private Point gettopleft(Point point1, Point point2) {
		int x1 = point1.x;
		int y1 = point1.y;
		int x2 = point2.x;
		int y2 = point2.y;
		int fx;
		int fy;
		if (x1 > x2) {
			fx = x2;
		} else {
			fx = x1;
		}
		if (y1 > y2) {
			fy = y2;
		} else {
			fy = y1;
		}
		return new Point(fx, fy);
	}

	/**
	 * Sets flies to region specified
	 * 
	 * @param point1
	 * @param point2
	 * @param arena
	 * @param frame
	 */
	public void setFliesToRegions(Point point1, Point point2, int arena,
			int frame) {
		flies = analyzer.getFlies();
		Point topleft = gettopleft(point1, point2);
		Point bottomright = getbottomright(point1, point2);
		for (Fly f : flies) {
			if (f.getX(frame) >= topleft.x && f.getX(frame) <= bottomright.x
					&& f.getY(frame) >= topleft.y
					&& f.getY(frame) <= bottomright.y) {
				f.setArena(arena);

			}
		}
		pastfirstpoints.add(topleft);
		pastsecondpoints.add(bottomright);
		PointArena pointarenapair = new PointArena(topleft, bottomright, arena);
		ArenaAssignment.add(pointarenapair);
		analyzer.passDownPoints();
	}

}
