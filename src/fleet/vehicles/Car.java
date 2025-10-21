package fleet.vehicles;

import fleet.exceptions.*;
import fleet.interfaces.*;

public class Car extends LandVehicle implements FuelConsumable, PassengerCarrier, Maintainable {
    private double fuelLevel;
    private final int passengerCapacity = 5;
    private int currentPassengers;
    private boolean maintenanceNeeded;

    public Car(String id, String model, double maxSpeed) throws InvalidOperationException {
        super(id, model, maxSpeed,4);
        this.fuelLevel = 0;
        this.currentPassengers = 0;
        this.maintenanceNeeded = false;
    }

    //Override methods from superclass Vehicle  
    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0){
            throw new InvalidOperationException("Distance cannot be negative.");
        }
        consumeFuel(distance);
        updateMileage(distance);
        System.out.printf("Car %s is driving on the road for %.1f km.\n", getId(), distance);
    }

    @Override
    public double calculateFuelEfficiency(){
        return 15.0;
    }

    @Override
    public String toCsvString() {
        return String.join(",", "Car", getId(), getModel(), String.valueOf(getMaxSpeed()),String.valueOf(4), String.valueOf(getCurrentMileage()));
    }

    // Interface implementations
    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (amount <= 0) throw new InvalidOperationException("Refuel amount must be positive.");
        this.fuelLevel += amount;
    }
    @Override
    public double getFuelLevel(){
        return fuelLevel; 
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double fuelNeeded = distance / calculateFuelEfficiency();
        if (fuelLevel < fuelNeeded) throw new InsufficientFuelException("Not enough fuel for the journey.");
        fuelLevel -= fuelNeeded;
        return fuelNeeded;
    }

    @Override
    public void boardPassengers(int count) throws OverloadException {
        if (currentPassengers + count > passengerCapacity) throw new OverloadException("Passenger capacity exceeded.");
        currentPassengers += count;
    }
    
    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count > currentPassengers) throw new InvalidOperationException("Cannot disembark more passengers than are on board.");
        currentPassengers -= count;
    }
    @Override
    public int getPassengerCapacity(){
        return passengerCapacity;
    }
    @Override
    public int getCurrentPassengers(){
        return currentPassengers;
    }
    @Override
    public void scheduleMaintenance(){
        this.maintenanceNeeded = true;
    }
    @Override
    public boolean needsMaintenance(){
        return getCurrentMileage() > 10000 || maintenanceNeeded;
        }
    @Override
    public void performMaintenance(){
        this.maintenanceNeeded = false;
        System.out.println("Maintenance performed on Car " + getId());
    }
}