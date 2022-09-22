package com.bikes.db;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bikes.records.Customer;
import com.bikes.records.Discount;
import com.bikes.records.Product;
import com.bikes.records.Sale;
import com.bikes.records.SalesPerson;

public class BikePostgresTest {
	
	private static final double DELTA = 0.001;
	private static BikePostgres conn;
	
	@BeforeClass
	public static void init() {
		conn = new BikePostgres();
	}
	
	@Test
	public void testGetSalesPeople() {
		List<SalesPerson> result = conn.getSalesPeople();
		assertEquals(4, result.size());
		
		SalesPerson sp = result.get(2);
		assertEquals(3, sp.id());
		assertEquals("Egon", sp.firstName());
		assertEquals("Spengler", sp.lastName());
		assertEquals("300 Peachtree Drive", sp.address());
		assertEquals("333-333-3333", sp.phone());
		assertEquals(LocalDate.of(2020, 4, 28), sp.startDate());
		assertEquals(LocalDate.of(2022, 5, 6), sp.termDate());
		assertEquals("Slimer", sp.manager());
	}
	
	@Test
	public void testGetProducts() {
		List<Product> result = conn.getProducts();
		assertEquals(4, result.size());
		
		Product p = result.get(1);
		assertEquals(2, p.id());
		assertEquals("Legion Freestyle", p.name());
		assertEquals("Mongoose", p.manufacturer());
		assertEquals("BMX", p.style());
		assertEquals(199.99, p.purchasePrice(), DELTA);
		assertEquals(329.99, p.salePrice(), DELTA);
		assertEquals(8, p.quantity());
		assertEquals(0.15, p.commissionPercent(), DELTA);
	}
	
	@Test
	public void testGetCustomers() {
		List<Customer> result = conn.getCustomers();
		assertEquals(5, result.size());
		
		Customer c = result.get(2);
		assertEquals(3, c.id());
		assertEquals("Milburn", c.firstName());
		assertEquals("Beckett", c.lastName());
		assertEquals("300 South Street", c.address());
		assertEquals("953-555-1820", c.phone());
		assertEquals(LocalDate.of(2022, 2, 7), c.startDate());
	}
	
	@Test
	public void testGetDiscounts() {
		List<Discount> result = conn.getDiscounts();
		assertEquals(1, result.size());
		
		Discount d = result.get(0);
		assertEquals(1, d.id());
		assertEquals(1, d.product());
		assertEquals(LocalDate.of(2022, 1, 1), d.beginDate());
		assertEquals(LocalDate.of(2022, 3, 31), d.endDate());
		assertEquals(0.4, d.discountPercent(), DELTA);
	}
	
	@Test
	public void testGetSales() {
		List<Sale> result = conn.getSales();
		assertEquals(13, result.size());
	}

}
