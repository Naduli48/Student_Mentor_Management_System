// Custom checked exception thrown when a student attempts to register with an ID that already exists in the mentorship programme.
public class DuplicateRegistrationException extends Exception {
    public DuplicateRegistrationException(String message) {
        super(message);
    }
}
