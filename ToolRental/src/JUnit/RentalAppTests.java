package JUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import RentalApplication.RentalAgreement;
import RentalApplication.RentalApplication;

class RentalAppTests {
	RentalAgreement agreement;
	
	@BeforeEach
	public void setup() {
		agreement = null;
	}
	
	@Test
	void test1() {
		System.out.println("Test 1:");
		try {
			agreement = RentalApplication.checkOut("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
			fail("Test failed: Exception should have been thrown");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
	@Test
	void test2() {
		System.out.println("Test 2:");
		try {
			agreement = RentalApplication.checkOut("LADW", 3, 10, LocalDate.of(2020, 7, 2));
			agreement.printAgreement();
			
			assertTrue(agreement.getChargeDays() == 2);
			assertTrue(agreement.getPreDiscountCharge().equals(new BigDecimal(3.98).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getDiscountAmount().equals(new BigDecimal(0.40).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getFinalCharge().equals(new BigDecimal(3.58).setScale(2, RoundingMode.HALF_UP)));
		} catch(Exception e) {
			fail(e.getMessage());
		}

		
	}
	
	@Test
	void test3() {
		System.out.println("Test 3:");
		try {
			agreement = RentalApplication.checkOut("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
			agreement.printAgreement();
			
			assertTrue(agreement.getChargeDays() == 3);
			assertTrue(agreement.getPreDiscountCharge().equals(new BigDecimal(4.47).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getDiscountAmount().equals(new BigDecimal(1.12).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getFinalCharge().equals(new BigDecimal(3.35).setScale(2, RoundingMode.HALF_UP)));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void test4() {
		System.out.println("Test 4:");
		try {
			agreement = RentalApplication.checkOut("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
			agreement.printAgreement();
			
			assertTrue(agreement.getChargeDays() == 3);
			assertTrue(agreement.getPreDiscountCharge().equals(new BigDecimal(8.97).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getDiscountAmount().equals(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getFinalCharge().equals(new BigDecimal(8.97).setScale(2, RoundingMode.HALF_UP)));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void test5() {
		System.out.println("Test 5:");
		try {
			agreement = RentalApplication.checkOut("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
			agreement.printAgreement();
			
			assertTrue(agreement.getChargeDays() == 5);
			assertTrue(agreement.getPreDiscountCharge().equals(new BigDecimal(14.95).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getDiscountAmount().equals(new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getFinalCharge().equals(new BigDecimal(14.95).setScale(2, RoundingMode.HALF_UP)));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void test6() {
		System.out.println("Test 6:");
		try {
			agreement = RentalApplication.checkOut("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
			agreement.printAgreement();
			
			assertTrue(agreement.getChargeDays() == 1);
			assertTrue(agreement.getPreDiscountCharge().equals(new BigDecimal(2.99).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getDiscountAmount().equals(new BigDecimal(1.50).setScale(2, RoundingMode.HALF_UP)));
			assertTrue(agreement.getFinalCharge().equals(new BigDecimal(1.49).setScale(2, RoundingMode.HALF_UP)));
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
}
