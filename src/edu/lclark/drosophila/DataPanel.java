package edu.lclark.drosophila;

import javax.swing.*;
import java.awt.*;

public class DataPanel extends JPanel {
	private AnalyzerPanel analyzerpanel;
	public DataPanel(AnalyzerPanel analyzerpanel){
		this.analyzerpanel=analyzerpanel;
		JTextArea textarea= new JTextArea();
		JScrollPane scrollpane= new JScrollPane(textarea);
		add(scrollpane);
	}
}
