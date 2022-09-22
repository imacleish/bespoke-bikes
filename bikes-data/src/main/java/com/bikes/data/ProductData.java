package com.bikes.data;

import java.util.List;

import com.bikes.records.Product;

public class ProductData {

	private BikeDataSource source;
	
	public ProductData(BikeDataSource source) {
		this.source = source;
	}
	
	public List<Product> getAllProducts() {
		return source.getProducts();
	}
}
