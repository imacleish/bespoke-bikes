package com.bikes.data;

import java.util.List;

import com.bikes.records.Customer;

public class CustomerData {
	
	private BikeDataSource source;
	
	public CustomerData(BikeDataSource source) {
		this.source = source;
	}
	
	public List<Customer> getAllCustomers() {
		return source.getCustomers();
	}
}
