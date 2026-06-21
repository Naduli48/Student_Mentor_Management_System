// Custom checked exception thrown when student registration data is missing, empty, or in an invalid format (e.g. blank name, invalid email).
public class InvalidStudentDataException extends RuntimeException {
    public InvalidStudentDataException(String message) {
        super(message);
    }
}
