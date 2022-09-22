package com.bikes.db;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bikes.records.Product;
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
		assertEquals("Egon", sp.firstName());
		assertEquals("Spengler", sp.lastName());
		assertEquals("300 Peachtree Drive", sp.address());
		assertEquals("333-333-3333", sp.phone());
		assertEquals(LocalDate.of(2020, 4, 28), sp.startDate());
		assertEquals(LocalDate.of(2022, 6, 6), sp.termDate());
		assertEquals("Slimer", sp.manager());
	}
	
	@Test
	public void testGetProducts() {
		List<Product> result = conn.getProducts();
		assertEquals(4, result.size());
		
		Product sp = result.get(1);
		assertEquals("Legion Freestyle", sp.name());
		assertEquals("Mongoose", sp.manufacturer());
		assertEquals("BMX", sp.style());
		assertEquals(199.99, sp.purchasePrice(), DELTA);
		assertEquals(329.99, sp.salePrice(), DELTA);
		assertEquals(8, sp.quantity());
		assertEquals(0.15, sp.commissionPercent(), DELTA);
	}

}
