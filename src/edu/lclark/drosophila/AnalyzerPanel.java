package edu.lclark.drosophila;

import java.io.File;
import java.util.List;

import javax.swing.*;

public class AnalyzerPanel extends JPanel {

	private AnalyzerGui gui;
	private ImagePanel ipanel;
	private MoviePanel mpanel;

	public void sizeThresholdUpdate(int input) {
		gui.sizeThresholdUpdate(input);
	}

	AnalyzerPanel(AnalyzerGui gui) {
		this.gui = gui;
		ButtonPanel bpanel = new ButtonPanel(this);
		ipanel = new ImagePanel(this);
		mpanel = new MoviePanel();
		add(bpanel);
		add(ipanel);
		add(mpanel);
	}

	public void passImage(File file) {
		gui.passImage(file);
	}

	public String passdownImage(int index) {
		if (gui.passDownImage(index) != null) {
			return gui.passDownImage(index).getPath();
		}
		return null;
	}
	public File getMovie(){
			return gui.getMovie();
	}
	
	public void playMovie(String sourceUrl){
		mpanel.play(sourceUrl);
	}

	public void setFlydentifiers() {
		ipanel.setFlydentifiers();
	}

	public List<Fly> getFlyList() {
		return gui.getFlies();
	}
}
