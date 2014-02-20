package edu.lclark.drosophila;

import java.awt.Dimension;
import java.io.File;

import javax.swing.*;

public class AnalyzerPanel extends JPanel {
	
	private AnalyzerGui gui;
	private ImagePanel ipanel;
	
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
		if(gui.passDownImage()!=null)return gui.passDownImage().getName();
		return null;
	}
}
