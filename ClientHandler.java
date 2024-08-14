import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;

/*
 * Last Updated: August 13, 2024
 */

public class ClientHandler {
    private Scanner scanner;
    private Salon salon;
    public ClientHandler(Salon sa){
        scanner = new Scanner(System.in);
        salon = sa;
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
        System.out.println("---------------------------");
        for(int i=0; i< services.size(); i++) {
            System.out.printf("%-2s | %-14s | $%.2f\n", i+1, services.get(i).getName(), + services.get(i).getPrice());
        }
        System.out.print("\nEnter the ID number of the service you would like: ");
        int serviceChoice = scanner.nextInt();
        return services.get(serviceChoice-1);
    }

    /*
     * Prints all of the stylists trained to perform the client's chosen service and prompts the client to choose a stylist
     * 
     * @param   the client's chosen service
     * @return  the client's chosen stylist
     */
    private Provider printAndChooseProvider(Service service) {
        // Determines which stylists are trained to perform the service
        ArrayList<Provider> allProviders = salon.getProviders();
        ArrayList<Provider> trainedProviders = new ArrayList<Provider>();
        for(int i=0; i < allProviders.size(); i++) {
            if(allProviders.get(i).getServices().contains(service)) {
                trainedProviders.add(allProviders.get(i));
            }
        }

        // Prompts the client to choose a provider
        System.out.println("\n\nProviders Available");
        System.out.println("===================");
        System.out.printf("%-2s | %-16s | %s\n", "ID", "Name", "Rating");
        System.out.println("------------------------------");
        for(int i=0; i< trainedProviders.size(); i++) {
            System.out.printf("%-2s | %-16s | %.1f\n", i+1, trainedProviders.get(i).getName(), trainedProviders.get(i).getRatings());
        }
        System.out.print("\nEnter the ID number of the provider you would like: ");
        int providerChoice = scanner.nextInt();
        return trainedProviders.get(providerChoice-1);
    }

    /*
     * Prints at least 5 dates/times the chosen stylist is available for an appointment and prompts the client to choose a date/time
     * 
     * @param stylist   the client's chosen stylist
     * @param service   the client's chosen service
     * @return          the client's chosen appointment date/time
     */
    private DateTimeRange printAndChooseDateTime(Provider provider, Service service) {
        ArrayList<DateTimeRange> availableTimes = findAvailableRanges(provider, service);
        System.out.println("\n\nDates and Times Available");
        System.out.println("=========================");
        System.out.printf("%-2s | %-26s\n", "ID", "Date/Time");
        System.out.println("-------------------------------");
        for(int i=0; i < 5; i++) {
            System.out.printf("%-2s | %-26s\n", i+1, availableTimes.get(i).startToString());
        }
        System.out.print("\nEnter the ID number of the date/time you would like: ");
        int dateTimeChoice = scanner.nextInt();
        return availableTimes.get(dateTimeChoice-1);
    }

    /*
     * Helper method that finds at least 5 available times for a client to book an appointment.
     * The available times will fall between 24 hours from today's date and 2 days after that.
     * These available times cannot be when the chosen provider is unavailable due to scheduled appointment or salon closure.
     * If at least 5 available times cannot be found within that time frame, then the process repeats with consecutive
     * weeks until 5 available times can be found.
     * 
     * @precondition    the given provider is trained to perform the given service
     * 
     * @return          an ArrayList of 5 possible ranges of time when the appointment could take place
     * 
     */
    private ArrayList<DateTimeRange> findAvailableRanges(Provider provider, Service service) {
        ArrayList<DateTimeRange> apptAvailability = new ArrayList<DateTimeRange>();
        ArrayList<DateTimeRange> providerUnavailable = provider.getUnavailable();
        int serviceLength = service.getDuration();
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startSearch = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth()+1, today.getHour()+1, 0);
        LocalDateTime endSearch = startSearch.plusDays(2);
        DateTimeRange apptDuration;
        boolean conflictFound;
        int unavailIndex;

