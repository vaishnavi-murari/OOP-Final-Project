import java.util.ArrayList;
import java.io.Serializable;

/*
 * Last Updated: August 13, 2024
 */
public abstract class Provider implements Serializable{
    private ArrayList<Service> servicesOffered;
    private ArrayList<DateTimeRange> unavailable;
    private String name;
    private float ratings;
    private String type;

    /*
     * Blocks off a range of time in the provider's schedule so they're unavailable.
     * This can either be due to a scheduled appointment or a salon closure.
     */
    public void addToSchedule(DateTimeRange appt) {
        int index = 0;
        while (index < unavailable.size() && appt.startsAfter(unavailable.get(index))) {
            index++;
        }
        unavailable.add(index, appt);
    }

    /*
     * Frees up a range of time in the provider's schedule so they're available.
     */
    public void removeFromSchedule(DateTimeRange appt) {
        unavailable.remove(appt);
    }

    // GETTERS AND SETTERS

    public ArrayList<Service> getServices(){
        return servicesOffered;
    }

    public void setServices (ArrayList<Service> ser) {
        servicesOffered = ser;
    }

    public ArrayList<DateTimeRange> getUnavailable(){
        return unavailable;
    }

    public void setUnavailable(ArrayList<DateTimeRange> unavail) {
        unavailable = unavail;
    }

    public String getName(){
        return name;
    }

    public void setName (String nm) {
        name = nm;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float r) {
        ratings = r;
    }

    public String getType() {
        return type;
    }

    public void setType(String t) {
        type = t;
    }
}
