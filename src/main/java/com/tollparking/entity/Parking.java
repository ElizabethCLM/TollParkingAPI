package com.tollparking.entity;

import com.tollparking.enums.VehicleType;
import com.tollparking.exception.TicketNotFoundException;
import com.tollparking.exception.InvalidCapacityException;
import com.tollparking.billing.PricingPerHourPolicy;
import com.tollparking.billing.PricingPolicy;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Set;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

public class Parking {

    private static Logger LOG = LoggerFactory.getLogger(Parking.class);

    /**
     * The pricing policy associated to a parking
     */
    private PricingPolicy pricingPolicy;

    /**
     * A map holding the number of slots for each vehicle type: A Slot contains data about the maximum capacity and the current capacity
     */
    private Map<VehicleType, SlotCapacity> vehicleTypeCapacityMap = new HashMap<>();

    /**
     * A set of tickets isues for each car that enters the parking
     */
    private Set<Ticket> ticketSet = new java.util.HashSet<>();

    /**
     * Creates a new parking with a default pricing policy (PricingPerHourPolicy with an amount set to BigDecimal.ZERO)
     *
     * @param standardSlotCapacity number of the standard slots available
     * @param kw20SlotCapacity number of the 20Kw slots available
     * @param kw50SlotCapacity number of 50Kw slots available
     * @throws InvalidCapacityException if either of the capacities given are negative
     *
     */
    public Parking(int standardSlotCapacity, int kw20SlotCapacity, int kw50SlotCapacity) throws InvalidCapacityException {
        if ((standardSlotCapacity < 0) || (kw20SlotCapacity < 0) || (kw50SlotCapacity < 0)) {
            throw new InvalidCapacityException("Each number of slots must be positive");
        }
        this.vehicleTypeCapacityMap.put(VehicleType.FUEL, new SlotCapacity(standardSlotCapacity));
        this.vehicleTypeCapacityMap.put(VehicleType.ELECTRIC_20KW, new SlotCapacity(kw20SlotCapacity));
        this.vehicleTypeCapacityMap.put(VehicleType.ELECTRIC_50KW, new SlotCapacity(kw50SlotCapacity));

        this.pricingPolicy = new PricingPerHourPolicy(BigDecimal.ZERO);
    }

    /**
     * Creates a new parking with the following parameters
     *
     * @param standardSlotCapacity number of the standard slots available
     * @param kw20SlotCapacity number of the 20Kw slots available
     * @param kw50SlotCapacity number of 50Kw slots available
     * @param pricingPolicy the pricing policy associated with the parking.
     *
     * If the pricing policy is null, a default pricing policy will be given with a BigDecimal.ZERO amount per hour
     * @throws InvalidCapacityException if either of the slot capacities given are negative
     *
     */
    public Parking(int standardSlotCapacity, int kw20SlotCapacity, int kw50SlotCapacity, PricingPolicy pricingPolicy) throws InvalidCapacityException {
        this(standardSlotCapacity, kw20SlotCapacity, kw50SlotCapacity);
        if (pricingPolicy != null) {
            this.pricingPolicy = pricingPolicy;
        }
    }

    /**
     * Checks in a vehicle in the parking
     *
     * @param vehicleType the vehicle type to park
     * @return  null if there are no spaces available for the given vehicle type or a Ticket having a unique id and a startDate set to
     * the current date.
     * */
    public Ticket parkVehicle(VehicleType vehicleType) {
        if (vehicleType == null) return null;

        Integer maxCapacity = this.vehicleTypeCapacityMap.get(vehicleType).capacity;
        Integer currentCapacity = this.vehicleTypeCapacityMap.get(vehicleType).currentCapacity;
        if (maxCapacity == currentCapacity)
            return null;

        Ticket ticket = new Ticket(UUID.randomUUID().toString(), vehicleType);
        this.ticketSet.add(ticket);
        currentCapacity++;
        this.vehicleTypeCapacityMap.get(vehicleType).setCurrentCapacity(currentCapacity);
        return ticket;
    }

    /**
     * Removes a vehicle from the parking
     *
     * @param ticket received at parking time
     * @return the ticket with the amount to be paid calculated
     * @throws TicketNotFoundException if the ticket given is not found
     */
    public Ticket removeVehicle(Ticket ticket) throws TicketNotFoundException {
        if (ticketSet.isEmpty() || ticket == null || !ticketSet.contains(ticket)) {
            LOG.error(String.format("Error in removing vehicle with ticket: %s", ticket));
            throw new TicketNotFoundException(String.format("Ticket not found in the system"));
        }

        ticket.setEndDate();
        ticket.setAmount(this.pricingPolicy.calculatePrice(ticket.getStartDate(), ticket.getEndDate()));

        this.ticketSet.remove(ticket);
        Integer currentCapacity = this.vehicleTypeCapacityMap.get(ticket.getVehicleType()).currentCapacity;
        currentCapacity--;
        this.vehicleTypeCapacityMap.get(ticket.getVehicleType()).setCurrentCapacity(currentCapacity);
        return ticket;
    }
}
