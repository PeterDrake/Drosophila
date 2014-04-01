package edu.lclark.drosophila;

import java.awt.EventQueue;

import javax.swing.JFrame;


public class DataPaneltest {
	public static void main(String[] args)
	   {
	      EventQueue.invokeLater(new Runnable()
	         {
	            public void run()
	            {
	               JFrame frame = new JFrame();
	               frame.add(new DataPanel(null));
	               frame.pack();
	               frame.setTitle("data component");
	               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	               frame.setVisible(true);
	            }
	         });
	   }
}
