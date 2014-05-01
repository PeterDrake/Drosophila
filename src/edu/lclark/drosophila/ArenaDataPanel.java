package edu.lclark.drosophila;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * An extension of data panel that displays the average velocity between the
 * start and the end. work could be done to add in smaller frame selection
 * capabilities.
 * 
 * @author jpoley
 * @author iShoshani
 */
public class ArenaDataPanel extends DataPanel {
	/**
	 * a set of all arenas that have been entered
	 */
	SortedSet<Integer> knownArenas;

	/**
	 * constructor for ArenaDataPanel
	 * 
	 * @param a
	 */
	public ArenaDataPanel(AnalyzerPanel a) {
		super(a);
		knownArenas = new TreeSet<Integer>();

	}

	/**
	 * Creates the text block that contains the data for all the flies in the
	 * fly list
	 * 
	 * @param start
	 * @param end
	 * @return String Data
	 */
	public String createData(int start, int end) {
		knownArenas.clear();
		String Data = "";
		Hashtable<Integer, String> lines = new Hashtable<Integer, String>();
		boolean known;
		if (analyzerPanel.getFlyList() != null) {
			for (Fly fly : analyzerPanel.getFlyList()) {
				known = false;
				for (Integer i : knownArenas) {
					if (i.equals(fly.getArena())) {
						known = true;
						break;
					}
				}
				// if(!known){
				knownArenas.add(fly.getArena());
				lines.put(fly.getArena(),
						createGroupLine(fly.getArena(), start, end));
			}
			for (int i : knownArenas) {
				Data += lines.get(i);
				Data += "\n";
			}

		}
		return Data;
	}

	/**
	 * takes a single fly and creates a string to display its avg velocity and
	 * total distance"
	 * 
	 * @param fly
	 * @param start
	 * @param end
	 * @return String FlyLine
	 */
	public String createGroupLine(int Arena, int start, int end) {
		String Data = String.format("%-15d %-25f", Arena,
				analyzerPanel.calcAverageVelocityforArena(Arena, start, end));
		return Data;
	}

	/**
	 * returns labels for the data panel
	 */
	public String createLabels() {
		String Labels = String.format("%-15s %-25s \n", "group",
				"Average Velocity");
		return Labels;
	}
}
