package com.bikes.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.bikes.data.BikeDataSource;
import com.bikes.records.Product;
import com.bikes.records.SalesPerson;

public class BikePostgres implements BikeDataSource {
	
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/bespoked_bikes";
	private static final String USER = "postgres";
	private static final String PASS = "postgres";
	
	private Connection conn;

	public BikePostgres() {
		try {
			for (Enumeration<Driver> e = DriverManager.getDrivers(); e.hasMoreElements();)
			       System.out.println(e.nextElement().getClass().getName());
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setSchema("public");
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<SalesPerson> getSalesPeople() {
		List<SalesPerson> result = new ArrayList<>();
		String QUERY = "SELECT id, first_name, last_name, address, phone, start_date, term_date, manager FROM salesperson";
		
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY)) {
			while (rs.next()) {
				result.add(new SalesPerson(
						rs.getInt("id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("address"),
						rs.getString("phone"),
						rs.getObject("start_date", LocalDate.class),
						rs.getObject("term_date", LocalDate.class),
						rs.getString("manager")));
			}
		} catch (SQLException e) {
		     e.printStackTrace();
		} 
		return result;
	}

	@Override
	public List<Product> getProducts() {
		
		return null;
	}

}
