import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/*
 * Name: Vaishnavi Murari
 * Date: August 11, 2024
 */
public class Appointment implements Serializable{
    private LocalDateTime dateTime;
    private Stylist stylist;
    private Service service;
    private String clientName;
    private String clientContact;
    private String clientPayment;
    private String status;

    public Appointment(LocalDateTime dt, Stylist styl, Service ser, String cName, String cContact, String cPayment) {
        dateTime = dt;
        stylist = styl;
        service = ser;
        clientName = cName;
        clientContact = cContact;
        clientPayment = cPayment;
        status = "Pending";
    }

    /*
     * Prints a client-friendly overview of the appointment details.
     */
    public void printDetails(){
        DateTimeFormatter dayForm = DateTimeFormatter.ofPattern("E, MMM dd");
        DateTimeFormatter timeForm = DateTimeFormatter.ofPattern("HH:mm a");
        String apptDate = dateTime.format(dayForm);
        String apptTime = dateTime.format(timeForm);
        
        System.out.println("You have an appointment on " + apptDate + " at " + apptTime + ".");
        System.out.println("Service: " + service.getName() + " with " + stylist.getName());
        System.out.println("Service Description: " + service.getDescription());
        System.out.printf("Estimated Cost: $%.2f\n", service.getCost());
        if(service.getLength() < 60) {
            System.out.println("This appointment will take " + service.getLength() + " minutes.");
        }
        else if (service.getLength() == 60) {
            System.out.println("This appointment will take 1 hour.");
        }
        else {
            System.out.println("This appointment will take " + service.getLength()/60 + " hours.");
        }
    }

    /*
     * Prints out the formatted info of the appointment.
     */
    public void printInfo() {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("E, MMM dd HH:mm a");
        String apptDateTime = dateTime.format(form);
        System.out.printf("%-18s | %-20s | %-24s | %-16s | %-16s | %-16s\n", apptDateTime, service.getName(), stylist.getName(), clientName, clientContact, status);
    }

    // GETTERS AND SETTERS

    public LocalDateTime getDateTime(){
        return dateTime;
    }

    public void setDateTime(LocalDateTime dt) {
        dateTime = dt;
    }

    public Stylist getStylist(){
        return stylist;
    }

    public void setStylist(Stylist styl) {
        stylist = styl;
    }

    public Service getService(){
        return service;
    }

    public void setService(Service ser) {
        service = ser;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus (String newApptStatus) {
        status = newApptStatus;
    }

    public String getClientName(){
        return clientName;
    }

    public void setClientName(String cName) {
        clientName = cName;
    }

    public String getClientContact(){
        return clientContact;
    }

    public void setClientContact(String cContact) {
        clientContact = cContact;
    }

    public String getClientPayment(){
        return clientPayment;
    }

    public void setClientPayment(String cPayment){
        clientPayment = cPayment;
    }
}