package com.tollparking.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import com.tollparking.billing.PricingPerHourWithFixedAmountPolicy;
import com.tollparking.exception.InvalidAmountException;
import com.tollparking.exception.InvalidDateException;

/**
 * Tests the PricingPerHourWithFixedAmountPolicy
 * @link com.parkingtoll.billing.PricingPerHourWithFixedAmountPolicy
 */
public class PricingPerHourWithFixedAmountPolicyTest {

    /**
     * Test the billing when one of the dates is null - should throw InvalidDateException
     */
    @Test
    public void billCustomer_NullDate()  {
        PricingPerHourWithFixedAmountPolicy pricing = new PricingPerHourWithFixedAmountPolicy(new BigDecimal("10"), new BigDecimal("5"));
        Instant startDate = Instant.now();
        assertThrows(InvalidDateException.class, () -> pricing.calculatePrice(startDate, null));
        assertThrows(InvalidDateException.class, () -> pricing.calculatePrice(null, startDate));
    }

    /**
     * Test the billing when the end date is after the start date - should throw InvalidDateException
     */
    @Test
    public void billCustomer_endDateBeforeStartDate()  {
        PricingPerHourWithFixedAmountPolicy pricing = new PricingPerHourWithFixedAmountPolicy(new BigDecimal("10"), new BigDecimal("5"));
        Instant startDate = Instant.now();
        Instant endDate = startDate.minus(1, ChronoUnit.DAYS).plus(2, ChronoUnit.MINUTES);
        assertThrows(InvalidDateException.class, () -> pricing.calculatePrice(startDate, endDate));
    }

    /**
     * Test the billing when the price set is negative - should throw InvalidAmountException
     */
    @Test
    public void billCustomer_NegativePrice()  {
        assertThrows(InvalidAmountException.class, () -> new PricingPerHourWithFixedAmountPolicy(new BigDecimal("-10"), new BigDecimal("10")));
    }

    /**
     * Test billing for 9 minutes
     */
    @Test
    public void billCustomer_9minutes()  {
        PricingPerHourWithFixedAmountPolicy pricing = new PricingPerHourWithFixedAmountPolicy(new BigDecimal("16.23"), new BigDecimal("9.99"));
        Instant startDate = Instant.now();
        Instant endDate = startDate.plus(9, ChronoUnit.MINUTES);
        assertEquals(new java.math.BigDecimal("12.42"), pricing.calculatePrice(startDate, endDate));
    }

    /**
     * Test billing for 7 minutes
     */
    @Test
    public void billCustomer_7minutes()  {
        PricingPerHourWithFixedAmountPolicy pricing = new PricingPerHourWithFixedAmountPolicy(new BigDecimal("13"), new BigDecimal("0.00"));
        Instant startDate = Instant.now();
        Instant endDate = startDate.plus(7, ChronoUnit.MINUTES);
        assertEquals(new java.math.BigDecimal("1.52"), pricing.calculatePrice(startDate, endDate));
    }

    /**
     * Test billing for 1 minute
     */
    @Test
    public void billCustomer_1minute()  {
        PricingPerHourWithFixedAmountPolicy pricing = new PricingPerHourWithFixedAmountPolicy(new BigDecimal("1"), new BigDecimal("10"));
        Instant startDate = Instant.now();
        Instant endDate = startDate.plus(1, ChronoUnit.MINUTES);
        assertEquals(new java.math.BigDecimal("10.02"), pricing.calculatePrice(startDate, endDate));
    }

    /**
     * Test billing for 1 day and 2 minutes
     */
    @Test
    public void billCustomer_1day2minutes()  {
        PricingPerHourWithFixedAmountPolicy pricing = new PricingPerHourWithFixedAmountPolicy(new BigDecimal("10"), new BigDecimal("5"));
        Instant startDate = Instant.now();
        Instant endDate = startDate.plus(1, ChronoUnit.DAYS).plus(2, ChronoUnit.MINUTES);
        assertEquals(new java.math.BigDecimal("245.33"), pricing.calculatePrice(startDate, endDate));
    }

}
