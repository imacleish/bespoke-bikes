package com.bikes.ui;


import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.bikes.data.CommissionData;
import com.bikes.records.Quarter;
import com.bikes.records.TotalCommission;

public class CommissionPanel extends JPanel {
	
	private final Quarter[] quarters = {new Quarter(2021, 4), new Quarter(2022, 1), 
			new Quarter(2022, 2), new Quarter(2022, 3), new Quarter(2022, 4)};

	private CommissionData source;
	private List<TotalCommission> data = List.of();
	

	public CommissionPanel(CommissionData cdata) {
		this.source = cdata;

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Top panel with quarter combo box
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		this.add(topPanel);
		topPanel.add(new JLabel("Select Quarter: "));
		JComboBox<Quarter> quarterPicker = new JComboBox<>(quarters);
		topPanel.add(quarterPicker);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(Box.createHorizontalGlue());

		// Table
		JTable table = new JTable(new CommissionModel());
		JScrollPane scroll = new JScrollPane(table);
		this.add(scroll);
		
		quarterPicker.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Quarter quarter = (Quarter) e.getItem();
				data = source.getCommissions(quarter);
				table.setModel(new CommissionModel()); // causes reset of table
		    }
		});
	}

	
	private class CommissionModel extends AbstractTableModel {
		
		private final DecimalFormat df = new DecimalFormat("0.00");
		
		private final List<String> columns = List.of("First Name", "Last Name", "Total Commission");
		
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
			TotalCommission tc = data.get(row);
			return switch (column) {
				case 0 -> tc.firstname();
				case 1 -> tc.lastname();
				case 2 -> df.format(tc.totalCommission());
				default -> throw new IllegalArgumentException("Unexpected column: " + column);
			};
		}
		
	}
}
