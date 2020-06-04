package com.tollparking.billing;

import java.math.BigDecimal;
import java.time.Instant;
import com.tollparking.exception.InvalidAmountException;

/**
 * PricingPerHourPolicy
 * extends PricingPerHourWithFixedAmountPolicy
 */
public class PricingPerHourPolicy extends PricingPerHourWithFixedAmountPolicy {

    /**
     * Creates a pricing policy with the following parameters
     *
     * @param pricePerHour the price that the customer wil pay for an hour stay
     * @throws InvalidAmountException if the pricePerHour argument given is negative
     */
    public PricingPerHourPolicy(BigDecimal pricePerHour) throws InvalidAmountException {
        super(pricePerHour, BigDecimal.ZERO);
    }

    /**
     * Calculates the price in between a start date and an end date with the following formula: (nrOfMinutes * pricePerMinute where
     * nrOfMinutes is the number of minutes taken in between the start date and end date)
     *
     * @param startDate the startDate of the period
     * @param endDate the endDate of the period
     * @return a BigDecimal value representing the amount of the bill the customer has to pay with a 2 decimal precision
     * @throws InvalidDateException if either of the dates is null or if end date is after the start date
     */
    @Override
    public BigDecimal calculatePrice(Instant startDate, Instant endDate) {
       return super.calculatePrice(startDate, endDate);
    }
}
