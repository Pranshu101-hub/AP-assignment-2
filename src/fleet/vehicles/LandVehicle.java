package fleet.vehicles;
import fleet.exceptions.InvalidOperationException;

public abstract class LandVehicle extends Vehicle {
    private int numWheels;
    
    public LandVehicle(String id, String model, double maxSpeed, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed);
        this.numWheels = numWheels;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        double maxSpeed = super.getMaxSpeed();
        double baseTime;
        if (maxSpeed > 0) {
            baseTime = distance / maxSpeed;
        } 
        else {
            baseTime = Double.POSITIVE_INFINITY;
        }
        return baseTime * 1.1; // Add 10% for traffic
    }

    public int getNumWheels(){
        return numWheels; 
    }
}