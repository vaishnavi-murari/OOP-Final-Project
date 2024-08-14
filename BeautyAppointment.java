import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/*
 * Last Updated: August 13, 2024
 */
public abstract class BeautyAppointment implements Serializable{
    private DateTimeRange dateTime;
    private Service service;
    private Provider prov;
    private String clientName;
    private String clientContact;
    private String clientPayment;
    private String status;

    public abstract void printDetails();

    /*
     * Prints out the formatted info of the beauty appointment.
     */
    public void printInfo() {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("E, MMM dd, yyyy h:mm a");
        String apptDateTime = dateTime.getStartOfRange().format(form);
        System.out.printf("%-26s | %-14s | %-16s | %-20s | %-20s | %-12s\n", apptDateTime, getService().getName(), getProvider().getName(), getClientName(), getClientContact(), getStatus());
    }

    // GETTERS AND SETTERS

    public DateTimeRange getDateTime(){
        return dateTime;
    }

    public void setDateTime(DateTimeRange dt) {
        dateTime = dt;
    }

    public Provider getProvider() {
        return prov;
    }

    public void setProvider(Provider p) {
        prov = p;
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