package edu.lclark.drosophila;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class DataPanel extends JPanel {
	private AnalyzerPanel analyzerpanel;
	private List<Fly> flyList;
	private JTextArea text;
	/**
	 * creates this panel which at the moment displays all the data for the analytical stories we want to show
	 * @param analyzerpanel
	 */
	public DataPanel(AnalyzerPanel analyzerpanel){
		
		this.analyzerpanel=analyzerpanel;
		flyList=analyzerpanel.getFlyList();
		text= new JTextArea(12,24);
		JScrollPane scrollpane= new JScrollPane(text);
		add(scrollpane);
		text.append(createLabels());
	}
	/**
	 * the paint component for this piece. Directly tied into the fly list.
	 */
	public void paintComponent(Graphics G){
		if(analyzerpanel.getFlyList().size()>0){
		text.setText(createLabels());
		text.append(createData(1,analyzerpanel.getTotalFrames()));
		}
	}
	/**
	 * sets this panels default size
	 */
	public Dimension getPreferredSize(){
		return new Dimension(300,300);
	}
	/**
	 * creates the String that labels our data
	 * @return Labels
	 */
	public String createLabels(){
		String Labels=String.format("%-15s %-25s %-5s \n", "fly", "average velocity", "total distance");
		return Labels;
	}
	/**
	 * Creates the text block that contains the data for all the flies in the fly list
	 * @param start
	 * @param end
	 * @return String Data
	 */
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
	/**
	 * takes a single fly and creates a string to display its avg velocity and total distance"
	 * @param fly
	 * @param start
	 * @param end
	 * @return String FlyLine
	 */
	public String createFlyLine(Fly fly, int start, int end){
		String Data =String.format("%-15s %-25f %-5f", fly.toString(), fly.averageVelFly(start, end), fly.totalDistance(start, end));
		return Data;
	}
	
}

