package com.bikes.ui;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.bikes.data.SaleData;
import com.bikes.records.Customer;
import com.bikes.records.FinalSale;
import com.bikes.records.Sale;

public class SalePanel extends JPanel {
	
	private SaleData source;
	private List<FinalSale> data;

	public SalePanel(SaleData sdata) {
		this.source = sdata;
		data = source.getAllSales();

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JTable table = new JTable(new ProductModel());
		JScrollPane scroll = new JScrollPane(table);
		this.add(scroll);
	}
	
	private class ProductModel extends AbstractTableModel {
		
		private final DecimalFormat df = new DecimalFormat("0.00");
		
		private final List<String> columns = List.of("Product", "Customer First", "Customer Last", 
				"Salesperson First", "Salesperson Last", "Date", "Final Price", "Commission");
		
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
			FinalSale c = data.get(row);
			return switch (column) {
				case 0 -> c.productName();
				case 1 -> c.customerFirst();
				case 2 -> c.customerLast();
				case 3 -> c.spFirst();
				case 4 -> c.spLast();
				case 5 -> c.date();
				case 6 -> df.format(c.finalPrice());
				case 7 -> df.format(c.commission());
				default -> throw new IllegalArgumentException("Unexpected column: " + column);
			};
		}
		
	}
}
