import java.util.ArrayList;

public class Stylist extends Provider {

    public Stylist(){
        setServices(new ArrayList<Service>());
        setUnavailable(new ArrayList<DateTimeRange>());
        setName("");
        setRatings(0);
        setType("hairstylist");
    }

    public Stylist(String name, ArrayList<Service> specialties, ArrayList<DateTimeRange> unavail, float ratings) {
        setServices(specialties);
        setUnavailable(unavail);
        setName(name);
        setRatings(ratings);
        setType("hairstylist");
    }
}
