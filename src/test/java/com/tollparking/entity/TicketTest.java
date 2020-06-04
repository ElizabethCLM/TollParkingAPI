package com.tollparking.entity;

import com.tollparking.enums.VehicleType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketTest {

    /**
     * Tests the equality of two tickets
     */
    @Test
    public void ticketsEqualityTest() {
        Ticket t1 = new Ticket("id1", VehicleType.ELECTRIC_20KW);
        Ticket t2 = new Ticket("id1", VehicleType.ELECTRIC_20KW);
        Instant startDate = Instant.now();
        Instant endDate = Instant.now().plus(10, ChronoUnit.MINUTES);
        t1.setStartDate(startDate);
        t1.setEndDate(endDate);
        t1.setAmount(new BigDecimal("10.00"));
        t2.setStartDate(startDate);
        t2.setEndDate(endDate);
        t2.setAmount(new BigDecimal("10.00"));
        assertTrue(t1.equals(t2) && (t2).equals(t1));
    }

    /**
     * Tests the equality of two tickets when one of them is null
     */
    @Test
    public void ticketsEqualityTest_OneTicketNull() {
        Ticket t1 = new Ticket("id1", VehicleType.ELECTRIC_20KW);
        Ticket t2 = null;
        Instant startDate = Instant.now();
        Instant endDate = Instant.now().plus(10, ChronoUnit.MINUTES);
        t1.setStartDate(startDate);
        t1.setEndDate(endDate);
        t1.setAmount(new BigDecimal("10.00"));
        assertFalse(t1.equals(t2) && (t2).equals(t1));
    }

    /**
     * Tests the hashcode of two tickets with the same id and the same vehicle type
     */
    @Test
    public void ticketsHashCodeTest_sameIdSameVehicleType() {
        Ticket t1 = new Ticket("id1", VehicleType.ELECTRIC_50KW);
        Ticket t2 = new Ticket("id1", VehicleType.ELECTRIC_50KW);
        Instant startDate = Instant.now();
        Instant endDate = Instant.now().plus(10, ChronoUnit.MINUTES);
        t1.setStartDate(startDate);
        t1.setEndDate(endDate);
        t1.setAmount(new BigDecimal("10.00"));
        t2.setStartDate(startDate);
        t2.setEndDate(endDate);
        t2.setAmount(new BigDecimal("10.00"));
        assertTrue(t1.hashCode() == t2.hashCode());
    }

    /**
     * Test the hash code of two tickets with the same id and different types
     */
    @Test
    public void testHashCode_sameIdDifferentVehicleTypes() {
        Ticket t1 = new Ticket("id1", VehicleType.ELECTRIC_50KW);
        Ticket t2 = new Ticket("id1", VehicleType.ELECTRIC_20KW);
        Instant startDate = Instant.now();
        Instant endDate = Instant.now().plus(10, ChronoUnit.MINUTES);
        t1.setStartDate(startDate);
        t1.setEndDate(endDate);
        t1.setAmount(new BigDecimal("10.00"));
        t2.setStartDate(startDate);
        t2.setEndDate(endDate);
        t2.setAmount(new BigDecimal("10.00"));
        assertFalse(t1.hashCode() == (t2.hashCode()));
    }

    /**
     * Test the toString method of the Ticket class
     */
    @Test
    public void tickets_toStringTest() {
        Ticket t = new Ticket("id1", VehicleType.ELECTRIC_50KW);
        Instant startDate = Instant.parse("2020-10-03T10:12:35Z");
        Instant endDate = Instant.parse("2020-10-03T12:30:35Z");
        t.setStartDate(startDate);
        t.setEndDate(endDate);
        t.setAmount(new BigDecimal("10.00"));
        assertEquals("id: id1 startDate: 2020-10-03T10:12:35Z endDate: 2020-10-03T12:30:35Z amount: 10.00", t.toString());
    }
}
