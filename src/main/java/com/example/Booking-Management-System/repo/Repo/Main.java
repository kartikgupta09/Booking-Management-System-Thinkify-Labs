import java.util.List;

public class Main {
    public static void main(String[] args) {
        CabBookingSystem cabSystem = new CabBookingSystem();

        // Onboard users
        System.out.print(cabSystem.addUser("Abhishek, F, 23")+"\n");
        System.out.print(cabSystem.addUser("Rahul, M") + "\n");
        System.out.print(cabSystem.addUser("Rahul, M, 20") + "\n");
        System.out.print(cabSystem.addUser("Nandani, F, 23") + "\n");
//
////        // Onboard drivers
        System.out.print(cabSystem.addDriver("Driver1, M, 22", "Swift, KA-01-12345",new int[]{10, 1}) + "\n");
        System.out.print(cabSystem.addDriver("Driver2, M, 29","Swift, KA-01-12345", new int[]{11, 10}) + "\n");
        System.out.print(cabSystem.addDriver("Driver3, M, 29","Swift, KA-01-12345", new int[]{5, 3}) + "\n");
        System.out.print(cabSystem.addDriver("Driver4, M, 29","Swift, KA-01-12345", new int[]{14,2}) + "\n");
//
//
//
        System.out.println(cabSystem.findRide("Abhishek", new int[]{-1, 0}, new int[]{20, 1}));

        System.out.println(cabSystem.findRide("Rahul", new int[]{10, 0}, new int[]{15, 3}));
        System.out.println(cabSystem.findRide("Nandani", new int[]{1, 6}, new int[]{0, 4}));

        List<String> driverList = cabSystem.findRide("Rahul", new int[]{10, 0}, new int[]{15, 3});

        System.out.println(cabSystem.chooseRide("Rahul", driverList));
//        cabSystem.showAvailableDriversAndUsers();
    }

}
