package edu.lclark.drosophila;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class DataPanel extends JPanel {
	private AnalyzerPanel analyzerpanel;
	private List<Fly> flyList;
	public DataPanel(AnalyzerPanel analyzerpanel){
		
		this.analyzerpanel=analyzerpanel;
		flyList=analyzerpanel.getFlyList();
		JTextArea textarea= new JTextArea(12,24);
		JScrollPane scrollpane= new JScrollPane(textarea);
		add(scrollpane);
		textarea.append(createLabels());
	}
	public Dimension getPreferredSize(){
		return new Dimension(300,300);
	}
	public boolean update(){
		return true;
	}
	public String createLabels(){
		String Labels="";
		Labels+="Fly   AvgVelocity   TotalTrajectory";
		return Labels;
	}
	public String createFlyLine(Fly fly, int frame){
		String Data ="";
		Data += fly+"   "+fly.getAvgVelocity()+"   "+fly.getTotalTrajectory();
	}
}
