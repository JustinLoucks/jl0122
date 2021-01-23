package RentalApplication;

import java.math.BigDecimal;

public class Jackhammer extends Tool {
	
	private static final BigDecimal CHARGE_AMOUNT = new BigDecimal(2.99);
	
	public Jackhammer(String brand, String code) {
		this.toolType = "Jackhammer";
		this.toolBrand = brand;
		this.toolCode = code;
		
		this.dailyCharge = CHARGE_AMOUNT;
		
		this.wkdayCharge = true;
		this.wkendCharge = false;
		this.holidayCharge = false;
	}
}
