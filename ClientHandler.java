import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * Name: Vaishnavi Murari
 * Date: August 11, 2024
 */

public class ClientHandler {
    private Scanner scanner;
    private Salon salon;
    public ClientHandler(){
        scanner = new Scanner(System.in);
        salon = new Salon();
    }

    public Salon getSalon(){
        return salon;
    }

    public void setSalon(Salon salonInfo){
        salon = salonInfo;
    }

    /*
     * Prompts the client to select an option for the program
     */
    public void printAndGetChoices(){
        int clientChoice = 0;
        while (clientChoice != 3) {
            System.out.println("Welcome to the salon!");
            System.out.println("=====================");
            System.out.println("Choose one of the following options:");
            System.out.println("(1) Book an appointment\n(2) View the details of your appointment\n(3) Quit");
            System.out.print("Enter your choice: ");
            clientChoice = scanner.nextInt();
            if (clientChoice == 1) {
                System.out.print("\033[H\033[2J");
                addNewAppt();
            }
            else if (clientChoice == 2) {
                System.out.print("\033[H\033[2J");
                viewApptDetails();
            }
            System.out.print("\033[H\033[2J");
        }
    }

    /*
     * Prints all of the available services in the salon and prompts the client to choose a service
     * 
     * @return  the client's chosen service
     */
    private Service printAndChooseService() {
        ArrayList<Service> services = salon.getServices();
        System.out.println("Services Available");
        System.out.println("==================");
        System.out.printf("%-2s | %-14s | %s\n", "ID", "Name", "Cost");
        System.out.println("----------------------------");
        for(int i=0; i< services.size(); i++) {
            System.out.printf("%-2s | %-14s | $%.2f\n", i+1, services.get(i).getName(), + services.get(i).getCost());
        }
        System.out.print("Enter the ID number of the service you would like: ");
        int i = scanner.nextInt();
        return services.get(i-1);
    }

    /*
     * Prints all of the stylists trained to perform the client's chosen service and prompts the client to choose a stylist
     * 
     * @param   the client's chosen service
     * @return  the client's chosen stylist
     */
    private Stylist printAndChooseStylist(Service service) {
        // Determines which stylists are trained to perform the service
        ArrayList<Stylist> salonStylists = salon.getStylists();
        ArrayList<Stylist> stylists = new ArrayList<Stylist>();
        for(int i=0; i < salonStylists.size(); i++) {
            if(salonStylists.get(i).getServices().contains(service)) {
                stylists.add(salonStylists.get(i));
            }
        }

        // Prompts the client to choose a stylist
        System.out.println("\n\nStylists Available");
        System.out.println("==================");
        System.out.printf("%-2s | %-20s | %s\n", "ID", "Name", "Rating");
        System.out.println("--------------------------------");
        for(int i=0; i< stylists.size(); i++) {
            System.out.printf("%-2s | %-20s | %.1f\n", i+1, stylists.get(i).getName(), stylists.get(i).getRatings());
        }
        System.out.print("Enter the ID number of the stylist you would like: ");
        int i = scanner.nextInt();
        return stylists.get(i-1);
    }

    /*
     * Prints all of the dates/times the chosen stylist is available for an appointment and prompts the client to choose a date/time
     * 
     * @param stylist   the client's chosen stylist
     * @return          the client's chosen appointment date/time
     */
    private LocalDateTime printAndChooseDateTime(Stylist stylist) {
        // NEED TO IMPLEMENT
        LocalDateTime time = LocalDateTime.of(2024, 8, 12, 11, 0, 0);
        return time;
    }

    /*
     * Prompts the client to choose a service, stylist, and date/time for their appointment. Then prompts the client
     * for their name, contact information, and payment information and saves the new appointment to the salon's
     * list of appointments.
     */
    public void addNewAppt() {
        scanner.nextLine();
        Service service;
        Stylist stylist;
        LocalDateTime dateTime;
        String clientName, clientContact, clientPayment;

        System.out.println("Book New Appointment");
        System.out.println("====================");
        service = printAndChooseService();
        stylist = printAndChooseStylist(service);
        dateTime = printAndChooseDateTime(stylist);
        scanner.nextLine();
        System.out.print("Name: ");
        clientName = scanner.nextLine();
        System.out.print("Contact Information: ");
        clientContact = scanner.nextLine();
        System.out.print("Payment Information: ");
        clientPayment = scanner.nextLine();
        salon.addAppointment(dateTime, stylist, service, clientName, clientContact, clientPayment);
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Saved successfully...Press Enter to return to the main screen");
        scanner.nextLine();
    }

    /*
     * Uses the client's name to search for their appointment in the salon schedule.
     * If the appointment exists, prints a formatted output of the appointment details and asks
     * the client if they want to make changes to their appointment.
     */
    public void viewApptDetails(){
        // Prompts the client to input their name
        scanner.nextLine();
        String clientName;
        System.out.println("View Appointment Details");
        System.out.println("========================");
        System.out.println("Enter the name associated with your appointment: ");
        clientName = scanner.nextLine();
        System.out.println();

        // Finds the appointment and asks the client if they would like to proceed further
        Appointment a = salon.findAppointment(clientName);
        boolean found = salon.printApptDetails(a);
        if (found) {
            System.out.println("\nWould you like to make any changes to your appointment?");
            System.out.println("(1) Yes\n(2) No");
            System.out.print("Enter your choice: ");
            int clientChoice = scanner.nextInt();
            if (clientChoice == 1) {
                modifyAppt(a);
            }
        }
        else {
            System.out.println("Sorry, we couldn't find an appointment under that name.");
        }
        System.out.println("Press Enter to return to the main screen");
        scanner.nextLine();
    }

    /*
     * Allows the salon client to change the date/time, stylist, and service associated with their appointment
     * up to 24 hours before their original appointment time. The client will be asked to reselect all three
     * to ensure that there are no conflicts with the new service, new stylist, or new date/time.
     * 
     * The client will also have the option to fully cancel their appointment. If the appointment is cancelled
     * less than 24 hours before the appointment time, the client will confirm that they will be charged 50% of
     * the cost of their service as a cancellation fee.
     * 
     * @precondition        a is an existing appointment in the salon's appointment list
     * @param a             the appointment being modified
     */
    public void modifyAppt(Appointment a){
        System.out.println("\nModify Appointment");
        System.out.println("==================");
        System.out.println("(1) Edit appointment service, stylist, and date/time\n(2) Cancel appointment");
        System.out.print("Enter your choice: ");
        int modChoice = scanner.nextInt();
        if (modChoice == 2) {                               // Client wishes to cancel appt
            salon.cancelAppointment(a);
            scanner.nextLine();
        }
        else {
            LocalDateTime oneDayFromNow = LocalDateTime.now().plusHours(24);
            if (oneDayFromNow.isAfter(a.getDateTime())) {   // Within 24 hours of appt, client cannot make changes
                System.out.println("\nSorry, it's too soon before your appointment to make changes.");
                scanner.nextLine();
            }
            else {                                          // More than 24 hours before appt, client can make changes
                Service newService = printAndChooseService();
                Stylist newStylist = printAndChooseStylist(newService);
                LocalDateTime newDateTime = printAndChooseDateTime(newStylist);
                a.setService(newService);
                a.setStylist(newStylist);
                a.setDateTime(newDateTime);
            }
        }    
    }
}