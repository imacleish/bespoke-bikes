package com.bikes.ui;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.bikes.data.ProductData;
import com.bikes.data.SalesPersonData;
import com.bikes.records.Product;
import com.bikes.records.SalesPerson;

public class ProductPanel extends JPanel {
	
	private ProductData source;
	private List<Product> data;

	public ProductPanel(ProductData psource) {
		this.source = psource;
		data = source.getAllProducts();

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JTable table = new JTable(new ProductModel());
		JScrollPane scroll = new JScrollPane(table);
		this.add(scroll);
	}
	
	private class ProductModel extends AbstractTableModel {
		
		private final List<String> columns = List.of("Name", "Manufacturer", "Style", "Purchase Price", 
				"Sales Price", "Quantity", "Commission");
		
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
			Product p = data.get(row);
			return switch (column) {
				case 0 -> p.name();
				case 1 -> p.manufacturer();
				case 2 -> p.style();
				case 3 -> p.purchasePrice();
				case 4 -> p.salePrice();
				case 5 -> p.quantity();
				case 6 -> p.commissionPercent();
				default -> throw new IllegalArgumentException("Unexpected column: " + column);
			};
		}
		
	}
}
