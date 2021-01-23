package RentalApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class RentalAgreement {
	
	private String toolType;
	private String toolBrand;
	private String toolCode;
	
	private BigDecimal dailyCharge;
	private int chargeDays;
	private int rentalLength;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private BigDecimal preDiscountCharge;
	private int discountPercent;
	private BigDecimal discountAmount;
	public String getToolType() {
		return toolType;
	}

	public String getToolBrand() {
		return toolBrand;
	}

	public String getToolCode() {
		return toolCode;
	}

	public BigDecimal getDailyCharge() {
		return dailyCharge;
	}

	public int getChargeDays() {
		return chargeDays;
	}

	public int getRentalLength() {
		return rentalLength;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public BigDecimal getPreDiscountCharge() {
		return preDiscountCharge;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public BigDecimal getFinalCharge() {
		return finalCharge;
	}

	public Tool getTool() {
		return tool;
	}

	private BigDecimal finalCharge;
	
	private Tool tool;
	
	
	public RentalAgreement(String toolCode, int rentalLength, int discount, LocalDate checkoutDate) {
		//initial agreement data from checkout
		this.toolCode = toolCode;
		this.rentalLength = rentalLength;
		this.discountPercent = discount;
		this.checkoutDate = checkoutDate;
	}
	
	/*
	 *Generate the rest of the agreement with all info
	 *
	 */
	public void generateFullAgreement() {
		tool = getToolInfo(toolCode);
		toolBrand = tool.toolBrand;
		toolType = tool.toolType;
		dailyCharge = tool.dailyCharge;
		
		dueDate = checkoutDate.plusDays(rentalLength);
		
		//chargeable days do not include the checkout date, so add one day
		int noChargeDays = calculateNoChargeDays(checkoutDate.plusDays(1), dueDate); 
		
		chargeDays = rentalLength - noChargeDays;
		
		preDiscountCharge = dailyCharge.multiply(new BigDecimal(chargeDays));
		preDiscountCharge = preDiscountCharge.setScale(2, RoundingMode.HALF_UP);
		
		discountAmount = preDiscountCharge.multiply(new BigDecimal(discountPercent / 100.0));
		discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP);
		
		finalCharge = preDiscountCharge.subtract(discountAmount);
	}
	
	/*
	 * Write out full agreement to console
	 */
	public void printAgreement() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/YY");
		System.out.print(
							"Tool Code: " + toolCode + "\n" +
							"Tool Type: " + toolType + "\n" +
							"Tool Brand: " + toolBrand + "\n" +
							"Rental Days: " + rentalLength + "\n" +
							"Checkout Date: " + dateFormat.format(checkoutDate) + "\n" +
							"Due Date: " + dateFormat.format(dueDate) + "\n" +
							"Daily Rental Charge: " + currencyFormat(dailyCharge) + "\n" +
							"Charge Days: " + chargeDays + "\n" +
							"Pre-discount Charge: " + currencyFormat(preDiscountCharge) + "\n" +
							"Discount Percent: " + discountPercent + "%\n" +
							"Discount Amount: " + currencyFormat(discountAmount) + "\n" +
							"Final Charge: " + currencyFormat(finalCharge) + "\n\n"
				);
	}
	
	/*
	 * Determine how many days will not be charged based on Holidays and Tool type
	 */
	private int calculateNoChargeDays(LocalDate firstChargeDate, LocalDate dueDate) {
		int noChargeDays = 0;
		
		LocalDate indepDay = LocalDate.of(firstChargeDate.getYear(), Month.JULY, 4);
		LocalDate laborDay = LocalDate.of(firstChargeDate.getYear(), Month.SEPTEMBER, 1);
		
		laborDay = laborDay.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		
		//finds the following monday if Independence Day falls on a Sunday, Friday before if it falls on Saturday
		if(indepDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
			indepDay.minusDays(1);
		}	
		else if (indepDay.getDayOfWeek() == DayOfWeek.SUNDAY)
		{
			indepDay.plusDays(1);
		}
		
		//does Independence Day or Labor Day fall between the range of checkout and due date + 1, due date must be inclusive in charge days
		if(!tool.holidayCharge) {
			if(indepDay.isAfter(checkoutDate) && indepDay.isBefore(dueDate.plusDays(1))) {
				noChargeDays++;
			}
			if(laborDay.isAfter(checkoutDate) && laborDay.isBefore(dueDate.plusDays(1))) {
				noChargeDays++;
			}
		}
		
		//If Tool has weekend charges, loop through days between checkout and due date to determine how many weekend days
		if(!tool.wkendCharge) {
			for(LocalDate currentDate = firstChargeDate; currentDate.isBefore(dueDate.plusDays(1)); currentDate = currentDate.plusDays(1)) {
				
				DayOfWeek day = currentDate.getDayOfWeek();
				
				if(day.equals(DayOfWeek.SUNDAY) || day.equals(DayOfWeek.SATURDAY)) {
					noChargeDays++;
				}
			}
		}
		
		return noChargeDays;
	}
	
	/*
	 * Format decimal amounts into dollars
	 */
	public static String currencyFormat(BigDecimal amount) {
		return NumberFormat.getCurrencyInstance().format(amount);
	}
	
	/*
	 * Create Tool objects based on tool code
	 */
	public static Tool getToolInfo(String toolCode) {
		Tool tool = null;
		
		switch(toolCode) {
		case "LADW": 
			tool = new Ladder("Werner", "LADW");
			break;
		case "CHNS":
			tool = new Chainsaw("Stihl", "CHNS");
			break;
		case "JAKR": 
			tool =  new Jackhammer("Rigid", "JAKR");
			break;
		case "JAKD":
			tool = new Jackhammer("DeWalt", "JAKD");
			break;
		}
		
		return tool;
	}
}
