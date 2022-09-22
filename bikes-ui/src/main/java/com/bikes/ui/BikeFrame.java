package com.bikes.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.bikes.data.BikeDataSource;
import com.bikes.data.SalesPersonData;
import com.bikes.db.BikePostgres;

public class BikeFrame extends JFrame {
	
	public BikeFrame(String title) {
		super(title);
		
		BikeDataSource source = new BikePostgres();
		SalesPersonData spdata = new SalesPersonData(source);
		
		
		
		JTabbedPane tabPane = new JTabbedPane();
		this.setContentPane(tabPane);
		
		tabPane.add("SalesPersons", new SalesPersonPanel(spdata));
		tabPane.add("Products", new ProductPanel());
		tabPane.add("Customers", new CustomerPanel());
		tabPane.add("Sales", new SalePanel());
	}

}
