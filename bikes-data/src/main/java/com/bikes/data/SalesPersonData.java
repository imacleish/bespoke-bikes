package com.bikes.data;

import java.util.List;

import com.bikes.records.SalesPerson;

public class SalesPersonData {
	
	private BikeDataSource source;
	
	public SalesPersonData(BikeDataSource source) {
		this.source = source;
	}
	
	public List<SalesPerson> getAllSalesPeople() {
		return source.getSalesPeople();
	}

}
