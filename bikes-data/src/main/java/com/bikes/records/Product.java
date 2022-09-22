package com.bikes.records;

public record Product(	
	int id,
	String name,
	String manufacturer,
	String style,
	double purchasePrice,
	double salePrice,
	int quantity,
	double commissionPercent
) {}
