package com.bikes.records;

import java.time.LocalDate;

public record Discount(
		
	int id,
	int product,
	LocalDate beginDate,
	LocalDate endDate,
	double discountPercent
		
) {}
