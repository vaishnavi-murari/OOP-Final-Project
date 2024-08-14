import java.util.Scanner;

public class OwnerHandler {
    private Scanner scanner;
    private Salon salon;
    
    
    public OwnerHandler(Salon salon) {
        this.salon = salon;
        this.scanner = new Scanner(System.in);
    }
    
    
    public void signIn() {
        System.out.println("Sign In");
        System.out.println("=======");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        if (salon.verifyLoginInfo(username, password)) {
            System.out.println("Login successful");
            scanner.nextLine();
            manageSalon();
        }

        else {
            System.out.println("Login error. Try again.");
        }
        scanner.nextLine();
    }
    
    private void manageSalon() {
        System.out.print("\033[H\033[2J");
        int option = 0;
        while (option != 5) {
            System.out.println("Booking Management System");
            System.out.println("=========================");
            System.out.println("\nChoose an option:");
            System.out.println("(1) View Salon Schedule");
            System.out.println("(2) Modify Salon Hours");
            System.out.println("(3) Change Appointment Status");
            System.out.println("(4) Calculate Expected Revenue");
            System.out.println("(5) Exit");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            if (option == 1) {
                System.out.print("\033[H\033[2J");
                viewSalonSchedule();
            }
            
            else if (option == 2) {
                System.out.print("\033[H\033[2J");
                modifySalonHours();
            }
            
            else if (option == 3) {
                System.out.print("\033[H\033[2J");
                changeAppointmentStatus();
            }
            
            else if (option == 4) {
                System.out.print("\033[H\033[2J");
                calculateExpectedRevenue();
            }
            
            else if (option == 5) {
                System.out.println("Exiting management panel.");
            }
            System.out.print("\033[H\033[2J");
        }
    }
    
    private void modifySalonHours() {
        scanner.nextLine();
        String startRange, endRange;
        System.out.println("Modify Salon Operating Hours");
        System.out.println("============================");
        System.out.print("Enter the start date/time for the salon to close (ex. Wed, Aug 14, 2024 2:00 PM): ");
        startRange = scanner.nextLine();
        System.out.print("Enter the end date/time for the salon to close (ex. Wed, Aug 14, 2024 2:00 PM): ");
        endRange = scanner.nextLine();
        DateTimeRange timesToBeClosed = new DateTimeRange(startRange, endRange);
        salon.closeSalon(timesToBeClosed);
        System.out.println("\nClosure added to salon schedule. All appointments scheduled during that time have been cancelled.");
        scanner.nextLine();
    }

    private void changeAppointmentStatus() {
        scanner.nextLine();
        salon.printPendingAppointments();
        System.out.print("\nEnter Appointment ID: ");
        int id = scanner.nextInt();
        BeautyAppointment appointment = salon.getAppointments().get(id-1);
        System.out.println("\n(1) Accept appointment\n(2) Reject appointment");
        System.out.print("Enter your choice: ");
        int statusChoice = scanner.nextInt();
        if(statusChoice == 1) {
            appointment.setStatus("Confirmed");
            System.out.println("Appointment accepted. Press Enter to continue");
            scanner.nextLine();
        }
        else {
            appointment.getProvider().removeFromSchedule(appointment.getDateTime());
            salon.getAppointments().remove(appointment);
            System.out.println("Appointment rejected. Press Enter to continue.");
            scanner.nextLine();
        }
        scanner.nextLine();
    }

    private void calculateExpectedRevenue() {
        scanner.nextLine();
        System.out.println("Calculate Expected Revenue");
        System.out.println("==========================");
        System.out.print("Enter the start date/time (ex. Wed, Aug 14, 2024 2:00 PM): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter the end date/time (ex. Wed, Aug 14, 2024 2:00 PM): ");
        String endDate = scanner.nextLine();
        
        float revenue = salon.calculateRevenue(startDate, endDate);
        System.out.printf("Expected Revenue: $%.2f\n", revenue);
        scanner.nextLine();
    }

    private void viewSalonSchedule() {
        scanner.nextLine();
        salon.printAllAppointments();
        System.out.println("\nPress Enter to return to the main screen.");
        scanner.nextLine();
    }
    
    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
}



