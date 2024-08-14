import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/*
 * Name: Vaishnavi Murari
 * Date: August 13, 2024
 */
public class DateTimeRange implements Serializable{
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public DateTimeRange(){
    }

    public DateTimeRange (LocalDateTime start, LocalDateTime end) {
        startDateTime = start;
        endDateTime = end;
    }

    public DateTimeRange (String start, String end) {
        try {
            DateTimeFormatter form = DateTimeFormatter.ofPattern("E, MMM dd, yyyy h:mm a");
            startDateTime = LocalDateTime.parse(start, form);
            endDateTime = LocalDateTime.parse(end, form);
        } catch (Exception e) {
            System.out.println("Sorry, could not read the date. Please be sure to enter a valid date in the form: Wed, Aug 14, 2024 2:00 PM");
        }
    }

    /*
     * Determines if this range of time shares any overlap with another range of time
     * @return  true if the two time ranges overlap; otherwise,
     *          false
     */
    public boolean conflictsWith(DateTimeRange other) {
        if(!(endDateTime.isAfter(other.getStartOfRange())) || !(other.getEndOfRange().isAfter(startDateTime))) {
            return false;
        }
        return true;
    }

    /*
     * Determines if this range of time starts after another range of time
     * @return  true if this time range starts later; otherwise,
     *          false
     */
    public boolean startsAfter (DateTimeRange other) {
        if(startDateTime.isAfter(other.getStartOfRange())) {
            return true;
        }
        return false;
    }

    /*
     * Overrides the Object toString() method
     * @return  a String of the format "E, MMM dd h:mm a - E, MMM dd h:mm a"
     */
    public String toString(){
        DateTimeFormatter form = DateTimeFormatter.ofPattern("E, MMM dd, yyyy h:mm a");
        String start = startDateTime.format(form);
        String end = endDateTime.format(form);
        return start + " - " + end;
    }

    /*
     * Obtains a String of the start of the range
     * @return  a String of the format "E, MMM dd, yyyy h:mm a"
     */
    public String startToString() {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("E, MMM dd, yyyy h:mm a");
        return startDateTime.format(form);
    }

    /*
     * Obtains a String of the end of the range
     * @return  a String of the format "E, MMM dd, yyyy h:mm a"
     */
    public String endToString() {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("E, MMM dd, yyyy h:mm a");
        return endDateTime.format(form);
    }

    // GETTERS AND SETTERS

    public LocalDateTime getStartOfRange(){
        return startDateTime;
    }

    public void setStartOfRange (LocalDateTime start) {
        startDateTime = start;
    }

    public LocalDateTime getEndOfRange(){
        return endDateTime;
    }

    public void setEndOfRange(LocalDateTime end) {
        endDateTime = end;
    }
}
