package edu.lclark.drosophila;

import java.awt.*;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class RegionMaker {
	public final static int MAXSQUARES = 50;
	Analyzer analyzer;
	List<Point> pastfirstpoints;
	List<PointArena> ArenaAssignment;
	
	public List<Point> getPastfirstpoints() {
		return pastfirstpoints;
	}

	
	public List<Point> getPastsecondpoints() {
		return pastsecondpoints;
	}


	List<Point> pastsecondpoints;
	List<Fly> flies;

	public RegionMaker(Analyzer a) {
		analyzer = a;
		ArenaAssignment = new LinkedList<PointArena>();
		pastfirstpoints = new LinkedList<Point>();
		pastsecondpoints = new LinkedList<Point>();
	}

	public void setFliesToRegions(Point point1, Point point2, int arena,
			int frame) {
		System.out.println("this method is being called for arena" + arena);
		flies = analyzer.getFlies();
		Point topleft = gettopleft(point1, point2);
		Point bottomright = getbottomright(point1, point2);
		System.out.println("between "+topleft+" and "+bottomright);
		for (Fly f : flies) {
			System.out.println("checking fly " + f+ " at " + f.getX(frame)
					+ " and " + f.getY(frame));
			if (f.getX(frame) >= topleft.x && f.getX(frame) <= bottomright.x
					&& f.getY(frame) >= topleft.y
					&& f.getY(frame) <= bottomright.y) {
				f.setArena(arena);
				System.out.println("set fly " + f + " at " + f.getX(frame)
						+ " and " + f.getY(frame) + " to Arena " + arena);

			}
		}
		pastfirstpoints.add(topleft);
		pastsecondpoints.add(bottomright);
		PointArena pointarenapair = new PointArena(topleft,bottomright,arena);
		ArenaAssignment.add(pointarenapair);
		analyzer.passDownPoints();
	}

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
	private List<PointArena> getArenaAssignment(){
		return ArenaAssignment;
	}
	public class PointArena{
		private Point topleft;
		private Point bottomright;
		public Point getTopleft() {
			return topleft;
		}
		public Point getBottomright() {
			return bottomright;
		}
		public int getArena() {
			return Arena;
		}
		private  int Arena;
		public PointArena( Point tl,Point br, int arena){
			topleft=tl;
			bottomright=br;
			Arena=arena;
		}
		}
}
