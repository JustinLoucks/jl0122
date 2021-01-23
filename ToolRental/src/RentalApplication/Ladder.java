package RentalApplication;

import java.math.BigDecimal;

public class Ladder extends Tool {
	
	private static final BigDecimal CHARGE_AMOUNT = new BigDecimal(1.99);
	
	public Ladder(String brand, String code) {
		this.toolType = "Ladder";
		this.toolBrand = brand;
		this.toolCode = code;
		
		this.dailyCharge = CHARGE_AMOUNT;
		
		this.wkdayCharge = true;
		this.wkendCharge = true;
		this.holidayCharge = false;
	}
}
