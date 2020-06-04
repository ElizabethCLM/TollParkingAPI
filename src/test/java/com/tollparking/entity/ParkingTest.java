package com.tollparking.entity;

import com.tollparking.enums.VehicleType;
import com.tollparking.exception.TicketNotFoundException;
import com.tollparking.exception.InvalidCapacityException;
import com.tollparking.billing.PricingPerHourPolicy;
import com.tollparking.billing.PricingPerHourWithFixedAmountPolicy;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * ParkingTest tests the toll parking API
 * @link Parking.class
 */
public class ParkingTest {

    private static Logger LOG = LoggerFactory.getLogger(ParkingTest.class);

    /**
     * Tests the creation of a parking with a negative number of places - should throw InvalidCapacityException
     */
    @Test
    public void addParkingWithNegativeNumberOfSlots() {
        assertThrows(InvalidCapacityException.class, () -> new Parking(-10, 20, 30));
        assertThrows(InvalidCapacityException.class, () -> new Parking(10, -30, 30));
        assertThrows(InvalidCapacityException.class, () -> new Parking(10, 30, -30));
    }

    /**
     * Tests the creation of a parking with a pricing policy associated
     * @throws InvalidCapacityException if the capacity is negative
     */
    @Test
    public void addParkingWithPricingPolicy() throws InvalidCapacityException {
        Parking parking = new Parking(1, 1,1, new PricingPerHourPolicy(BigDecimal.ZERO));
        assertNotNull(parking);
    }

    /**
     * Test that each vehicle can be marked on its corresponding parking slot
     * @throws InvalidCapacityException if the capacity is negative
     */
    @Test
    public void parkVehicleEachOnItsParkingSlot() throws InvalidCapacityException {
        Parking p = new Parking(1, 1,1);
        assertNotNull(p.parkVehicle(VehicleType.FUEL));
        assertNotNull(p.parkVehicle(VehicleType.ELECTRIC_20KW));
        assertNotNull(p.parkVehicle(VehicleType.ELECTRIC_50KW));
    }

    /**
     * Tests that only the allowed number of vehicles can park in a certain slot
     * @throws InvalidCapacityException if the capacity is negative
     */
    @Test
    public void park2StandardVehicles_1StandardParkingPlace() throws InvalidCapacityException {
        Parking parking = new Parking(1, 10,10);
        assertNotNull(parking.parkVehicle(VehicleType.FUEL));
        assertNull(parking.parkVehicle(VehicleType.FUEL));
    }

    /**
     * Test that although there are spaces available for other types, the car cannot park
     * @throws InvalidCapacityException if the capacity is negative
     */
    @Test
    public void parkElectricVehicle_1StandardParkingPlace() throws InvalidCapacityException {
        Parking p1 = new Parking(1, 0,0);
        assertNull(p1.parkVehicle(VehicleType.ELECTRIC_20KW));
    }

    /**
     * Test that although there are spaces available for other types, the car cannot park
     * @throws InvalidCapacityException if the capacity is negative
     */
    @Test
    public void parkElectricVehicle_1ParkingPlaceButWrongCapacity() throws InvalidCapacityException {
        Parking parking = new Parking(0, 0,1);
        assertNull(parking.parkVehicle(VehicleType.ELECTRIC_20KW));
    }

    /**
     * Test the parking of the vehicle and the creation of a ticket
     * @throws InvalidCapacityException if the capacity is negative
     */
    @Test
    public void parkVehicleAndCreateTicket() throws InvalidCapacityException {
        Parking parking = new Parking(0, 10,10);
        Ticket ticket = parking.parkVehicle(VehicleType.ELECTRIC_20KW);
        assertNotNull(ticket);
    }

    /**
     * Test the parking and the removal of a vehicle from the parking
     * @throws InvalidCapacityException if the capacity is negative
     * @throws TicketNotFoundException if the ticket cannot be found
     */
    @Test
    public void parkAndRemoveVehicle() throws InvalidCapacityException, TicketNotFoundException{
        Parking parking = new Parking(0, 1,1);
        Ticket ticket = parking.parkVehicle(VehicleType.ELECTRIC_20KW);
        assertNotNull(ticket);

        Ticket billedTicket = parking.removeVehicle(ticket);
        assertNotNull(billedTicket.getEndDate());
        assertNotNull(billedTicket.getAmount());
    }

