package edu.lclark.drosophila;

import java.awt.Dimension;
import java.io.File;

import javax.swing.*;

public class AnalyzerPanel extends JPanel {
	
	private AnalyzerGui gui;
	private ImagePanel ipanel;
	
	public void sizeThresholdUpdate(int input) {
		gui.sizeThresholdUpdate(input);
	}

	AnalyzerPanel(AnalyzerGui gui) {
		this.gui = gui;
		ButtonPanel bpanel = new ButtonPanel(this);
		ipanel = new ImagePanel(this);
		add(bpanel);
		add(ipanel);
	}
	public void passImage(File file) {
		gui.passImage(file);		
	}
	public String passdownImage(){
		if(gui.passDownImage()!=null)return gui.passDownImage().getPath();
		return null;
	}
}
