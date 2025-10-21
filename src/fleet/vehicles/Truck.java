package fleet.vehicles;

import fleet.exceptions.*;
import fleet.interfaces.*;

public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable {
    private double fuelLevel;
    private final double cargoCapacity = 5000.0;
    private double currentCargo;
    private boolean maintenanceNeeded;

    public Truck(String id, String model, double maxSpeed, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, numWheels);
        this.fuelLevel = 0;
        this.currentCargo = 0;
        this.maintenanceNeeded = false;
    }
    // from superclass
    @Override
    public double calculateFuelEfficiency() {
        double baseEfficiency = 8.0;
        if (currentCargo > cargoCapacity * 0.5) {
            return baseEfficiency * 0.9; // 10% efficiency reduction
        }
        return baseEfficiency;
    }
    
    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) throw new InvalidOperationException("Distance cannot be negative.");
        consumeFuel(distance);
        updateMileage(distance);
        System.out.printf("Truck %s is hauling cargo for %.1f km.\n", getId(), distance);
    }
    
    @Override
    public String toCsvString() {
        return String.join(",", "Truck", getId(), getModel(), String.valueOf(getMaxSpeed()), String.valueOf(getNumWheels()), String.valueOf(getCurrentMileage()));
    }
    // interface
    @Override
    public void refuel(double amount) throws InvalidOperationException{
        if(amount <= 0){
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
        if (fuelLevel < fuelNeeded) throw new InsufficientFuelException("Not enough fuel for truck " + getId());
        fuelLevel -= fuelNeeded;
        return fuelNeeded;
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
    public double getCurrentCargo() {
        return currentCargo;
    }

    @Override
    public void scheduleMaintenance() {
        this.maintenanceNeeded = true;
    }

    @Override
    public boolean needsMaintenance() {
        return getCurrentMileage() > 10000 || maintenanceNeeded;
    }

    @Override
    public void performMaintenance() {
        this.maintenanceNeeded = false;
        System.out.println("Maintenance performed on Truck " + getId());
    }
}