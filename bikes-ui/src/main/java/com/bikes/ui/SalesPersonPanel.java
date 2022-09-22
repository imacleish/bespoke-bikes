package com.bikes.ui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.bikes.data.SalesPersonData;
import com.bikes.records.SalesPerson;

public class SalesPersonPanel extends JPanel {
	
	private SalesPersonData source;
	private List<SalesPerson> data;

	public SalesPersonPanel(SalesPersonData spdata) {
		this.source = spdata;
		data = source.getAllSalesPeople();

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JTable table = new JTable(new SalesPersonModel());
		JScrollPane scroll = new JScrollPane(table);
		this.add(scroll);
	}
	
	private class SalesPersonModel extends AbstractTableModel {
		
		private final List<String> columns = List.of("First Name", "Last Name", "Address", "Phone", 
				"Start Date", "Termination Date", "Manager");
		
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
			SalesPerson sp = data.get(row);
			return switch (column) {
				case 0 -> sp.firstName();
				case 1 -> sp.lastName();
				case 2 -> sp.address();
				case 3 -> sp.phone();
				case 4 -> sp.startDate();
				case 5 -> sp.termDate();
				case 6 -> sp.manager();
				default -> throw new IllegalArgumentException("Unexpected column: " + column);
			};
		}
		
	}

}
