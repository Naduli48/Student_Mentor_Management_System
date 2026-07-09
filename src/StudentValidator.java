public class StudentValidator {

    // Validates all student registration fields.
    // Throws InvalidStudentDataException if any field is invalid.
    public static void validate(String id, String name, String email,
                                String program, String preferences, int year)
            throws InvalidStudentDataException {

        // check all fields are completed
        if (id.isEmpty() || name.isEmpty() || email.isEmpty()
                || program.isEmpty() || preferences.isEmpty()) {
            throw new InvalidStudentDataException(
                    "Registration failed: All fields must be completed.");
        }

        if (!email.contains("@")) {
            throw new InvalidStudentDataException(
                    "Email address is not valid.");
        }

        if (year < 1 || year > 4) {
            throw new InvalidStudentDataException(
                    "Year must be between 1 and 4.");
        }
    }
}