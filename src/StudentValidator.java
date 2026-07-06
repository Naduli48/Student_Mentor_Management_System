// Responsible for validating student registration input data.
// Separated from Main to follow the single responsibility principle -
// validation logic is defined once and reused by both Main and tests.
public class StudentValidator {

    // Validates student registration fields.
    // Throws InvalidStudentDataException if any field is invalid.
    public static void validate(String id, String name, String email, int year)
            throws InvalidStudentDataException {

        if (id.isEmpty()) {
            throw new InvalidStudentDataException("Student ID cannot be empty.");
        }

        if (name.isEmpty()) {
            throw new InvalidStudentDataException("Name cannot be empty.");
        }

        if (email.isEmpty()) {
            throw new InvalidStudentDataException("Email cannot be empty.");
        }

        if (!email.contains("@")) {
            throw new InvalidStudentDataException("Email address is not valid.");
        }

        if (year < 1 || year > 7) {
            throw new InvalidStudentDataException("Year must be between 1 and 7.");
        }
    }
}