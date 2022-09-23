package com.bikes.records;

public record Name(int id, String first, String last) {

	
	public String full() {
		return first + " " + last;
	}
	
	@Override
	public String toString() {
		return full();
	}
}
