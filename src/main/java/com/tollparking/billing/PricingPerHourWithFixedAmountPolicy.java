package com.tollparking.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import com.tollparking.exception.InvalidAmountException;
import com.tollparking.exception.InvalidDateException;

public class PricingPerHourWithFixedAmountPolicy implements PricingPolicy {

    private static Logger LOG = LoggerFactory.getLogger(PricingPerHourWithFixedAmountPolicy.class);

    /**
     * the pricePerHour value of the policy
     */
    private BigDecimal pricePerHour;

    /**
     * the fixedAmount of the policy
     */
    private BigDecimal fixedAmount;

    /**
     * Creates a pricing policy with the following parameters
     *
     * @param pricePerHour the price per hour
     * @param fixedAmount the fixed amount to pe paid, besides the calculated stay
     * @throws InvalidAmountException if the amount specified for either of the parameters are negative
     *
     */
    public PricingPerHourWithFixedAmountPolicy(BigDecimal pricePerHour, BigDecimal fixedAmount) throws InvalidAmountException {
        if ((pricePerHour.compareTo(BigDecimal.ZERO) == -1) || (fixedAmount.compareTo(BigDecimal.ZERO) == -1))
            throw new InvalidAmountException("The amounts cannot be negative");
        this.pricePerHour = pricePerHour;
        this.fixedAmount = fixedAmount;
    }

    /**
     * Calculates the price in between a start date and an end date with the following formula:
     * (fixedAmount + nrOfMinutes * pricePerMinute where nrOfMinutes is the number of minutes taken
     * in between the start date and end date)
     *
     * @param startDate the startDate of the period
     * @param endDate the endDate of the period
     * @return a BigDecimal value representing the amount of the bill with a 2 decimal precision
     * @throws InvalidDateException if either of the dates is null or if end date is after the start date
     */
    @Override
    public BigDecimal calculatePrice(Instant startDate, Instant endDate) throws InvalidDateException {
        if (startDate == null || endDate == null) {
            throw new InvalidDateException("The dates cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidDateException("The start date must be prior to the end date");
        }
        long minutes = ChronoUnit.MINUTES.between(startDate, endDate);
        BigDecimal pricePerMinute = this.pricePerHour.divide(new BigDecimal("60"), 6, java.math.RoundingMode.HALF_UP);
        BigDecimal totalPrice = pricePerMinute.multiply(new BigDecimal(minutes));
        BigDecimal result = totalPrice.add(fixedAmount).setScale(2, java.math.RoundingMode.HALF_UP);
        return result;
    }


}
