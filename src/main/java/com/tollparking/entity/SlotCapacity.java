package com.tollparking.entity;

public class SlotCapacity {

    /**
     * The maximum capacity of the slot
     */
    public int capacity;

    /**
     * The current capacity of the slot
     */
    public int currentCapacity;

    /**
     * Creates a slot object of the specified capacity
     * @param capacity the maximum capacity
     */
    public SlotCapacity(int capacity) {
        this.capacity = capacity;
        this.currentCapacity = 0;
    }

    /**
     * Set the current capacity fot the specified slot
     * @param currentCapacity the current capacity (number of vehicles that are parked)
     */
    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
}
