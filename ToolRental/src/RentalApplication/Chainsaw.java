package RentalApplication;

import java.math.BigDecimal;

public class Chainsaw extends Tool {
	
	private static final BigDecimal CHARGE_AMOUNT = new BigDecimal(1.49);
	
	public Chainsaw(String brand, String code) {
		this.toolType = "Chainsaw";
		this.toolBrand = brand;
		this.toolCode = code;
		
		this.dailyCharge = CHARGE_AMOUNT;
		
		this.wkdayCharge = true;
		this.wkendCharge = false;
		this.holidayCharge = true;
	}
}
