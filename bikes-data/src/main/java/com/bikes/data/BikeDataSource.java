package com.bikes.data;

import java.util.List;

import com.bikes.records.Customer;
import com.bikes.records.Product;
import com.bikes.records.SalesPerson;

public interface BikeDataSource {
	
	public List<SalesPerson> getSalesPeople();
	
	public List<Product> getProducts();
	
}
