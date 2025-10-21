Transportation Fleet Management System

This assignment is built on four core OOP ideas. Here’s how you can see them in the code:
Inheritance: This is shown in the class hierarchy. A Car is-a LandVehicle, which in turn is-a Vehicle. This allows specific vehicle types like Car to inherit the common properties and methods from the more general LandVehicle and Vehicle classes, avoiding code repetition.
Polymorphism: This is the concept that lets us treat different objects in the same way. The FleetManager has a single List<Vehicle>. When we call the startAllJourneys() method, it simply calls .move() on all the objects. It doesn't need to know if it's a Car, Truck, or Airplane. The specific version of move() (driving, flying, etc.) is automatically executed for each object.
Abstract Classes: Vehicle and LandVehicle are abstract classes. This is because you can't create a generic "vehicle"; you can only create a specific type of vehicle, like a Car. The abstract classes serve as a template, forcing all concrete subclasses to implement essential methods like move().
Interfaces: An interface is defined as "acts-as". For example, the FuelConsumable interface defines what a vehicle that uses fuel can do. A Car and a Truck are very different, but they both can implement the FuelConsumable interface because they both use fuel. This allows us to give common capabilities to otherwise unrelated classes.


Code Implementation Details

The project is organised into packages for clarity and maintainability.

fleet

Main.java: This is the entry point of the application. Its main method is what runs first. It is responsible for creating the FleetManager and the Scanner for user input. It contains the main application loop, which displays the menu (printMenu), processes user choices, and calls the appropriate methods in the FleetManager.
FleetManager.java: This class holds the private List<Vehicle> fleet, which stores all the vehicle objects in the fleet.
addVehicle() / removeVehicle(): Safely add and remove vehicles, checking for duplicate IDs.
startAllJourneys() / maintainAll(): These methods demonstrate polymorphism by iterating through the entire fleet and calling a general method (move() or performMaintenance()) on each vehicle, allowing each object to perform its specific action.
generateReport(): Gathers information from every vehicle and formats it into a user-friendly report.
saveToFile() / loadFromFile(): Manages persistence by converting vehicle objects to CSV strings and back. loadFromFile uses the private createVehicleFromData factory method to reconstruct the correct type of vehicle from the file.
searchByType(): Allows the user to filter the fleet based on a class or interface type.

fleet.exceptions (Custom Errors)

This package contains custom checked exceptions to handle specific logical errors.
InvalidOperationException: Used for illogical actions, like moving a negative distance.
OverloadException: Used when trying to load more passengers or cargo than a vehicle can handle.
InsufficientFuelException: Used when a vehicle tries to travel a distance but doesn't have enough fuel.

fleet.interfaces (Capabilities)

This package defines the "can-do" contracts for various vehicle capabilities.
FuelConsumable: A contract for vehicles that use fuel. Requires methods like refuel() and consumeFuel().
CargoCarrier: A contract for vehicles that can carry cargo. Requires loadCargo() and unloadCargo().
PassengerCarrier: A contract for vehicles that can carry passengers. Requires boardPassengers().
Maintainable: A contract for vehicles that need maintenance. Requires needsMaintenance() and performMaintenance().

fleet.vehicles (The Vehicle Hierarchy)

This package contains the class hierarchy for all vehicle types.
Vehicle.java (Abstract Class): The root of the hierarchy. It defines the common properties for ALL vehicles (id, model, mileage) and declares abstract methods (move, calculateFuelEfficiency) that every concrete vehicle MUST implement. It also implements Comparable to allow for sorting.
LandVehicle, AirVehicle, WaterVehicle (Abstract Classes): These are intermediate classes that provide specialized properties and behaviors for each travel type. For example, LandVehicle adds numWheels. They inherit from Vehicle.
Car.java, Truck.java, Bus.java, Airplane.java, CargoShip.java (Concrete Classes): These are the final, usable objects. Each one extends an abstract vehicle class (e.g., Car extends LandVehicle) and implements one or more interfaces (e.g., implements FuelConsumable, PassengerCarrier). They provide the final, specific implementations for all abstract methods they inherit, such as the unique move() message and fuel efficiency calculation for each type.


How to Compile and Run
1.  Open Your Terminal:
    Open the Command Prompt (cmd) or PowerShell on Windows.

2.  Navigate to the "src" Directory:
    Use the "cd" command to go into the "src" folder of the project.
    C:\Users\prans\OneDrive\Desktop\AP_A1\src

3.  Compile All Files:
    a) Create a list of all .java files.
        In Command Prompt (cmd):
        dir /s /b *.java > sources.txt
    b) Compile using the list.
         javac @sources.txt
    If this command finishes without any errors, the assignment is compiled.

4.  Run the Application:
    Make sure you are still in the "src" directory and run this command:
    java -cp . fleet.Main
    The application's menu will now appear.

How to Use the Command-Line Interface (CLI)

The main menu gives you several options. Just type the number and press Enter.
1-2. Add/Remove Vehicle: Lets you add a new vehicle to the fleet or remove an existing one by its ID.
3. Start Journey: Asks for a distance (in km) and simulates all vehicles traveling that far.
4. Refuel All: Asks for an amount of fuel and adds it to all compatible vehicles.
5. Perform Maintenance: Performs maintenance on any vehicles that need it.
6. Generate Report: Shows a summary of all vehicles in the fleet, including mileage and fuel levels.
7-8. Save/Load Fleet: Saves the current state of your fleet to fleet_data.csv or loads a fleet from it.
9. List Vehicles Needing Maintenance: Shows a special list of only the vehicles that need repairs.
10. exit

A Simple Walkthrough

Here's a simple demo you can run to see the features in action:
Start the Program. The initial sample fleet is loaded.
Select option 6 (Generate Report).
Expected Output: You will see a list of the starting vehicles. Note that they all have 0.0 km mileage and 0.0 L of fuel.
Select option 4 (Refuel All Vehicles). Enter 50 for the amount.
Expected Output: Confirmation messages for vehicles being refueled.
Select option 6 (Generate Report) again.
Expected Output: The report now shows that the vehicles have 50.0 L of fuel, but their mileage is still 0.0 km.
Select option 3 (Start Journey). Enter 100 for the distance.
Expected Output: Messages for each vehicle moving.
Select option 6 (Generate Report) one last time.
Expected Output: The report now shows that all vehicles have 100.0 km mileage, and their fuel levels have decreased.
Select option 7 (Save Fleet to File).
Expected Output: A confirmation message that the fleet state was saved to fleet_data.csv.
