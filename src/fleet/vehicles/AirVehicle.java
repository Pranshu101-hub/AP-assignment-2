package fleet.vehicles;
import fleet.exceptions.InvalidOperationException;

public abstract class AirVehicle extends Vehicle{
    private double maxAltitude;

    public AirVehicle(String id, String model, double maxSpeed, double maxAltitude) throws InvalidOperationException {
        super(id, model, maxSpeed);
        this.maxAltitude = maxAltitude;
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
        return baseTime * 0.95; // Reduce 5% for direct paths
    }
    
    public double getMaxAltitude(){
        return maxAltitude;
    
    }
}