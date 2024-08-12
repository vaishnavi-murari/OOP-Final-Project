import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/*
 * Name: Vaishnavi Murari
 * Date: August 12, 2024
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
        DateTimeFormatter format = DateTimeFormatter.ofPattern("E, MMM dd, yyyy h:mm a");
        startDateTime = LocalDateTime.parse(start, format);
        endDateTime = LocalDateTime.parse(end, format);
    }

    /*
     * Determines if this range of time shares any overlap with another range of time
     * @return  true if the two time ranges overlap; otherwise,
     *          false
     */
    public boolean conflictsWith(DateTimeRange other) {
        if(startDateTime.isBefore(other.getEndOfRange()) || endDateTime.isAfter(other.getStartOfRange())) {
            return true;
        }
        return false;
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
     * @return  a String of the format "E, MMM dd, yyyy h:mm a - E, MMM dd, yyyy h:mm a"
     */
    public String toString(){
        DateTimeFormatter form = DateTimeFormatter.ofPattern("E, MMM dd, yyyy h:mm a");
        String start = startDateTime.format(form);
        String end = endDateTime.format(form);
        return start + " - " + end;
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