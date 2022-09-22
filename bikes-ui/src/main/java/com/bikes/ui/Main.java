package com.bikes.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {
	
	
	public static void main(String[] args) {
		BikeFrame frame = new BikeFrame("Bespoked Bikes");        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setPreferredSize(new Dimension(600, 400));
	    frame.pack();
	    frame.setVisible(true);
	}
	

}
