package edu.lclark.drosophila;

import java.awt.Dimension;
import java.io.File;

import javax.swing.*;

public class AnalyzerPanel extends JPanel {
	
	private AnalyzerGui gui;
	
	AnalyzerPanel(AnalyzerGui gui) {
		this.gui = gui;
		ButtonPanel bpanel = new ButtonPanel(this);
		add(bpanel);
	}

	public void passImage(File file) {
		gui.passImage(file);		
	}
	
}
