package fleet.vehicles;

import fleet.exceptions.*;
import fleet.interfaces.*;

public class Bus extends LandVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable {
    private double fuelLevel;
    private final int passengerCapacity = 50;
    private int currentPassengers;
    private final double cargoCapacity = 500.0;
    private double currentCargo;
    private boolean maintenanceNeeded;

    public Bus(String id, String model, double maxSpeed, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, numWheels);
        this.fuelLevel = 0;
        this.currentPassengers = 0;
        this.currentCargo = 0;
        this.maintenanceNeeded = false;
    }
    // superclass
    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) throw new InvalidOperationException("Distance must be positive.");
        consumeFuel(distance);
        updateMileage(distance);
        System.out.printf("Bus %s is transporting passengers and cargo for %.1f km.%n", getId(), distance);
    }

    @Override
    public double calculateFuelEfficiency() {
        return 10.0;
    }

    @Override
    public String toCsvString() {
        return String.join(",", "Bus", getId(), getModel(), String.valueOf(getMaxSpeed()), String.valueOf(getNumWheels()), String.valueOf(getCurrentMileage()));
    }

    // interface

    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (amount <= 0) {
            throw new InvalidOperationException("Amount must be positive.");
        }
        this.fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() {
        return fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double fuelNeeded = distance / calculateFuelEfficiency();
        if (fuelLevel < fuelNeeded) {
            throw new InsufficientFuelException("Not enough fuel for bus " + getId());
        }
        fuelLevel -= fuelNeeded;
        return fuelNeeded;
    }

    @Override
    public void boardPassengers(int count) throws OverloadException {
        if (currentPassengers + count > passengerCapacity) {
            throw new OverloadException("Passenger capacity exceeded.");
        }
        currentPassengers += count;
    }

    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count > currentPassengers) {
            throw new InvalidOperationException("Cannot disembark more passengers than available.");
        }
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
    public void loadCargo(double weight) throws OverloadException {
        if (currentCargo + weight > cargoCapacity) {
            throw new OverloadException("Cargo capacity exceeded.");
        }
        currentCargo += weight;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight > currentCargo) {
            throw new InvalidOperationException("Cannot unload more cargo than available.");
        }
        currentCargo -= weight;
    }

    @Override
    public double getCargoCapacity() {
        return cargoCapacity;
    }

    @Override
    public double getCurrentCargo(){
        return currentCargo;
    }

    @Override
    public void scheduleMaintenance() {
        this.maintenanceNeeded = true;
    }

    @Override
    public boolean needsMaintenance(){
        return getCurrentMileage() > 10000 || maintenanceNeeded;
    }

    @Override
    public void performMaintenance() {
        this.maintenanceNeeded = false;
        System.out.println("Maintenance performed on Bus " + getId());
    }
}