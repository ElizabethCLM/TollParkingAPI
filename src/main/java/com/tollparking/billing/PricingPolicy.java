package com.tollparking.billing;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * A pricing policy is a specific policy associated to a parking toll
 */
public interface PricingPolicy {

    /**
     * The method calculates the price between two Instant dates
     * @param startDate the start date
     * @param endDate the end date
     * @return a BigDecimal value with the amount to pay calculated between the start date and end date
     */
    BigDecimal calculatePrice(Instant startDate, Instant endDate);
}
