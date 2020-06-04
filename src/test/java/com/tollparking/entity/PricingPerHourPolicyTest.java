package com.tollparking.entity;

import com.tollparking.billing.PricingPerHourPolicy;
import com.tollparking.exception.InvalidAmountException;
import com.tollparking.exception.InvalidDateException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**\
 * Tests for PricingPerHour policy
 */
public class PricingPerHourPolicyTest {

    /**
     * Test the billing of a customer when one date is null - should throw InvalidDateException
     */
    @Test
    public void billCustomer_NullDate()  {
        PricingPerHourPolicy pricing = new PricingPerHourPolicy(new BigDecimal("10"));
        Instant startDate = Instant.now();
        assertThrows(InvalidDateException.class, () -> pricing.calculatePrice(startDate, null));
        assertThrows(InvalidDateException.class, () -> pricing.calculatePrice(null, startDate));
    }

    /**
     * Test the billing when the end date is prior to the start date - should throw InvalidDateException
     */
    @Test
    public void billCustomer_endDateBeforeStartDate()  {
        PricingPerHourPolicy pricing = new PricingPerHourPolicy(new BigDecimal("20"));
        Instant startDate = Instant.now();
        Instant endDate = startDate.minus(2, ChronoUnit.DAYS);
        assertThrows(InvalidDateException.class, () -> pricing.calculatePrice(startDate, endDate));
    }

    /**
     * Test the billing when the price is negative - should throw InvalidAmountException
     */
    @Test
    public void billCustomer_NegativePrice()  {
        assertThrows(InvalidAmountException.class, () -> new PricingPerHourPolicy(new BigDecimal("-10")));
    }

    /**
     * Test the billing for 1 day and 2 minutes
     */
    @Test
    public void billCustomer_1day2minutes()  {
        PricingPerHourPolicy pricing = new PricingPerHourPolicy(new BigDecimal("10"));
        Instant startDate = Instant.now();
        Instant endDate = startDate.plus(1, ChronoUnit.DAYS).plus(2, ChronoUnit.MINUTES);
        assertEquals(new java.math.BigDecimal("240.33"), pricing.calculatePrice(startDate, endDate));
    }

    /**
     * Test the billing for 1H
     */
    @Test
    public void billCustomer_1H()  {
        PricingPerHourPolicy pricing = new PricingPerHourPolicy(new BigDecimal("10"));
        Instant startDate = Instant.now();
        Instant endDate = startDate.plus(1, ChronoUnit.HOURS);
        assertEquals(new java.math.BigDecimal("10.00"), pricing.calculatePrice(startDate, endDate));
    }
}
