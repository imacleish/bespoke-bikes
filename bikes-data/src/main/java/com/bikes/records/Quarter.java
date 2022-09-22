package com.bikes.records;

import java.time.LocalDate;

public class Quarter {

	public int year;
	public int quarter;
	
	public Quarter(int year, int quarter) {
		this.year = year;
		this.quarter = quarter;
	}
	
	@Override
	public String toString() {
		return "" + year + "-Q" + quarter;
	}
	
	public LocalDate after() {
		return switch (quarter) {
			case 1 -> LocalDate.of(year - 1, 12, 31);
			case 2 -> LocalDate.of(year, 3, 31);
			case 3 -> LocalDate.of(year, 6, 30);
			case 4 -> LocalDate.of(year, 9, 30);
			default -> throw new IllegalArgumentException("Unexpected quarter value: " + quarter);
		};
	}
	
	public LocalDate before() {
		return switch (quarter) {
			case 1 -> LocalDate.of(year, 4, 1);
			case 2 -> LocalDate.of(year, 7, 1);
			case 3 -> LocalDate.of(year, 10, 1);
			case 4 -> LocalDate.of(year + 1, 1, 1);
			default -> throw new IllegalArgumentException("Unexpected quarter value: " + quarter);
		};
	}
	
}
