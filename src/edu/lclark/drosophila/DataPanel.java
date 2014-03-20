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
		text= new JTextArea(12,24);
		JScrollPane scrollpane= new JScrollPane(text);
		add(scrollpane);
		text.append(createLabels());
	}
	public void paintComponent(Graphics G){
		if(analyzerpanel.getFlyList().size()>0){
		text.setText(createLabels());
		text.append(createData(1,analyzerpanel.getTotalFrames()));
		}
	}
	public Dimension getPreferredSize(){
		return new Dimension(300,300);
	}
	public boolean update(){
		return true;
	}
	public String createLabels(){
		String Labels=String.format("%-15s %-25s %-5s \n", "fly", "average velocity", "total distance");
		return Labels;
	}
	public String createData(int start, int end){
		String Data= "";
		if(analyzerpanel.getFlyList()!=null){
		for(Fly fly : analyzerpanel.getFlyList()){
			Data+=createFlyLine(fly,start,end);
			Data+="\n";
		}
	}
		return Data;
	}
	public String createFlyLine(Fly fly, int start, int end){
		String Data =String.format("%-15s %-25f %-5f", fly.toString(), fly.averageVelFly(start, end), fly.totalDistance(start, end));
		return Data;
	}
	
}

