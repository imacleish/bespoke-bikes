package com.bikes.data;

import java.time.LocalDate;

public record Discount(
		
	int id,
	String product,
	LocalDate startDate,
	LocalDate endDate,
	double discount
		
) {}
