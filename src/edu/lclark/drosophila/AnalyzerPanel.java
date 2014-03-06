package edu.lclark.drosophila;

import java.io.File;
import java.util.List;

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

	public String passdownImage(int index) {
		if (gui.passDownImage(index) != null) {
			return gui.passDownImage(index).getPath();
		}
		return null;
	}

	public void setFlydentifiers() {
		ipanel.setFlydentifiers();
	}

	public List<Fly> getFlyList() {
		return gui.getFlies();
	}

	public double[] getAverageVelocity() {
		return gui.getAverageVelocity();
	}
}
