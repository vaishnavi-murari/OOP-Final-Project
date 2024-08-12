import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.Serializable;

/*
 * Name: Vaishnavi Murari
 * Date: August 12, 2024
 */
public class Salon implements Serializable{
    private ArrayList<Appointment> appointments;
    private ArrayList<Stylist> stylists;
    private ArrayList<Service> services;
    private ArrayList<DateTimeRange> salonClosed;
    private String loginUsername;
    private String loginPassword;

    public Salon(){
        appointments = new ArrayList<Appointment>();
        stylists = new ArrayList<Stylist>();
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
    public Appointment findAppointment(String clientName) {
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
     */
    public void addAppointment(LocalDateTime dt, Stylist styl, Service ser, String cName, String cContact, String cPayment) {
        Appointment a = new Appointment(dt, styl, ser, cName, cContact, cPayment);
        int index = 0;
        while (index < appointments.size() && dt.isAfter(appointments.get(index).getDateTime())) {
            index++;
        }
        appointments.add(index, a);
    }

    /*
     * Prints a client-friendly, formatted output of an appointment.
     * 
     * @param a     the given appointment
     * @return      true if the appointment exists; otherwise,
     *              false
     */
    public boolean printApptDetails(Appointment a) {
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
    public void cancelAppointment(Appointment a) {
        LocalDateTime oneDayFromNow = LocalDateTime.now().plusHours(24);
        if (oneDayFromNow.isAfter(a.getDateTime())) {
            System.out.printf("\nYou will be charged a cancellation fee of $%.2f.\n", a.getService().getCost()/2);
        }
        appointments.remove(a);
        System.out.println("Your appointment has been successfully cancelled.");
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
        System.out.printf("%-2s | %-26s | %-16s | %-24s | %-16s | %-16s | %-16s\n", "ID", "Date/Time", "Service", "Stylist", "Client Name", "Client Contact", "Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        for(int i=0; i<appointments.size();i++) {
            System.out.printf("%-2s | ", i);
            appointments.get(i).printInfo();
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    /*
     * Prints a list of the current pending appointments
     */
    public void printPendingAppointments(){
        System.out.println("Pending Appointments");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-2s | %-26s | %-16s | %-24s | %-16s | %-16s\n", "ID", "Date/Time", "Service", "Stylist", "Client Name", "Client Contact");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for(int i=0; i<appointments.size();i++) {
            if (appointments.get(i).getStatus().equals("Pending")){
                System.out.printf("%-2s | ", i);
                appointments.get(i).printInfo();
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    /*
     * Adds the given range of days/times to the salon's list of days/times that it's closed.
     * This method will insert the new closure time in the list so that the list maintains chronological
     * order by start time.
     * 
     * This method will also cancel any appointments that were scheduled to take place during the new
     * closure time.
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
            Appointment a = appointments.get(i);
            DateTimeRange apptDuration = new DateTimeRange(a.getDateTime(), a.getDateTime().plusMinutes(a.getService().getLength()));
            if(apptDuration.conflictsWith(timesToBeClosed)) {
                appointments.remove(a);
            }
        }
    }

    /*
     * Calculates the expected revenue from the salon in a given time frame
     * 
     * @param startDate     the date to begin calculating the expected revenue, in the form "E, MMM dd"
     * @param endDate       the date to end calculating the expected revenue, in the form "E, MMM dd"
     */
    public float calculateRevenue(String startDate, String endDate) {
        float revenue = 0;
        Appointment a;
        DateTimeRange range = new DateTimeRange(startDate, endDate);
        for(int i=0; i<appointments.size(); i++) {
            a = appointments.get(i);
            if(a.getDateTime().isBefore(range.getEndOfRange()) && a.getDateTime().isAfter(range.getStartOfRange())) {
                revenue += a.getService().getCost();
            }
        }
        return revenue;
    }

    // GETTERS AND SETTERS

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments (ArrayList<Appointment> a) {
        appointments = a;
    }

    public ArrayList<Stylist> getStylists() {
        return stylists;
    }

    public void setStylists (ArrayList<Stylist> styls) {
        stylists = styls;
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

}
