package com.bikes.data;

import java.util.List;

import com.bikes.records.Discount;
import com.bikes.records.FinalSale;
import com.bikes.records.InsertSale;
import com.bikes.records.Name;
import com.bikes.records.Sale;

public class SaleData {
	
	private BikeDataSource source;
	
	public SaleData(BikeDataSource source) {
		this.source = source;
	}
	
	public List<FinalSale> getAllSales() {
		List<Sale> sales = source.getSales();
		List<Discount> discounts = source.getDiscounts();
		List <FinalSale> finalSales = sales.stream()
				.map(sale -> this.discountSale(sale, discounts))
				.toList();
		return finalSales;
	}


	private FinalSale discountSale(Sale sale, List<Discount> discounts) {
		// check if any discount matches
		Discount matchingDiscount = null;
		for (Discount discount : discounts) {
			if (sale.productId() == discount.product() &&
				sale.date().isAfter(discount.beginDate()) &&
				sale.date().isBefore(discount.endDate())) 
			{
				matchingDiscount = discount;
			}
		}
		
		// Calculate final price
		double finalPrice;
		if (matchingDiscount != null) {
			finalPrice = sale.salePrice() * (1 - matchingDiscount.discountPercent());
		} else {
			finalPrice = sale.salePrice();
		}
		
		// calculate commission from final price and build final Sale
		double commission = finalPrice * sale.productCommission();
		return new FinalSale(sale, finalPrice, commission);
	}
	
	public List<Name> getSalespersonNames() {
		return source.getSalesPeople().stream()
				.map(sp -> new Name(sp.id(), sp.firstName(), sp.lastName()))
				.toList();
	}
	
	public List<Name> getCustomerNames() {
		return source.getCustomers().stream()
				.map(cust -> new Name(cust.id(), cust.firstName(), cust.lastName()))
				.toList();
	}
	
	public List<Name> getProductNames() {
		return source.getProducts().stream()
				.map(p -> new Name(p.id(), p.name(), ""))
				.toList();
	}

	public void createSale(InsertSale sale) {
		source.insertSale(sale);
	}
}
