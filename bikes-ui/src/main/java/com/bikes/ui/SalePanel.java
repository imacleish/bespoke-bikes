package com.bikes.ui;

import java.awt.Dimension;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import com.bikes.data.SaleData;
import com.bikes.records.FinalSale;
import com.bikes.records.InsertSale;
import com.bikes.records.Name;

public class SalePanel extends JPanel {
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
	
	private SaleData source;
	private List<FinalSale> data;
	
	private JTextField afterFilter;
	private JTextField beforeFilter;
	private TableRowSorter<ProductModel> sorter;
	
	private JComboBox<Name> productPicker;
	private JComboBox<Name> spPicker;
	private JComboBox<Name> customerPicker;
	private JTextField saleDate;
	
	

	public SalePanel(SaleData sdata) {
		this.source = sdata;
		data = source.getAllSales();

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Top label with filter date pickers
		JPanel filterPanel = new JPanel();
		filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.LINE_AXIS));
		this.add(filterPanel);
		filterPanel.add(new JLabel("Filter by date (format YYYY-MM-DD) from "));
		afterFilter = new JTextField("2022-1-1");
		filterPanel.add(afterFilter);
		filterPanel.add(new JLabel(" to "));
		beforeFilter = new JTextField("2022-7-1");
		filterPanel.add(beforeFilter);
		JButton filterButton = new JButton("Filter");
		filterButton.addActionListener(event -> sorter.sort());
		filterPanel.add(filterButton);
		filterPanel.add(Box.createHorizontalGlue());
		
		// Table
		ProductModel model = new ProductModel();
		sorter = new TableRowSorter<>(model);
		sorter.setRowFilter(new DateFilter());
		JTable table = new JTable(model);
		table.setRowSorter(sorter);
		JScrollPane scroll = new JScrollPane(table);
		this.add(scroll);
		
		// Bottom panel with fields to add sale
		JPanel addPanel = new JPanel();
		addPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.LINE_AXIS));
		this.add(addPanel);
		addPanel.add(new JLabel("Product: "));
		productPicker = new JComboBox<>(source.getProductNames().toArray(new Name[0]));
		addPanel.add(productPicker);
		addPanel.add(Box.createHorizontalGlue());
		addPanel.add(new JLabel("Salesperson: "));
		spPicker = new JComboBox<>(source.getSalespersonNames().toArray(new Name[0]));
		addPanel.add(spPicker);
		

		JPanel addPanel2 = new JPanel();
		addPanel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		addPanel2.setLayout(new BoxLayout(addPanel2, BoxLayout.LINE_AXIS));
		this.add(addPanel2);
		addPanel2.add(new JLabel("Customer: "));
		customerPicker = new JComboBox<>(source.getCustomerNames().toArray(new Name[0]));
		addPanel2.add(customerPicker);
		addPanel2.add(Box.createRigidArea(new Dimension(50,0)));
		addPanel2.add(new JLabel("Date: "));
		saleDate = new JTextField("YYYY-MM-DD");
		addPanel2.add(saleDate);
		addPanel2.add(Box.createRigidArea(new Dimension(50,0)));
		JButton addButton = new JButton("Add Sale");
		addPanel2.add(addButton);
		addButton.addActionListener(ae -> {
			// Parse date
			LocalDate date = LocalDate.now();
			try {
				date = LocalDate.parse(saleDate.getText(), formatter);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid date: " + saleDate.getText(),
			               "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Collect IDs
			int productID = ((Name) productPicker.getSelectedItem()).id();
			int spID = ((Name) spPicker.getSelectedItem()).id();
			int customerID = ((Name) customerPicker.getSelectedItem()).id();
			
			InsertSale sale = new InsertSale(productID, spID, customerID, date);
			new SwingWorker<Object, Object>() {
				@Override
				protected Object doInBackground() throws Exception {
					source.createSale(sale);
					return null;
				}
				
				@Override
				protected void done() {
					data = source.getAllSales();
					sorter.sort();
				}
			}.execute();
		});
	}


	private class DateFilter extends RowFilter<ProductModel, Integer> {
		@Override
		public boolean include(Entry<? extends ProductModel, ? extends Integer> entry) {
			try {
				LocalDate afterDate = LocalDate.parse(afterFilter.getText(), formatter);
				LocalDate beforeDate = LocalDate.parse(beforeFilter.getText(), formatter);
				FinalSale row = data.get(entry.getIdentifier());
				return row.date().isAfter(afterDate) &&
					   row.date().isBefore(beforeDate);
			} catch(Exception e) {
				System.err.println("Bad time string");
				return true;
			}
		}

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