        // Runs until at least 5 times are found
        while(apptAvailability.size() < 5) {
            // Runs until the search window has been completed
            while(!startSearch.isAfter(endSearch)) {
                apptDuration = new DateTimeRange(startSearch, startSearch.plusMinutes(serviceLength));
                conflictFound = false;
                unavailIndex = 0;
                // Checks appt duration against all of the provider's conflicts
                for(int i=0; i < providerUnavailable.size(); i++) {
                    if (apptDuration.conflictsWith(providerUnavailable.get(i))) {
                        conflictFound = true;
                        unavailIndex = i;
                    }
                }
                // Starts at the provider's next available time
                if(conflictFound) {
                    startSearch = providerUnavailable.get(unavailIndex).getEndOfRange();
                }
                else {
                    // Checks to see if appointment ends before salon closes
                    if(apptDuration.getEndOfRange().getHour() < salon.getHourClosed() || 
                    (apptDuration.getEndOfRange().getHour() == salon.getHourClosed() && apptDuration.getEndOfRange().getMinute() == 0)) {
                        apptAvailability.add(apptDuration);
                        startSearch = startSearch.plusHours(1);
                    }
                    // Otherwise begins search at opening the next day
                    else {
                        startSearch = LocalDateTime.of(startSearch.getYear(), startSearch.getMonth(), startSearch.getDayOfMonth()+1, salon.getHourOpen(), 0);
                    }
                }
            }
            // Sets the start of the new search window to 10 AM the morning after the end of the previous search window
            startSearch = endSearch;
            endSearch = startSearch.plusDays(2);
        }
        return apptAvailability;
    }

    /*
     * Prompts the client to choose a service, provider, and date/time for their appointment. Then prompts the client
     * for their name, contact information, and payment information and saves the new appointment to the salon's
     * list of appointments.
     */
    private void addNewAppt() {
        scanner.nextLine();
        Service service;
        Provider provider;
        DateTimeRange dateTime;
        String clientName, clientContact, clientPayment;

        System.out.println("Book New Appointment");
        System.out.println("====================");
        service = printAndChooseService();
        provider = printAndChooseProvider(service);
        dateTime = printAndChooseDateTime(provider, service);
        scanner.nextLine();
        System.out.print("\nName: ");
        clientName = scanner.nextLine();
        System.out.print("\nContact Information: ");
        clientContact = scanner.nextLine();
        System.out.print("\nPayment Information: ");
        clientPayment = scanner.nextLine();
        salon.addAppointment(dateTime, provider, service, clientName, clientContact, clientPayment);
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Saved successfully...Press Enter to return to the main screen");
        scanner.nextLine();
    }

    /*
     * Uses the client's name to search for their appointment in the salon schedule.
     * If the appointment exists, prints a formatted output of the appointment details and asks
     * the client if they want to make changes to their appointment.
     */
    private void viewApptDetails(){
        // Prompts the client to input their name
        scanner.nextLine();
        String clientName;
        System.out.println("View Appointment Details");
        System.out.println("========================");
        System.out.printf("Enter the name associated with your appointment: ");
        clientName = scanner.nextLine();
        System.out.println("\n");

        // Finds the appointment and asks the client if they would like to proceed further
        BeautyAppointment a = salon.findAppointment(clientName);
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
    private void modifyAppt(BeautyAppointment a){
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
            if (oneDayFromNow.isAfter(a.getDateTime().getStartOfRange())) {   // Within 24 hours of appt, client cannot make changes
                System.out.println("\nSorry, it's too soon before your appointment to make changes.");
                scanner.nextLine();
            }
            else {                                          // More than 24 hours before appt, client can make changes
                Provider oldProvider = a.getProvider();
                DateTimeRange oldDateTime = a.getDateTime();
                oldProvider.removeFromSchedule(oldDateTime);

                Service newService = printAndChooseService();
                Provider newProvider = printAndChooseProvider(newService);
                DateTimeRange newDateTime = printAndChooseDateTime(newProvider, newService);
                a.setService(newService);
                a.setProvider(newProvider);
                a.setDateTime(newDateTime);
                newProvider.addToSchedule(newDateTime);
                scanner.nextLine();
            }
        }   
        scanner.nextLine(); 
    }
}