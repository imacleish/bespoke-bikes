package com.bikes.data;

import java.util.ArrayList;
import java.util.List;

import com.bikes.records.FinalSale;
import com.bikes.records.Quarter;
import com.bikes.records.Sale;
import com.bikes.records.SalesPerson;
import com.bikes.records.TotalCommission;

public class CommissionData {
	
	private BikeDataSource source;
	private SaleData saleData;
	
	public CommissionData(BikeDataSource source, SaleData saleData) {
		this.source = source;
		this.saleData = saleData;
	}
	
	public List<TotalCommission> getCommissions(Quarter quarter) {
		List<TotalCommission> commissions = new ArrayList<>();
		List<SalesPerson> people = source.getSalesPeople();
		List<FinalSale> sales = saleData.getAllSales();
		
		// filter sales to given quarter
		sales = sales.stream()
					.filter(s -> s.date().isAfter(quarter.after())
							  && s.date().isBefore(quarter.before()))
					.toList();
		
		// for each SP total remaining sales
		for (SalesPerson sp : people) {
			double total = 0;
			for (FinalSale sale : sales) {
				if (sale.spFirst().equals(sp.firstName()) &&
					sale.spLast().equals(sp.lastName())) {
					total += sale.commission();
				}
			}
			commissions.add(new TotalCommission(sp.firstName(), sp.lastName(), total));
		}
		
		return commissions;
	}
}
