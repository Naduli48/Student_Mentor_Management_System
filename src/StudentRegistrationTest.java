import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.sql.SQLException;

// Unit tests for the Student Mentor Management System - Student Registration use case.
// Tests cover domain logic, input validation, and database operations.
public class StudentRegistrationTest {

    private StudentRepository repository;
    private MentorshipProgram program;

    // Runs before each test - sets up a fresh repository and program instance
    @Before
    public void setUp() {
        repository = new StudentRepository();
        program = new MentorshipProgram("P001", "Test Programme");
    }

    // Runs after each test - cleans up any test data inserted into the database
    @After
    public void tearDown() throws SQLException {
        repository.delete("TEST001");
        repository.delete("TEST002");
    }

    // Test 1: Verify a Student object is created correctly with all fields
    @Test
    public void testStudentCreation() {
        Student student = new Student(
                "TEST001", "Naduli Kosgallana", "naduli@gmail.com",
                "BSc AI and Data Science", 2, "Machine Learning"
        );
        assertEquals("TEST001", student.getId());
        assertEquals("Naduli Kosgallana", student.getName());
        assertEquals("naduli@gmail.com", student.getEmail());
        assertEquals("BSc AI and Data Science", student.getProgram());
        assertEquals(2, student.getYear());
        assertEquals("Machine Learning", student.getPreferences());
    }

    // Test 2: Verify inheritance - Student is an instance of User
    @Test
    public void testStudentIsInstanceOfUser() {
        Student student = new Student(
                "TEST001", "Naduli Kosgallana", "naduli@gmail.com",
                "BSc AI and Data Science", 2, "Machine Learning"
        );
        assertTrue(student instanceof User);
    }

    // Test 3: Verify a student can be saved to the database and retrieved by ID
    @Test
    public void testSaveAndFindById() throws SQLException {
        Student student = new Student(
                "TEST001", "Naduli Kosgallana", "naduli@gmail.com",
                "BSc AI and Data Science", 2, "Machine Learning"
        );
        repository.save(student);
        Student found = repository.findById("TEST001");
        assertNotNull(found);
        assertEquals("TEST001", found.getId());
        assertEquals("Naduli Kosgallana", found.getName());
        assertEquals("naduli@gmail.com", found.getEmail());
    }

    // Test 4: Verify findById returns null when student does not exist
    @Test
    public void testFindByIdReturnsNullWhenNotFound() throws SQLException {
        Student result = repository.findById("NONEXISTENT");
        assertNull(result);
    }

    // Test 5: Verify a student can be deleted from the database
    @Test
    public void testDeleteStudent() throws SQLException {
        Student student = new Student(
                "TEST001", "Naduli Kosgallana", "naduli@gmail.com",
                "BSc AI and Data Science", 2, "Machine Learning"
        );
        repository.save(student);
        repository.delete("TEST001");
        Student result = repository.findById("TEST001");
        assertNull(result);
    }

    // Test 6: Verify MentorshipProgram correctly detects a registered student
    @Test
    public void testIsStudentRegistered() {
        Student student = new Student(
                "TEST001", "Naduli Kosgallana", "naduli@gmail.com",
                "BSc AI and Data Science", 2, "Machine Learning"
        );
        program.addStudent(student);
        assertTrue(program.isStudentRegistered("TEST001"));
        assertFalse(program.isStudentRegistered("TEST002"));
    }

    // Test 7: Verify InvalidStudentDataException thrown for empty ID
    @Test(expected = InvalidStudentDataException.class)
    public void testValidationThrowsExceptionForEmptyId()
            throws InvalidStudentDataException {
        StudentValidator.validate("", "Naduli", "naduli@gmail.com", 2);
    }

    // Test 8: Verify InvalidStudentDataException thrown for empty name
    @Test(expected = InvalidStudentDataException.class)
    public void testValidationThrowsExceptionForEmptyName()
            throws InvalidStudentDataException {
        StudentValidator.validate("TEST001", "", "naduli@gmail.com", 2);
    }

    // Test 9: Verify InvalidStudentDataException thrown for invalid email
    @Test(expected = InvalidStudentDataException.class)
    public void testValidationThrowsExceptionForInvalidEmail()
            throws InvalidStudentDataException {
        StudentValidator.validate("TEST001", "Naduli", "notanemail", 2);
    }

    // Test 10: Verify DuplicateRegistrationException thrown for duplicate ID
    // Simulates the duplicate check performed in Main.registerStudent()
    @Test(expected = DuplicateRegistrationException.class)
    public void testDuplicateRegistrationThrowsException()
            throws SQLException, DuplicateRegistrationException {
        Student student = new Student(
                "TEST001", "Naduli Kosgallana", "naduli@gmail.com",
                "BSc AI and Data Science", 2, "Machine Learning"
        );
        repository.save(student);

        // simulates the duplicate check performed in Main.registerStudent()
        if (repository.findById("TEST001") != null) {
            throw new DuplicateRegistrationException(
                    "A student with ID TEST001 is already registered.");
        }
    }

    // Test 11: Verify findAll returns all saved students
    @Test
    public void testFindAllReturnsStudents() throws SQLException {
        Student s1 = new Student("TEST001", "Naduli", "naduli@gmail.com", "BSc AI", 2, "ML");
        Student s2 = new Student("TEST002", "Kasun", "kasun@gmail.com", "BSc CS", 1, "Web");
        repository.save(s1);
        repository.save(s2);
        java.util.List<Student> all = repository.findAll();
        assertTrue(all.size() >= 2);
    }

    // Test 12: Verify InvalidStudentDataException thrown for year below 1
    @Test(expected = InvalidStudentDataException.class)
    public void testValidationThrowsExceptionForInvalidYearBelow()
            throws InvalidStudentDataException {
        StudentValidator.validate("TEST001", "Naduli", "naduli@gmail.com", 0);
    }

    // Test 13: Verify InvalidStudentDataException thrown for year above 4
    @Test(expected = InvalidStudentDataException.class)
    public void testValidationThrowsExceptionForInvalidYearAbove()
            throws InvalidStudentDataException {
        StudentValidator.validate("TEST001", "Naduli", "naduli@gmail.com", 5);
    }
}