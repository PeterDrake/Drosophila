package edu.lclark.drosophila;

import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

public class RegionSelectPanel extends JPanel {
	
	private AnalyzerPanel analyzerPanel;
	private JFrame frame;
	private JList<Integer> leftList;
	private JList<Integer> rightList;
	private DefaultListModel<Integer> leftListModel;
	private DefaultListModel<Integer> rightListModel;
	private List<Fly> flies;
	private List<Integer> listOfRegions;
	private JButton addButton;
	private JButton removeButton;
	private JButton applyButton;
	

	public RegionSelectPanel(AnalyzerPanel analyzerPanel, JFrame frame){
		this.analyzerPanel = analyzerPanel;
		this.frame = frame;
		listOfRegions = new LinkedList<Integer>();
		leftListModel = new DefaultListModel<Integer>();
		rightListModel = new DefaultListModel<Integer>();
		flies=analyzerPanel.getFlyList();
		for(int i =0; i<flies.size(); i++){
			if(!listOfRegions.contains(flies.get(i).getArena())){
				listOfRegions.add(flies.get(i).getArena());
			}
		}
		for(int i = 0; i<listOfRegions.size(); i++){
			leftListModel.addElement(listOfRegions.get(i));
		}
		leftList=new JList<Integer>(leftListModel);
		this.add(leftList);
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		applyButton = new JButton("Apply");
		this.add(addButton);
		this.add(removeButton);
		this.add(applyButton);
	}
	
	public Dimension getPreferredSize() {
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		return new Dimension((int)(analyzerPanel.getWidth() * (3.0 / 8.0)), (int)(analyzerPanel.getHeight() * 2.0 / 3.0));
	}
	
	

}
