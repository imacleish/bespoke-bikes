package com.bikes.records;

import java.time.LocalDate;

public record Customer(
		
	int id,
	String firstName,
	String lastName,
	String address,
	String phone,
	LocalDate startDate
		
) {
}
