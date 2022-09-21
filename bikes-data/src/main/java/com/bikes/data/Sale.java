package com.bikes.data;

import java.time.LocalDate;

public record Sale(
		
	int id,
	String product,
	String salesperson,
	String customer,
	LocalDate salesDate
		
) {}
