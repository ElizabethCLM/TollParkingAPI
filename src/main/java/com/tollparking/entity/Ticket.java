package com.tollparking.entity;

import java.math.BigDecimal;
import java.time.Instant;
import com.tollparking.enums.VehicleType;
import java.util.Objects;

/**
 * Test the Ticket class
 */
public class Ticket {

    /**
     * Id of the ticket
     */
    private String id;

    /**
     * The date when the ticket was issued (when the car entered the parking)
     */
    private Instant startDate;

    /**
     * The date when the car left the parking
     */
    private Instant endDate;

    /**
     * The amount calculated for the ticket between the startDate and the endDate
     */
    private BigDecimal amount;

    /**
     * The vehicle type
     */
    VehicleType vehicleType;

    public Ticket(String id, VehicleType vehicleType) {
        this.id = id;
        this.startDate = Instant.now();
        this.vehicleType = vehicleType;
    }

    /**
     * Returns the type of the vehicle
     * @return the type of the vehicle for which the ticket has been issued
     */
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    /**
     * Sets the start date of the ticket
     * @return the date when the ticket was issues
     */
    public Instant getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the ticket
     * @return the end date of the stay associated to the ticket
     */
    public Instant getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the ticket to the current date
     */
    public void setEndDate() {
        this.endDate = Instant.now();
    }

    /**
     * Sets the end date of the ticket
     * @param date the date to be set as the end date of the ticket
     */
    public void setEndDate(Instant date) {
        this.endDate = date;
    }

    /**
     * Sets the start date of the ticket
     * @param startDate the startDate of the ticket (the moment when the ticket is issued - aka when the car enters the parking)
     */
    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the amount of the ticket
     * @return the amount of the ticket
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount
     * @param amount the calculated amount for the entire stay
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Indicates if the object obj is equal to the this object
     * @param obj the object to verify
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ticket)) return false;
        Ticket ticket = (Ticket) obj;
        return Objects.equals(this.id, ticket.id);
    }

    /**
     * Returns a hash code value for the Ticket object
     * @return the hashcode value for the Ticket object
     */
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 29 * hash + ((this.id == null)? 0: Objects.hashCode(this.id));
        hash = 29 * hash + ((this.vehicleType == null) ? 0 : Objects.hashCode(this.vehicleType));
        return hash;
    }

    /**
     * Returns the String representation of a Ticket
     * @return a string that "textually represents" this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("id: ")
                .append(this.id)
                .append(" startDate: ")
                .append(this.startDate)
                .append(" endDate: ")
                .append(this.endDate)
                .append(" amount: ")
                .append(this.amount);
        return sb.toString();
    }
}
