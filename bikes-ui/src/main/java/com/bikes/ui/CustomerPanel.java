package com.bikes.ui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.bikes.data.CustomerData;
import com.bikes.records.Customer;

public class CustomerPanel extends JPanel {
	
	private CustomerData source;
	private List<Customer> data;

	public CustomerPanel(CustomerData cdata) {
		this.source = cdata;
		data = source.getAllCustomers();

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JTable table = new JTable(new ProductModel());
		JScrollPane scroll = new JScrollPane(table);
		this.add(scroll);
	}
	
	private class ProductModel extends AbstractTableModel {
		
		private final List<String> columns = List.of("First Name", "Last Name", "Address", 
				"Phone Number", "Start Date");
		
		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return columns.size();
		}
		
	    public String getColumnName(int col) {
	        return columns.get(col);
	    }
		
		public boolean isCellEditable(int row, int col) { 
			return true; 
		}

		@Override
		public Object getValueAt(int row, int column) {
			Customer c = data.get(row);
			return switch (column) {
				case 0 -> c.firstName();
				case 1 -> c.lastName();
				case 2 -> c.address();
				case 3 -> c.phone();
				case 4 -> c.startDate();
				default -> throw new IllegalArgumentException("Unexpected column: " + column);
			};
		}
		
	}
}
