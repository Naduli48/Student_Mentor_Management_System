import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;
import java.util.logging.Logger;

// Entry point of the Student Mentor Management System.
// Provides a console-based menu for registering, viewing, and removing students.
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerUtil.getLogger("SMMSLogger");

    public static void main(String[] args) {

        MentorshipProgram program = new MentorshipProgram("P001", "University Mentorship Programme 2026");
        StudentRepository repository = new StudentRepository();

        while (true) {

            System.out.println("\nSTUDENT MENTOR MANAGEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Register a student");
            System.out.println("2. View all registered students");
            System.out.println("3. Remove a student");
            System.out.println("4. Exit");
            System.out.print("\nEnter option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerStudent(program, repository);
                    break;

                case "2":
                    viewAllStudents(repository);
                    break;

                case "3":
                    removeStudent(program, repository);
                    break;

                case "4":
                    System.out.println("Thank you for using SMMS.");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Handles the student registration process, including input validation and duplicate checking before saving to the database.
    private static void registerStudent(MentorshipProgram program, StudentRepository repository) {
        try {
            System.out.print("Enter student ID: ");
            String id = scanner.nextLine().trim();

            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter program: ");
            String courseProgram = scanner.nextLine().trim();

            // check program field separately since it is not in StudentValidator
            if (courseProgram.isEmpty()) {
                throw new InvalidStudentDataException("Program cannot be empty.");
            }

            System.out.print("Enter year of study (1-4): ");
            String yearInput = scanner.nextLine().trim();

            // check year field is not blank before parsing
            if (yearInput.isEmpty()) {
                throw new InvalidStudentDataException("Year of study cannot be empty.");
            }

            int year = Integer.parseInt(yearInput);

            System.out.print("Enter mentorship preferences: ");
            String preferences = scanner.nextLine().trim();

            // check preferences field separately
            if (preferences.isEmpty()) {
                throw new InvalidStudentDataException("Mentorship preferences cannot be empty.");
            }

            // validate all core fields using StudentValidator
            StudentValidator.validate(id, name, email, year);

            // check for duplicate registration against database
            if (repository.findById(id) != null) {
                throw new DuplicateRegistrationException("A student with ID " + id + " is already registered.");
            }

            Student student = new Student(id, name, email, courseProgram, year, preferences);

            // add to in-memory program list
            program.addStudent(student);

            // persist to database
            repository.save(student);

            System.out.println("Student registered successfully.");
            logger.info("Student registered: " + id);

        } catch (DuplicateRegistrationException e) {
            System.out.println("Registration failed: " + e.getMessage());
            logger.warning("Registration failed: " + e.getMessage());

        } catch (InvalidStudentDataException e) {
            System.out.println("Registration failed: " + e.getMessage());
            logger.warning("Registration failed: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Registration failed: Year of study must be a valid number.");
            logger.warning("Invalid year input during registration.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            logger.warning("Database error during registration: " + e.getMessage());
        }
    }

    // Retrieves and displays all students currently saved in the database.
    private static void viewAllStudents(StudentRepository repository) {
        try {
            List<Student> students = repository.findAll();

            if (students.isEmpty()) {
                System.out.println("No students registered yet.");
                return;
            }

            System.out.println("\nREGISTERED STUDENTS");
            System.out.println("====================");
            for (Student s : students) {
                s.displayInfo();
                System.out.println("--------------------");
            }

            logger.info("Viewed all registered students.");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            logger.warning("Database error while viewing students: " + e.getMessage());
        }
    }

    // Removes a student from both the in-memory programme list and the database.
    private static void removeStudent(MentorshipProgram program, StudentRepository repository) {
        try {
            System.out.print("Enter student ID to remove: ");
            String id = scanner.nextLine().trim();

            // check if student exists in database before attempting removal
            Student existing = repository.findById(id);

            if (existing == null) {
                System.out.println("No student found with ID: " + id + ". Nothing was removed.");
                logger.warning("Attempted to remove non-existent student: " + id);
                return;
            }

            program.removeStudent(id);
            repository.delete(id);

            System.out.println("Student removed successfully.");
            logger.info("Student removed: " + id);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            logger.warning("Database error while removing student: " + e.getMessage());
        }
    }
}