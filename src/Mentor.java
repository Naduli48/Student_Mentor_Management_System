public class Mentor extends User {
    private String expertise;
    private String availability;

    public Mentor(String id, String name, String email,
                  String expertise, String availability) {
        super(id, name, email);
        this.expertise = expertise;
        this.availability = availability;
    }

    public String getExpertise() {
        return expertise;
    }

    public String getAvailability() {
        return availability;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public void displayInfo() {
        System.out.println("Mentor ID    : " + getId());
        System.out.println("Name         : " + getName());
        System.out.println("Email        : " + getEmail());
        System.out.println("Expertise    : " + expertise);
        System.out.println("Availability : " + availability);
    }
}