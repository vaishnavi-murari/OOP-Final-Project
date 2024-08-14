import java.time.format.DateTimeFormatter;

/*
 * Last Updated: August 13, 2024
 */
public class HairAppointment extends BeautyAppointment {

    public HairAppointment(DateTimeRange dt, Stylist styl, Service ser, String cName, String cContact, String cPayment) {
        setDateTime(dt);
        setProvider(styl);
        setService(ser);
        setClientName(cName);
        setClientContact(cContact);
        setClientPayment(cPayment);
        setStatus("Pending");
    }

    /*
     * Prints a client-friendly overview of the appointment details.
     */
    public void printDetails(){
        Service service = getService();
        DateTimeFormatter dayForm = DateTimeFormatter.ofPattern("E, MMM dd, yyyy");
        DateTimeFormatter timeForm = DateTimeFormatter.ofPattern("h:mm a");
        String apptDate = getDateTime().getStartOfRange().format(dayForm);
        String apptTime = getDateTime().getStartOfRange().format(timeForm);
        
        System.out.println("You have a hair appointment on " + apptDate + " at " + apptTime + ".");
        System.out.println("Service: " + service.getName() + " with " + getProvider().getName());
        System.out.println("Service Description: " + service.getDescription());
        System.out.printf("Estimated Cost: $%.2f\n", service.getPrice());
        if(service.getDuration() < 60) {
            System.out.println("This appointment will take " + service.getDuration() + " minutes.");
        }
        else if (service.getDuration() == 60) {
            System.out.println("This appointment will take 1 hour.");
        }
        else {
            System.out.println("This appointment will take " + service.getDuration()/60 + " hours.");
        }
    }
}
