package fleet.interfaces;
import fleet.exceptions.InsufficientFuelException;
import fleet.exceptions.InvalidOperationException;

public interface FuelConsumable {
    void refuel(double amount) throws InvalidOperationException;
    double getFuelLevel();
    double consumeFuel(double distance) throws InsufficientFuelException;
}