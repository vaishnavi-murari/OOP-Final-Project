import java.util.List;

public class Stylist {
    private String name;
    private int id;
    private List<Service> specialties;
    private Availability availability;
    private float ratings;

    public Stylist(String name, int id, List<Service> specialties, Availability availability, float ratings) {
        this.name = name;
        this.id = id;
        this.specialties = specialties;
        this.availability = availability;
        this.ratings = ratings;
    }


    public void scheduleAppointment(Details appointmentDetails) {
        System.out.println("Schedule an appointment for " + name + ": " + appointmentDetails.getDetail());
    }

    public void cancelAppointment(int appointmentId) {
        System.out.println("Cancel appointment with Appointment ID: " + appointmentId);
    }

    public void updateAvailability(Availability newAvailability) {
        this.availability = newAvailability;
        System.out.println("Updated availability for " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Service> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Service> specialties) {
        this.specialties = specialties;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }
}
