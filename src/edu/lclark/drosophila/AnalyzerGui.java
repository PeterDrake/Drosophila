package edu.lclark.drosophila;

import java.awt.EventQueue;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;

public class AnalyzerGui extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Analyzer analyzer;
	
	private AnalyzerPanel analyzerPanel;

	public AnalyzerGui(Analyzer a) {
		this.analyzer = a;
		this.analyzerPanel = new AnalyzerPanel(this);
	}

	public double[] getAverageVelocity() {
		return analyzer.averageVelMultFlies(analyzer.getFlies(), 0, analyzer.getFlies().get(0).getVx().length);
	}

	public List<Fly> getFlies() {
		return analyzer.getFlies();
	}

	public File passDownImage(int index) {
		return analyzer.getImage(index);
	}

	public void passImage(File file) {
		analyzer.flydentify(file);

	}

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("Flydentifier");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.add(analyzerPanel);
				frame.pack();
			}
		});
		analyzerPanel.repaint();
	}

	public void sizeThresholdUpdate(int input) {
		analyzer.sizeThresholdUpdate(input);
	}

}
