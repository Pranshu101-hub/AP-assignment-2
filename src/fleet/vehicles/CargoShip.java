package fleet.vehicles;

import fleet.exceptions.*;
import fleet.interfaces.*;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable {
    private double fuelLevel;
    private final double cargoCapacity = 50000.0;
    private double currentCargo;
    private boolean maintenanceNeeded;

    public CargoShip(String id, String model, double maxSpeed, boolean hasSail) throws InvalidOperationException {
        super(id, model, maxSpeed, hasSail);
        this.fuelLevel = 0.0;
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
    }

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) throw new InvalidOperationException("Distance must be positive.");
        if (hasSail() == false) { // consume fuel if no sail
            consumeFuel(distance);
        }
        updateMileage(distance);
        System.out.printf("Cargo Ship %s is sailing with cargo for %.1f km.%n", getId(), distance);
    }
    // superclass
    @Override
    public double calculateFuelEfficiency() {
        if(hasSail()){
            return 0;
        }
        return 10.0;
    }

    @Override
    public String toCsvString() {
        return String.join(",", "CargoShip", getId(), getModel(), String.valueOf(getMaxSpeed()), String.valueOf(hasSail()), String.valueOf(getCurrentMileage()));
    }

    // interfaces
    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (hasSail()) {
            throw new InvalidOperationException("Sailing ship cannot be refueled.");
        }
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
        if (hasSail()) {
            return 0;
        }
        double fuelNeeded = distance / calculateFuelEfficiency();
        if (fuelLevel < fuelNeeded) {
            throw new InsufficientFuelException("Not enough fuel for ship " + getId());
        }
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
        System.out.println("Maintenance performed on Cargo Ship " + getId());
    }
}