    /**
     * Test that a ticket will not be issues if the maximum capacity is reached for a certain vehicle type
     * @throws InvalidCapacityException if the capacity is negative
     * @throws TicketNotFoundException if the ticket cannot be found
     */
    @Test
    public void parkingCapacity() throws InvalidCapacityException, TicketNotFoundException{
        Parking parking = new Parking(1, 0,0);
        Ticket ticket = parking.parkVehicle(VehicleType.FUEL);
        parking.removeVehicle(ticket);
        assertNotNull(parking.parkVehicle(VehicleType.FUEL));
        assertNull(parking.parkVehicle(VehicleType.FUEL));
    }

    /**
     * Test that we cannot to remove the vehicle from a parking twice
     * @throws InvalidCapacityException if the capacity is negative
     * @throws TicketNotFoundException if the ticket cannot be found
     */
    @Test
    public void removeVehicleFromParkingTwice() throws InvalidCapacityException, TicketNotFoundException{
        Parking parking = new Parking(1, 0,0);
        Ticket ticket = parking.parkVehicle(VehicleType.FUEL);
        assertNotNull(parking.removeVehicle(ticket));
        assertThrows(TicketNotFoundException.class, () -> parking.removeVehicle(ticket));
    }

    /**
     * Test that if we try to remove a null ticket, we throw a TicketNotFoundException
     * @throws InvalidCapacityException if the capacity is negative
     */
    @Test
    public void removeVehicleWithNullTicket() throws InvalidCapacityException {
        Parking parking = new Parking(0, 1,1);
        Ticket nullTicket = null;
        assertThrows(TicketNotFoundException.class, () -> parking.removeVehicle(nullTicket));
    }

    /**
     *  Test that if we try to remove a non-existent ticket, we throw a TicketNotFoundException
     * @throws InvalidCapacityException if the capacity is negative
     */
    @Test
    public void removeVehicleWithNonExistingTicket() throws InvalidCapacityException {
        Parking parking = new Parking(0, 1,1);
        Ticket ticket = new Ticket("NON_EXISTENT", VehicleType.ELECTRIC_20KW);
        assertThrows(TicketNotFoundException.class, () -> parking.removeVehicle(ticket));
    }

    /**
     * Test the billing with no policy set - should apply the default policy (free parking)
     * @throws TicketNotFoundException if the capacity is negative
     * @throws InvalidCapacityException if the ticket cannot be found
     */
    @Test
    public void billCustomer_noPricingPolicySet() throws TicketNotFoundException, InvalidCapacityException {
        Parking parking = new Parking(1, 0,0);
        Ticket ticket = parking.parkVehicle(VehicleType.FUEL);
        Instant date = Instant.now();
        ticket.setStartDate(date.minus(20, ChronoUnit.MINUTES));
        ticket.setEndDate(date);
        ticket = parking.removeVehicle(ticket);
        assertEquals(new BigDecimal("0.00"), ticket.getAmount());
    }

    /**
     * Test the billing with PricingPerHour policy set
     * @throws TicketNotFoundException if the capacity is negative
     * @throws InvalidCapacityException if the ticket cannot be found
     */
    @Test
    public void billCustomer_pricingPerHour_30minutes() throws TicketNotFoundException, InvalidCapacityException {
        Parking parking = new Parking(1, 0,0, new PricingPerHourPolicy(BigDecimal.ONE));
        Ticket ticket = parking.parkVehicle(VehicleType.FUEL);
        Instant date = Instant.now();
        ticket.setStartDate(date.minus(20, ChronoUnit.MINUTES));
        ticket.setEndDate(date);
        ticket = parking.removeVehicle(ticket);
        assertEquals(new BigDecimal("0.33"), ticket.getAmount());
    }

    /**
     * Test the billing with PricingPerHourWithFixedAmountPolicy policy set
     * @throws InvalidCapacityException if the capacity is negative
     * @throws TicketNotFoundException if the ticket cannot be found
     */
    @Test
    public void billCustomer_pricingPerHourWithFixedAmount_1H15minutes() throws InvalidCapacityException, TicketNotFoundException {
        Parking parking = new Parking(1, 0,0, new PricingPerHourWithFixedAmountPolicy(new BigDecimal("5"), BigDecimal.TEN));
        Ticket ticket = parking.parkVehicle(VehicleType.FUEL);
        Instant date = Instant.now();
        ticket.setStartDate(date.minus(75, ChronoUnit.MINUTES));
        ticket.setEndDate(date);
        ticket = parking.removeVehicle(ticket);
        assertEquals(new BigDecimal("16.25"), ticket.getAmount());
    }
}