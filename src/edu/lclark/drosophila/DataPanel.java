package edu.lclark.drosophila;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class DataPanel extends JPanel {
	private AnalyzerPanel analyzerpanel;
	private List<Fly> flyList;
	private JTextArea text;
	public DataPanel(AnalyzerPanel analyzerpanel){
		
		this.analyzerpanel=analyzerpanel;
		flyList=analyzerpanel.getFlyList();
		JTextArea text= new JTextArea(12,24);
		JScrollPane scrollpane= new JScrollPane(text);
		add(scrollpane);
		text.append(createLabels());
	}
	public void paintcomponent(Graphics G){
		text.setText(createLabels());
		text.append(createData(0,analyzerpanel.getTotalFrames()));
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
	public String createData(int start, int end){
		String Data= "";
		if(flyList!=null){
		for(Fly fly : flyList){
			Data+=createFlyLine(fly,start,end);
		}
	}
		return Data;
	}
	public String createFlyLine(Fly fly, int start, int end){
		String Data ="";
		Data += fly+"   "+fly.averageVelFly(start, end)+"   "+fly.totalDistance(start, end);
		return Data;
	}
	
}

