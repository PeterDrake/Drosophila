package edu.lclark.drosophila;

import java.io.File;
import java.util.List;

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

	public List<Fly> getFlyList() {
		return gui.getFlies();
	}

	public String passdownImage(int index) {
		if (gui.passDownImage(index) != null) {
			return gui.passDownImage(index).getPath();
		}
		return null;
	}

	public void passImage(File file) {
		gui.passImage(file);
	}

	public void setDrawTrajectories(int startFrame, int endFrame) {
		ipanel.setDrawTrajectories(startFrame, endFrame);
		
	}

	public void setFlydentifiers() {
		ipanel.setFlydentifiers();
	}

	public void sizeThresholdUpdate(int input) {
		gui.sizeThresholdUpdate(input);
	}
}
