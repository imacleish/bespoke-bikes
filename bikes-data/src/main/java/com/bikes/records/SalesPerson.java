package com.bikes.records;

import java.time.LocalDate;

public record SalesPerson(
		
	int id,
	String firstName,
	String lastName,
	String address,
	String phone,
	LocalDate startDate,
	LocalDate termDate,
	String manager
		
) {}
