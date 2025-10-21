package fleet.vehicles;
import fleet.exceptions.InvalidOperationException;

public abstract class WaterVehicle extends Vehicle {
    private boolean hasSail;

    public WaterVehicle(String id, String model, double maxSpeed, boolean hasSail) throws InvalidOperationException {
        super(id, model, maxSpeed);
        this.hasSail = hasSail;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        double maxSpeed = super.getMaxSpeed();
        double baseTime; 
        if(maxSpeed>0){
            baseTime = distance / maxSpeed;
        }
        else{
            baseTime = Double.POSITIVE_INFINITY;
        }
        return baseTime * 1.15; // Add 15% for currents
    }

    public boolean hasSail(){
        return hasSail;
    }
}