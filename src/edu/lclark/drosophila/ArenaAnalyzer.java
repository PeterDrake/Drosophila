package edu.lclark.drosophila;
import java.util.LinkedList;
import java.util.List;
public class ArenaAnalyzer {
	private Analyzer analyzer;
	private List<Fly> fullflylist;
	public ArenaAnalyzer(Analyzer a){
		this.analyzer=a;
		fullflylist=analyzer.getFlies();
	}
	public double AvgVelocityofArena(int Arena,int frame){
		List<Fly> arenaflies = new LinkedList<Fly>();
		for (Fly f:fullflylist){
			if (f.getArena()==Arena){
				arenaflies.add(f);
			}
		}
		return Analyzer.averageVelMultFlies(arenaflies, 0, analyzer.getTotalFrames())[frame];
	}
}


