package com.bikes.records;

import java.time.LocalDate;

public record FinalSale(
		
		int id,
		String productName,
		String customerFirst,
		String customerLast,
		String spFirst,
		String spLast,
		LocalDate date,
		double finalPrice,
		double commission
) {

	public FinalSale(Sale sale, double finalPrice2, double commission2) {
		this(sale.id(),
		sale.productName(),
		sale.customerFirst(),
		sale.customerLast(),
		sale.spFirst(),
		sale.spLast(),
		sale.date(),
		finalPrice2,
		commission2);
	}
}
