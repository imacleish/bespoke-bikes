package com.bikes.records;

import java.time.LocalDate;

public record InsertSale(

		int productID,
		int salespersonID,
		int customerID,
		LocalDate date
		
) {

}
