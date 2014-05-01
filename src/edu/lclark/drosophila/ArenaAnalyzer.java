package edu.lclark.drosophila;

import java.util.LinkedList;
import java.util.List;

/**
 * Does math that involves the arenas.
 */

public class ArenaAnalyzer {
	
	/**
	 * Reference to the Analyzer class
	 */
	private Analyzer analyzer;
	
	/**
	 * Full list of flies
	 */
	private List<Fly> fullflylist;

	/**
	 * Constructor with reference to the analyzer
	 * @param a
	 */
	public ArenaAnalyzer(Analyzer a) {
		this.analyzer = a;
	}

	/**
	 * Calculates the Velocity of an Arena between frames start and end.
	 * 
	 * @param Arena
	 * @param start
	 * @param end
	 * @return
	 */
	public double AvgVelocityofArena(int Arena, int start, int end) {
		fullflylist = analyzer.getFlies();
		List<Fly> arenaflies = new LinkedList<Fly>();
		for (Fly f : fullflylist) {
			if (f.getArena() == Arena) {
				arenaflies.add(f);
			}
		}
		double avg = 0;
		for (Fly f : arenaflies) {
			avg += f.averageVelFly(start, end);
		}
		avg = avg / arenaflies.size();
		return avg;
	}
}
