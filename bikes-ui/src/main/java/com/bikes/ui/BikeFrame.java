package com.bikes.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.bikes.data.BikeDataSource;
import com.bikes.data.CustomerData;
import com.bikes.data.ProductData;
import com.bikes.data.SaleData;
import com.bikes.data.SalesPersonData;
import com.bikes.db.BikePostgres;

public class BikeFrame extends JFrame {
	
	public BikeFrame(String title) {
		super(title);
		
		BikeDataSource source = new BikePostgres();
		SalesPersonData spdata = new SalesPersonData(source);
		ProductData pdata = new ProductData(source);
		CustomerData cdata = new CustomerData(source);
		SaleData sdata = new SaleData(source);
		
		
		JTabbedPane tabPane = new JTabbedPane();
		this.setContentPane(tabPane);
		
		tabPane.add("SalesPersons", new SalesPersonPanel(spdata));
		tabPane.add("Products", new ProductPanel(pdata));
		tabPane.add("Customers", new CustomerPanel(cdata));
		tabPane.add("Sales", new SalePanel(sdata));
	}

}
