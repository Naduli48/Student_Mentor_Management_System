import java.util.ArrayList;
import java.util.List;

public class MentorshipProgram {

    private String programId;
    private String programName;
    private List<Student> students;

    public MentorshipProgram(String programId, String programName) {
        this.programId = programId;
        this.programName = programName;
        this.students = new ArrayList<>(); // initialise empty student list
    }

    public String getProgramId() {
        return programId;
    }

    public String getProgramName() {
        return programName;
    }

    // Returns the full list of registered students
    public List<Student> getStudents() {
        return students;
    }

    // Adds a student to the programme after successful registration
    public void addStudent(Student student) {
        students.add(student);
    }

    // Removes a student from the programme by their ID
    public void removeStudent(String studentId) {
        Student toRemove = null;

        // loop through students to find the matching ID
        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                toRemove = s;
                break;
            }
        }

        // remove the student if found
        if (toRemove != null) {
            students.remove(toRemove);
        }
    }

    // Checks whether a student with the given ID is already registered
    // Used to prevent duplicate registrations
    public boolean isStudentRegistered(String studentId) {
        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                return true;
            }
        }
        return false;
    }

    // Displays all currently registered students in the programme
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students currently registered.");
            return;
        }
        System.out.println("\nRegistered Students in " + programName + ":");
        System.out.println("=".repeat(40));
        for (Student s : students) {
            s.displayInfo();
            System.out.println("-".repeat(40));
        }
    }
}