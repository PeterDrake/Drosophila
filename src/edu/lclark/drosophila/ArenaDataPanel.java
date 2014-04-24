package edu.lclark.drosophila;

import java.util.LinkedList;
import java.util.List;

public class ArenaDataPanel extends DataPanel{
	List<Integer> knownArenas;
	public ArenaDataPanel(AnalyzerPanel a){
		super(a);
		knownArenas = new LinkedList<Integer>();
		
	}
	
	public String createLabels(){
		String Labels=String.format("%-15s %-25s \n", "group", "Average Velocity");
		return Labels;
	}
	/**
	 * Creates the text block that contains the data for all the flies in the fly list
	 * @param start
	 * @param end
	 * @return String Data
	 */
	public String createData(int start, int end){
		String Data= "";
		boolean known;
		if(analyzerPanel.getFlyList()!=null){
		for(Fly fly : analyzerPanel.getFlyList()){
			known = false;
			for (Integer i : knownArenas){
				if(i.equals(fly.getArena())){
					known = true;
					break;
				}
			}
				if(!known){
					knownArenas.add(fly.getArena());
					Data+=createGroupLine(fly.getArena(),start,end);
					Data+="\n";
				}
			}
		}
		return Data;
	}
	/**
	 * takes a single fly and creates a string to display its avg velocity and total distance"
	 * @param fly
	 * @param start
	 * @param end
	 * @return String FlyLine
	 */
	public String createGroupLine(int Arena,int start,int end){
		String Data =String.format("%-15d %-25f", Arena, analyzerPanel.calcAverageVelocityforArena(Arena,start,end));
		return Data;
	}
}
