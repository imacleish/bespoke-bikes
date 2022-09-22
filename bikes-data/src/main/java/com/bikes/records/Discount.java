package com.bikes.records;

import java.time.LocalDate;

public record Discount(
		
	int id,
	String product,
	LocalDate startDate,
	LocalDate endDate,
	double discount
		
) {}
