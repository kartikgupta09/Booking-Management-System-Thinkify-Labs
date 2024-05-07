import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class CabBookingSystem {
    private Map<String, User> users;
    private Map<String, Driver> drivers;
    private List<String> bookedDrivers;
    private Lock lock;

    CabBookingSystem() {
        this.users = new HashMap<>();
        this.drivers = new HashMap<>();
        this.bookedDrivers = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    void showAvailableDriversAndUsers() {
        System.out.println("Available Drivers:");
        for (Map.Entry<String, Driver> entry : drivers.entrySet()) {
            if (!bookedDrivers.contains(entry.getKey())) {
                System.out.println("Name: " + entry.getValue().name +
                        ", Age: " + entry.getValue().age +
                        ", Gender: " + entry.getValue().gender +
                        ", Vehicle: " + entry.getValue().vehicleDetails);
            }
        }

        System.out.println("\nAvailable Users:");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            System.out.println("Username: " + entry.getValue().username);
        }
    }

    String addUser(String userDetail) {
        if (userDetail == null || userDetail.isEmpty()) {
            return "User detail cannot be null or empty.";
        }

        String[] userDetails = userDetail.split(", ");
        if (userDetails.length != 3) {
            return "Invalid user detail format. Please provide name, gender, and age separated by commas.";
        }
        if(Integer.parseInt(userDetails[2]) <=0){
            return "Invalid age detail";
        }
        if(userDetails[0]==""){
            return "User with no name cannot be created";
        }
        if (!userDetails[1].equals("M") && !userDetails[1].equals("F")) {
            return "User with gender M or F exists only";
        }


        String name = userDetails[0];
        String gender = userDetails[1];
        int age;
        try {
            age = Integer.parseInt(userDetails[2]);
        } catch (NumberFormatException e) {
            return "Invalid age format. Age must be a valid integer.";
        }

        User user = new User(name, gender, age);
        users.put(name, user);

        return "User " + name + " added successfully.";
    }
    String addDriver(String driverDetails, String vehicleDetails, int[] currentLocation) {
        if (driverDetails == null || driverDetails.isEmpty() || vehicleDetails == null || vehicleDetails.isEmpty() || currentLocation == null || currentLocation.length != 2) {
            return "Invalid driver details, vehicle details, or current location.";
        }

        String[] details = driverDetails.split(", ");
        if (details.length != 3) {
            return "Invalid driver details format. Please provide name, gender, and age separated by commas.";
        }
        if(Integer.parseInt(details[2])<=0){
            return "Invalid age";
        }
        if (!details[1].equals("M") && !details[1].equals("F")) {
            return "User with gender M or F exists only";
        }

        if(details[0].isEmpty()){
            return "Invalid details";
        }
        if(currentLocation[0]<0 || currentLocation[1]<0){
            return "Invalid coordinates";
        }

        String name = details[0];
        String gender = details[1];
        int age;
        try {
            age = Integer.parseInt(details[2]);

        } catch (NumberFormatException e) {
            return "Invalid age format. Age must be a valid integer.";
        }

        // Create the Driver object
        Driver driver = new Driver(name, gender, age, vehicleDetails, currentLocation);

        // Add the driver to the drivers map
        drivers.put(name, driver);

        return "Driver " + name + " added successfully.";
    }

    private double calculateDistance(int[] loc1, int[] loc2) {
        return Math.sqrt(Math.pow(loc1[0] - loc2[0], 2) + Math.pow(loc1[1] - loc2[1], 2));
    }

    List<String> findRide(String username, int[] source, int[] destination) {
        if (!users.containsKey(username)) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("User " + username + " does not exist.");
            return errorMessage;
        }
        if(source[0]<0 || source[1]<0 || destination[0]<0 || destination[1]<0){
            List<String> ll = new ArrayList<>();
            ll.add("Invalid coordinates");
            return ll;
        }
        if(username.isEmpty()){
            List<String> ll = new ArrayList<>();
            ll.add("Name can't be empty");
            return ll;
        }
        List<String> availableRides = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (!bookedDrivers.contains(driver.name)) {
                double distanceSource = calculateDistance(source, driver.currentLocation);
                double distanceDestination = calculateDistance(destination, driver.currentLocation);
                if (distanceSource <= 5 ) {
                    availableRides.add(driver.name);
                }
            }
        }

        if (availableRides.isEmpty()) {
            List<String> noRideMessage = new ArrayList<>();
            noRideMessage.add("No ride found for user " + username);
            return noRideMessage;
        } else {
            return availableRides;
        }
    }


    String chooseRide(String username, List<String> availableDrivers) {
        if (!users.containsKey(username)) {
            return "User " + username + " does not exist.";
        }

        // Check if the list of available drivers is not null or empty
        if (availableDrivers == null || availableDrivers.isEmpty()) {
            return "No available drivers to choose from.";
        }

        // Select the first driver from the list of available drivers
        String selectedDriver = availableDrivers.get(0);

        // Mark the selected driver as booked
        bookedDrivers.add(selectedDriver);

        return "Ride with driver " + selectedDriver + " selected successfully.";
    }
}

