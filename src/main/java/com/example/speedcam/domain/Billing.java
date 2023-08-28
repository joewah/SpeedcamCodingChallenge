package com.example.speedcam.domain;

/**
 * Calculates the fine based on the max allowed speed, the actual speed, a tolerance of 8 km/h and a modifier function
 * the maximum fine cannot exceed 10'000 CHF
 * @author wahljo1
 *
 */

public class Billing {

	public static final int TOLERANCE_KMH = 8; //8 km/h tolerance
	public static final int MAX_AMOUNT_CHF = 10000; 
	public static final double MODIFIER = 50.0;
	
	
	public static int calculateFineInChf(int maxSpeed, int actualSpeed) {
		int speedMinusTolerance = actualSpeed-TOLERANCE_KMH;
		int fine = 0;
		if(speedMinusTolerance>0) {
			fine = (int)(Math.round(maxSpeed-actualSpeed)*MODIFIER);
		}
		return fine;
	}
}
