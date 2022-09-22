package com.bikes.records;

import java.time.LocalDate;

public record Sale (
		
	int id,
	int productId,
	String productName,
	double productCommission,
	String customerFirst,
	String customerLast,
	String spFirst,
	String spLast,
	LocalDate date,
	double salePrice
) {
	
	
	
}
