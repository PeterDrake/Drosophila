package edu.lclark.drosophila;

import java.awt.Dimension;
import java.io.File;

import javax.swing.*;

public class AnalyzerPanel extends JPanel {
	
	private AnalyzerGui gui;
	
	public void sizeThresholdUpdate(int input) {
		gui.sizeThresholdUpdate(input);
	}

	AnalyzerPanel(AnalyzerGui gui) {
		this.gui = gui;
		ButtonPanel bpanel = new ButtonPanel(this);
		add(bpanel);
	}

	public void passImage(File file) {
		gui.passImage(file);		
	}
	
}
