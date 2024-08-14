import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.Serializable;

/*
 * Last Updated: August 13, 2024
 */
public class Salon implements Serializable{
    private ArrayList<BeautyAppointment> appointments;
    private ArrayList<Provider> providers;
    private ArrayList<Service> services;
    private ArrayList<DateTimeRange> salonClosed;
    private String loginUsername;
    private String loginPassword;
    private final int hourOpen = 10;
    private final int hourClosed = 17;

    public Salon(){
        appointments = new ArrayList<BeautyAppointment>();
        providers = new ArrayList<Provider>();
        services = new ArrayList<Service>();
        salonClosed = new ArrayList<DateTimeRange>();
        loginUsername = "JV";
        loginPassword = "OOPFinalProject";
    }

    /*
     * Searches for an appointment using the given client name
     * 
     * @precondition        an appointment with the given client name exists
     * @param clientName    the name of a salon client
     * @return              the appointment associated with the given client name
     */
    public BeautyAppointment findAppointment(String clientName) {
        for (int i=0; i < appointments.size(); i++) {
            if (appointments.get(i).getClientName().equals(clientName)) {
                return appointments.get(i);
            }
        }
        return null;
    }
    
    /*
     * Creates a new appointment with the given information and adds it to the list of appointments.
     * This method will insert the new appointment in the list so that the list is in chronological
     * order of appointment time.
     * 
     * @param dt        the range of time from when the appointment starts to when it ends
     * @param p         the client's chosen provider
     * @param ser       the client's chosen service
     * @param cName     the client-inputted name for the appointment
     * @param cContact  the client-inputted contact info for the appointment
     * @param cPayment  the client-inputted payment info for the appointment
     */
    public void addAppointment(DateTimeRange dt, Provider p, Service ser, String cName, String cContact, String cPayment) {
        BeautyAppointment a = chooseAppointmentType(dt, p, ser, cName, cContact, cPayment);
        int index = 0;
        while (index < appointments.size() && dt.startsAfter(appointments.get(index).getDateTime())) {
            index++;
        }
        appointments.add(index, a);
        p.addToSchedule(dt);
    }

    /*
     * Determines what type of appointment to create based on the type of provider
     * 
     * @param dt        the range of time from when the appointment starts to when it ends
     * @param p         the client's chosen provider, whose type determines what kind of appointment to create
     * @param ser       the client's chosen service
     * @param cName     the client-inputted name for the appointment
     * @param cContact  the client-inputted contact info for the appointment
     * @param cPayment  the client-inputted payment info for the appointment
     * @return          a beauty appointment of a more specific type
     */
    private BeautyAppointment chooseAppointmentType(DateTimeRange dt, Provider p, Service ser, String cName, String cContact, String cPayment) {
        BeautyAppointment a = null;
        if (p.getType().equals("hairstylist")) {
            a = new HairAppointment(dt, (Stylist) p, ser, cName, cContact, cPayment);
        }
        return a;
    }

    /*
     * Prints a client-friendly, formatted output of an appointment.
     * 
     * @param a     the given appointment
     * @return      true if the appointment exists; otherwise,
     *              false
     */
    public boolean printApptDetails(BeautyAppointment a) {
        if (a != null) {
            a.printDetails();
            return true;
        }
        return false;
    }

    /*
     * Removes an appointment from the list of salon appointments
     * 
     * @precondition        a is an existing appointment in the salon
     * @param a             the appointment being canceled
     */
    public void cancelAppointment(BeautyAppointment a) {
        LocalDateTime oneDayFromNow = LocalDateTime.now().plusHours(24);
        if (oneDayFromNow.isAfter(a.getDateTime().getStartOfRange())) {
            System.out.printf("\nYou will be charged a cancellation fee of $%.2f.\n", a.getService().getPrice()/2);
        }
        appointments.remove(a);
        a.getProvider().removeFromSchedule(a.getDateTime());
        System.out.println("\nYour appointment has been successfully cancelled.");
    }

