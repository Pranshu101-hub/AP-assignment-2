package fleet;

import fleet.exceptions.*;
import fleet.interfaces.*;
import fleet.vehicles.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FleetManager {
    private List<Vehicle> fleet = new ArrayList<>();
    public void addVehicle(Vehicle v) throws InvalidOperationException {
        for (Vehicle i : fleet) {
            if (i.getId().equals(v.getId())) {
                throw new InvalidOperationException("Vehicle ID " + v.getId() + " already exists.");
            }
        }
        fleet.add(v);
    }

    public void removeVehicle(String id) throws InvalidOperationException {
        boolean flag = false;
        for (Vehicle i : fleet){
            if (i.getId().equals(id)){
                flag = true;
                System.out.println("Vehicle ID" + id + " removed.");
                fleet.remove(i);
                break;
            }
        }
        if (flag == false) throw new InvalidOperationException("Vecicle ID not found.");
    }

    public void startAllJourneys(double distance) {
        System.out.printf("\nstarting all journeys for %.1f km...\n", distance);
        for (Vehicle v : fleet) {
            try {
                v.move(distance);
            } 
            catch (Exception e){
                System.err.println("Could not start journey for " + v.getId() + ": " + e.getMessage());
            }
        }
    }

    public void maintainAll() {
        System.out.println("\nperforming maintenance...");
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable && ((Maintainable) v).needsMaintenance()) {
                ((Maintainable) v).performMaintenance();
            }
        }
    }
    public void searchByType(){
        int fc = 0;
        int m = 0;
        int cc = 0;
        int pa = 0;
        for (Vehicle v:fleet){
            if (v instanceof FuelConsumable) fc = fc + 1;
            if (v instanceof Maintainable) m=m+1;
            if (v instanceof CargoCarrier) cc= cc+1;
            if (v instanceof PassengerCarrier) pa=pa+1;
            }
        System.out.printf("FuelConsumables = %d\nMaintainable = %d\nCargoCarrier = %d\nPassengerCarrier = %d\n", fc,m,cc,pa);
    }
    public void refuelAll(double amount) throws InvalidOperationException {
        System.out.printf("\nrefueling all compatible vehicles with %.1f liters...\n", amount);
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable) {
                try {
                    ((FuelConsumable) v).refuel(amount);
                    System.out.println("Refueled vehicle " + v.getId());
                } 
                catch (InvalidOperationException e) {
                    System.err.println("Could not refuel vehicle " + v.getId() + ": " + e.getMessage());
                }
            }
        }
    }
    
    public List<Vehicle> vehiclesMaintenance() {
        return fleet.stream()
        .filter(v -> v instanceof Maintainable && ((Maintainable) v).needsMaintenance())
        .collect(Collectors.toList());
    }
    
    public void generateReport() throws InvalidOperationException{
        if (fleet.isEmpty()) throw new InvalidOperationException("fleet is empty.");
        
        System.out.println("\nFleet Status Report");
        System.out.printf("Total vehicles: %d\n",fleet.size());
        Collections.sort(fleet);
        for (Vehicle v : fleet) {
            System.out.printf("ID: %-5s , Type: %-10s , Model: %-15s , Mileage: %-8.1f km , Efficiency: %-4.1f km/l\n", v.getId(), v.getClass().getSimpleName(), v.getModel(), v.getCurrentMileage(), v.calculateFuelEfficiency());
        }
    }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Vehicle v : fleet) {
                writer.println(v.toCsvString());
            }
            System.out.println("\nFleet data successfully saved to " + filename);
        }
    }

    public void loadFromFile(String filename) throws IOException, InvalidOperationException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            fleet.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Vehicle v = createVehicleFromData(data);
                if (v != null) fleet.add(v);
            }
            System.out.println("\nFleet data successfully loaded from " + filename);
        }
    }

    private Vehicle createVehicleFromData(String[] data) throws InvalidOperationException {
        String type = data[0];
        String id = data[1];
        String model = data[2];
        double maxSpeed = Double.parseDouble(data[3]);
        
        Vehicle vehicle;
        switch (type) {
            case "Car":
                vehicle = new Car(id, model, maxSpeed);
                break;
            case "Truck":
                vehicle = new Truck(id, model, maxSpeed, Integer.parseInt(data[4]));
                break;
            case "Bus":
                vehicle = new Bus(id, model, maxSpeed, Integer.parseInt(data[4]));
                break;
            case "Airplane":
                vehicle = new Airplane(id, model, maxSpeed, Double.parseDouble(data[4]));
                break;
            case "CargoShip":
                vehicle = new CargoShip(id, model, maxSpeed, Boolean.parseBoolean(data[4]));
                break;
            default:
                throw new InvalidOperationException("Unknown vehicle type in file: " + type);
        }
        
        return vehicle;
    }
}