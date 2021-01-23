package RentalApplication;

import java.time.LocalDate;
import java.util.*;

public class RentalApplication {

	public static RentalAgreement checkOut(String toolCode, int rentalLength, int discount, LocalDate checkoutDate) throws Exception {
		
		if(rentalLength < 1)
			throw new Exception("The length of the rental must be one day or longer.");
		
		if(discount < 0 || discount > 100)
			throw new Exception("Discounts must be in the range 0-100 percent.");
		
		RentalAgreement agreement = new RentalAgreement(toolCode, rentalLength, discount, checkoutDate);
		
		agreement.generateFullAgreement();
		
		return agreement;
	}	
}
