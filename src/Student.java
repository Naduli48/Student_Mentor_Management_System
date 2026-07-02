public class Student extends User {
    private String program;
    private int year;
    private String preferences;

    public Student(String id, String name, String email,
                   String program, int year, String preferences) {
        super(id, name, email);
        this.program = program;
        this.year = year;
        this.preferences = preferences;
    }

    public String getProgram() { return program; }
    public int getYear() { return year; }
    public String getPreferences() { return preferences; }

    public void setProgram(String program) { this.program = program; }
    public void setYear(int year) { this.year = year; }
    public void setPreferences(String preferences) { this.preferences = preferences; }

    @Override
    public void displayInfo() {
        System.out.println("Student ID   : " + getId());
        System.out.println("Name         : " + getName());
        System.out.println("Email        : " + getEmail());
        System.out.println("Program      : " + program);
        System.out.println("Year         : " + year);
        System.out.println("Preferences  : " + preferences);
    }
}