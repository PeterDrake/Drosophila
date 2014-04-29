package edu.lclark.drosophila;
import java.util.LinkedList;
import java.util.List;
public class ArenaAnalyzer {
	private Analyzer analyzer;
	private List<Fly> fullflylist;
	public ArenaAnalyzer(Analyzer a){
		this.analyzer=a;
	}
	
	public double AvgVelocityofArena(int Arena,int start, int end){
		fullflylist=analyzer.getFlies();
		List<Fly> arenaflies = new LinkedList<Fly>();
		for (Fly f:fullflylist){
			if (f.getArena()==Arena){
				arenaflies.add(f);
			}
		}
		double avg = 0;
		for(Fly f:arenaflies){
			avg+=f.averageVelFly(start, end);			
		}
		avg= avg/ arenaflies.size();
		return avg;
		}
}


