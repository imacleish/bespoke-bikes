package com.bikes.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.bikes.data.BikeDataSource;
import com.bikes.records.Customer;
import com.bikes.records.Discount;
import com.bikes.records.InsertSale;
import com.bikes.records.Product;
import com.bikes.records.Sale;
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
		List<Product> result = new ArrayList<>();
		String QUERY = "SELECT id, name, manufacturer, style, purchase_price, sale_price, quantity, commission FROM product";
		
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY)) {
			while (rs.next()) {
				result.add(new Product(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("manufacturer"),
					rs.getString("style"),
					rs.getDouble("purchase_price"),
					rs.getDouble("sale_price"),
					rs.getInt("quantity"),
					rs.getDouble("commission")));
			}
		} catch (SQLException e) {
		     e.printStackTrace();
		} 
		return result;
	}

	@Override
	public List<Customer> getCustomers() {
		List<Customer> result = new ArrayList<>();
		String QUERY = "SELECT id, first_name, last_name, address, phone, start_date FROM customer";
		
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY)) {
			while (rs.next()) {
				result.add(new Customer(
					rs.getInt("id"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("address"),
					rs.getString("phone"),
					rs.getObject("start_date", LocalDate.class)));
			}
		} catch (SQLException e) {
		     e.printStackTrace();
		} 
		return result;
	}

	@Override
	public List<Discount> getDiscounts() {
		List<Discount> result = new ArrayList<>();
		String QUERY = "SELECT id, product, begin_date, end_date, discount_percent FROM discount";
		
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY)) {
			while (rs.next()) {
				result.add(new Discount(
					rs.getInt("id"),
					rs.getInt("product"),
					rs.getObject("begin_date", LocalDate.class),
					rs.getObject("end_date", LocalDate.class),
					rs.getDouble("discount_percent")));
			}
		} catch (SQLException e) {
		     e.printStackTrace();
		} 
		return result;
	}

	@Override
	public List<Sale> getSales() {
		List<Sale> result = new ArrayList<>();
		String QUERY = """
SELECT sale.id as sale_id, 
	product.id as product_id, 
	product.name as product_name, 
	product.commission as product_commission, 
	customer.first_name as customer_first_name,
	customer.last_name as customer_last_name, 
	salesperson.first_name as salesperson_first_name,
	salesperson.last_name as salesperson_last_name, 
	sale.sale_date as sale_date, 
	product.sale_price as sale_price
FROM sale
INNER JOIN product ON (sale.product=product.id)
INNER JOIN customer ON (sale.customer=customer.id)
INNER JOIN salesperson ON (sale.salesperson=salesperson.id)
		""";
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY)) {
			while (rs.next()) {
				result.add(new Sale(
					rs.getInt("sale_id"),
					rs.getInt("product_id"),
					rs.getString("product_name"),
					rs.getDouble("product_commission"),
					rs.getString("customer_first_name"),
					rs.getString("customer_last_name"),
					rs.getString("salesperson_first_name"),
					rs.getString("salesperson_last_name"),
					rs.getObject("sale_date", LocalDate.class),
					rs.getDouble("sale_price")));
			}
		} catch (SQLException e) {
		     e.printStackTrace();
		} 
		return result;
	}

	@Override
	public void insertSale(InsertSale sale) {
		String QUERY = "INSERT INTO sale (product, salesperson, customer, sale_date) VALUES (?, ?, ?, ?);";
		try(PreparedStatement stmt = conn.prepareStatement(QUERY)) {
			stmt.setInt(1, sale.productID());
			stmt.setInt(2, sale.salespersonID());
			stmt.setInt(3, sale.customerID());
			stmt.setObject(4, sale.date());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

}
