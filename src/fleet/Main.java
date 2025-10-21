package fleet;

import fleet.exceptions.InvalidOperationException;
import fleet.vehicles.*;
import java.util.*;

public class Main {
    private static FleetManager manager = new FleetManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1: addVehicle(); break;
                    case 2: removeVehicle(); break;
                    case 3: startJourney(); break;
                    case 4: refuelAll(); break;
                    case 5: manager.maintainAll(); break;
                    case 6: manager.generateReport(); break;
                    case 7: manager.saveToFile("vehicles_data.csv"); break;
                    case 8: manager.loadFromFile("vehicles_data.csv"); break;
                    case 9: manager.searchByType(); break;
                    case 10: listMaintenanceNeeds(); break;
                    case 11: running = false; break;
                    default: System.out.println("Invalid choice. Please enter a number between 1 and 10.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (Exception e) {
                System.err.println("error: " + e.getMessage());
            }
        }
        System.out.println("exiting....");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Remove Vehicle");
        System.out.println("3. Start Journey for All Vehicles");
        System.out.println("4. Refuel All Vehicles");
        System.out.println("5. Perform Maintenance for All Vehicles");
        System.out.println("6. Generate Fleet Report");
        System.out.println("7. Save Fleet to File");
        System.out.println("8. Load Fleet from File");
        System.out.println("9. Search by Type");
        System.out.println("10. List Vehicles Needing Maintenance");
        System.out.println("11. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void loadSampleData() { //demo
        try {
            System.out.println("loading sample data..."); 
            manager.addVehicle(new Car("C-001", "Toyota Camry", 180.0));
            manager.addVehicle(new Truck("T-001", "Volvo FH16", 120.0, 8));
            manager.addVehicle(new Bus("B-001", "Mercedes Tourismo", 140.0,6));
            manager.addVehicle(new Airplane("A-001", "Boeing 747", 900.0, 40000.0));
            manager.addVehicle(new CargoShip("S-001", "Evergreen", 40.0, false));
        } 
        catch (InvalidOperationException e) {
            System.err.println("error: " + e.getMessage());
        }
    }

    private static void addVehicle() throws InvalidOperationException {
        System.out.print("Enter vehicle type (Car, Truck, Bus, Airplane, CargoShip): ");
        String type = scanner.nextLine();
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter Max Speed: ");
        double maxSpeed = scanner.nextDouble();
        scanner.nextLine();
        
        Vehicle v;
        switch (type.toLowerCase()) {
            case "car": v = new Car(id, model, maxSpeed); break;
            case "truck":
                System.out.print("Enter number of wheels: ");
                int wheelstruck = scanner.nextInt(); 
                scanner.nextLine();
                v = new Truck(id, model, maxSpeed, wheelstruck); 
                break;
            case "bus": 
                System.out.print("Enter number of wheels: ");
                int wheelsbus = scanner.nextInt();
                scanner.nextLine();
                v = new Bus(id, model, maxSpeed, wheelsbus);
                break;
            case "airplane":
                System.out.print("Enter max altitude: ");
                double altitude = scanner.nextDouble(); scanner.nextLine();
                v = new Airplane(id, model, maxSpeed, altitude); break;
            case "cargoship":
                System.out.print("Does it have a sail? (true/false): ");
                boolean hasSail = scanner.nextBoolean(); scanner.nextLine();
                v = new CargoShip(id, model, maxSpeed, hasSail); break;
            default: System.out.println("Invalid vehicle type."); return;
        }
        manager.addVehicle(v);
        System.out.println(type + " with ID " + id + " added successfully.");
    }
    
    private static void removeVehicle() throws InvalidOperationException {
        System.out.print("Enter ID of vehicle to remove: ");
        String id = scanner.nextLine();
        manager.removeVehicle(id);
    }

    private static void startJourney() {
        System.out.print("Enter journey distance in km: ");
        double distance = scanner.nextDouble(); scanner.nextLine();
        manager.startAllJourneys(distance);
    }
    
    private static void refuelAll() throws InvalidOperationException {
        System.out.print("Enter amount to refuel in liters: ");
        double amount = scanner.nextDouble(); scanner.nextLine();
        manager.refuelAll(amount);
    }
    
    private static void listMaintenanceNeeds() {
        System.out.println("\nvehicles needing maintenance...");
        List<Vehicle> needsMaintenanceList = manager.vehiclesMaintenance();
        if (needsMaintenanceList.isEmpty()) {
            System.out.println("No vehicles currently need maintenance.");
        } else {
            for (Vehicle v : needsMaintenanceList) {
                v.displayInfo();
            }
        }
    }
}