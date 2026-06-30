import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Handles all database operations for Student records.
// Keeps SQL/JDBC code separate from the Student domain class itself, following the separation of concerns principle.
public class StudentRepository {

    // Constructor - ensures the students table exists when the repository is created
    public StudentRepository() {
        createTableIfNotExists();
    }

    // Creates the students table in the database if it does not already exist.
    // Runs once when the repository is first used.
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "program TEXT," +
                "year INTEGER," +
                "preferences TEXT" +
                ")";

        try (Connection conn = DBConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Failed to create students table: " + e.getMessage());
        }
    }

    // Inserts a new student record into the database.
    // Uses PreparedStatement to safely insert values and avoid SQL injection.
    public void save(Student student) throws SQLException {
        String sql = "INSERT INTO students (id, name, email, program, year, preferences) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getProgram());
            ps.setInt(5, student.getYear());
            ps.setString(6, student.getPreferences());

            ps.executeUpdate();
        }
    }

    // Retrieves a student by their ID. Returns null if not found.
    public Student findById(String id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToStudent(rs);
            }
            return null;
        }
    }

    // Retrieves all students currently stored in the database.
    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        }
        return students;
    }

    // Deletes a student record from the database by their ID.
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    // Helper method to convert a single database row (ResultSet) into a Student object.
    // Avoids repeating this conversion logic in every query method above.
    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("program"),
                rs.getInt("year"),
                rs.getString("preferences")
        );
    }
}