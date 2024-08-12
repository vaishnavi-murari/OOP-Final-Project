import java.util.Scanner;

public class OwnerHandler {
    private Scanner scanner;
    private Salon salon;
    
    
    public OwnerHandler(Salon salon) {
        this.salon = salon;
        this.scanner = new Scanner(System.in);
    }
    
    
    public void signIn() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        if (salon.verifyLoginInfo(username, password)) {
            System.out.println("Login successful");
            manageSalon();
        }

        else {
            
            System.out.println("Login Error.");
        }
    }
    
    private void manageSalon() {
        int option = 0;
        while (option != 6) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Salon Schedule");
            System.out.println("2. View Pending Appointments");
            System.out.println("3. Modify Salon Hours");
            System.out.println("4. Change Appointment Status");
            System.out.println("5. Calculate Expected Revenue");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine();
    
            if (option == 1) {
                viewSalonSchedule();
            }
            
            else if (option == 2) {
                salon.printPendingAppointments();
            } 
            
            else if (option == 3) {
                modifySalonHours();
            }
            
            else if (option == 4) {
                changeAppointmentStatus();
            }
            
            else if (option == 5) {
                calculateExpectedRevenue();
            }
            
            else if (option == 6) {
                System.out.println("Exiting management panel.");
            }
        }
    }
    
    private void modifySalonHours() {
        System.out.println("Modify Salon's operating hours:");
    }

    private void changeAppointmentStatus() {
        System.out.print("Enter Appointment ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter Status: ");
        String status = scanner.nextLine();
        Appointment appointment = salon.getAppointments().get(id);
        appointment.setStatus(status);
        System.out.println("Appointment updated.");
    }

    private void calculateExpectedRevenue() {
        System.out.print("Enter start date: ");
        String startDate = scanner.nextLine();
        System.out.print("Enter end date: ");
        String endDate = scanner.nextLine();
        
        float revenue = salon.calculateRevenue(startDate, endDate);
        System.out.printf("Expected revenue:" + revenue);
    }

    private void viewSalonSchedule() {
        System.out.println("View Salon Schedule:");
        salon.printAllAppointments();
    }
    
    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}



