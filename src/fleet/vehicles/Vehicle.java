package fleet.vehicles;

import fleet.exceptions.InvalidOperationException;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    public Vehicle(String id, String model, double maxSpeed) throws InvalidOperationException {
        if (id.trim().isEmpty()) {
            throw new InvalidOperationException("Vehicle ID cannot be empty.");
        }
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.currentMileage = 0.0;
    }

    // abstract Methods
    public abstract void move(double distance) throws InvalidOperationException, Exception;
    public abstract double calculateFuelEfficiency();
    public abstract double estimateJourneyTime(double distance);
    public abstract String toCsvString();

    //concrete Methods
    public void displayInfo() {
        System.out.printf("ID: %s\tModel: %s\tMax Speed: %.2f km/h\tMileage: %.2f km%n", id, model, maxSpeed, currentMileage);
    }

    //getters
    protected void updateMileage(double distance) {
        if (distance > 0) {
            this.currentMileage += distance;
        }
    }
    public String getId(){
        return id;
    }
    public String getModel(){
        return model;
    }
    public double getMaxSpeed(){
        return maxSpeed;
        }
    public double getCurrentMileage(){
        return currentMileage;
        }
    
    // For sorting
    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(other.calculateFuelEfficiency(), this.calculateFuelEfficiency());
    }
}