    /*
     * Checks if the owner-inputted username and password match the salon username and password
     * 
     * @param username  a String typed in by the salon owner
     * @param password  a String typed in by the salon owner
     * @return          true if the username and password match the salon's username and password; otherwise,
     *                  false
     */
    public boolean verifyLoginInfo (String username, String password) {
        if (username.equals(loginUsername) && password.equals(loginPassword)) {
            return true;
        }
        return false;
    }

    /*
     * Prints a schedule of all salon appointments
     */
    public void printAllAppointments () {
        System.out.println("Salon Schedule");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-2s | %-26s | %-14s | %-16s | %-20s | %-20s | %-12s\n", "ID", "Date/Time", "Service", "Provider", "Client Name", "Client Contact", "Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        for(int i=0; i<appointments.size();i++) {
            System.out.printf("%-2s | ", i+1);
            appointments.get(i).printInfo();
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    /*
     * Prints a list of the current pending appointments
     */
    public void printPendingAppointments(){
        System.out.println("Pending Appointments");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-2s | %-26s | %-14s | %-16s | %-20s | %-20s | %-12s\n", "ID", "Date/Time", "Service", "Provider", "Client Name", "Client Contact", "Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        for(int i=0; i<appointments.size();i++) {
            if(appointments.get(i).getStatus().equals("Pending")) {
                System.out.printf("%-2s | ", i+1);
                appointments.get(i).printInfo();
            }
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    /*
     * Adds the given range of days/times to the salon's list of days/times that it's closed.
     * This method will insert the new closure time in the list so that the list maintains chronological
     * order by start time.
     * 
     * This method will also cancel any appointments that were scheduled to take place during the new
     * closure time and will update all providers' availability to reflect the new closure.
     * 
     * @param timesToBeClosed   a range of time when the salon should now be closed
     */
    public void closeSalon (DateTimeRange timesToBeClosed) {
        // Adds the range of times to the salon's closed hours
        int index = 0;
        while (index < salonClosed.size() && timesToBeClosed.startsAfter(salonClosed.get(index))) {
            index++;
        }
        salonClosed.add(index, timesToBeClosed);

        // Cancels any appointments scheduled during timesToBeClosed
        for(int i=0; i<appointments.size(); i++) {
            BeautyAppointment a = appointments.get(i);
            DateTimeRange apptDuration = a.getDateTime();
            if(apptDuration.conflictsWith(timesToBeClosed)) {
                appointments.remove(a);
            }
        }

        // Updates all provider availability so they're unavailable when the salon should be closed
        for(int i=0; i<providers.size(); i++) {
            providers.get(i).addToSchedule(timesToBeClosed);
        }
    }

    /*
     * Calculates the expected revenue from the salon in a given time frame
     * 
     * @param startDate     the date to begin calculating the expected revenue, in the form "E, MMM dd"
     * @param endDate       the date to end calculating the expected revenue, in the form "E, MMM dd"
     * @return              the total cost of all services performed between startDate and endDate
     */
    public float calculateRevenue(String startDate, String endDate) {
        float revenue = 0;
        BeautyAppointment a;
        DateTimeRange range = new DateTimeRange(startDate, endDate);
        for(int i=0; i<appointments.size(); i++) {
            a = appointments.get(i);
            if(a.getDateTime().getStartOfRange().isBefore(range.getEndOfRange()) && a.getDateTime().getEndOfRange().isAfter(range.getStartOfRange())) {
                revenue += a.getService().getPrice();
            }
        }
        return revenue;
    }

    // GETTERS AND SETTERS

    public ArrayList<BeautyAppointment> getAppointments() {
        return appointments;
    }

    public void setAppointments (ArrayList<BeautyAppointment> a) {
        appointments = a;
    }

    public ArrayList<Provider> getProviders() {
        return providers;
    }

    public void setProviders (ArrayList<Provider> provs) {
        providers = provs;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices (ArrayList<Service> ser) {
        services = ser;
    }

    public ArrayList<DateTimeRange> getSalonClosed() {
        return salonClosed;
    }

    public void setSalonClosed (ArrayList<DateTimeRange> timesClosed) {
        salonClosed = timesClosed;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String user) {
        loginUsername = user;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String pass) {
        loginPassword = pass;
    }

    public int getHourOpen() {
        return hourOpen;
    }

    public int getHourClosed() {
        return hourClosed;
    }

}
