package edu.lclark.drosophila;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
		java.util.Collections.sort(listOfRegions);
		for(int i = 0; i<listOfRegions.size(); i++){
			leftListModel.addElement(listOfRegions.get(i));
		}
		
		leftList=new JList<Integer>(leftListModel);
		rightList = new JList<Integer>(rightListModel);
		this.add(leftList);
		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonAction());
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new RemoveButtonAction());
		applyButton = new JButton("Apply");
		applyButton.addActionListener(new ApplyButtonAction());
		this.add(addButton);
		this.add(removeButton);
		this.add(applyButton);
		this.add(rightList);
	}
	
	private class AddButtonAction implements ActionListener {

		
		public void actionPerformed(ActionEvent e) {
			List<Integer> tempList = leftList.getSelectedValuesList();
			for (Integer i : tempList) {
				leftListModel.remove(leftListModel.indexOf(i));
				rightListModel.addElement(i);
			}
		}
	}
	
	private class RemoveButtonAction implements ActionListener {

		
		public void actionPerformed(ActionEvent e) {
			List<Integer> tempList = rightList.getSelectedValuesList();
			for (Integer i : tempList) {
				rightListModel.remove(rightListModel.indexOf(i));
				leftListModel.addElement(i);
				
			}
		}
	}
	
	private class ApplyButtonAction implements ActionListener {

		
		public void actionPerformed(ActionEvent e) {
			analyzerPanel.setRegionsOfInterest(rightListModel);
		}
	}
	
	public Dimension getPreferredSize() {
//		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		return new Dimension((int)(analyzerPanel.getWidth() * (3.0 / 8.0)), (int)(analyzerPanel.getHeight() * 2.0 / 3.0));
	}
	
	

}
