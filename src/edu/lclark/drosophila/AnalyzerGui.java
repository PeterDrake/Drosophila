package edu.lclark.drosophila;

import javax.swing.JFrame;

public class AnalyzerGui extends JFrame{
	
	private Analyzer a;
	private ButtonPanel bpanel;
	
	public AnalyzerGui(Analyzer a) {
		this.a = a;
		bpanel = new ButtonPanel();
		
	}
	
}
