import java.sql.*;

public class StudentApp {

    // --- DB Config ---
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "5432";
    private static final String DB_NAME = "student_management";
    private static final String DB_USER = "postgres"; 
    private static final String DB_PASSWORD = "noob123"; 
    private static final String JDBC_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    /** Returns DB connection. */
    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("ERROR: Could not connect to the database.");
            System.err.println("Details: " + e.getMessage());
            return null;
        }
    }

    // --- READ Operation ---
    public static void getAllStudents() {
        System.out.println("\n--- READ: Displaying Students ---");
        String sql = "SELECT student_id, first_name, last_name, email, enrollment_date FROM students ORDER BY student_id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (conn == null) return;

            // Print header
            System.out.printf("| %-4s | %-12s | %-12s | %-30s | %-17s |%n", 
                                 "ID", "First Name", "Last Name", "Email", "Enrollment Date");
            System.out.println("----------------------------------------------------------------------------------");

            // Print data rows
            while (rs.next()) {
                System.out.printf("| %-4d | %-12s | %-12s | %-30s | %-17s |%n", 
                                     rs.getInt("student_id"), 
                                     rs.getString("first_name"), 
                                     rs.getString("last_name"), 
                                     rs.getString("email"), 
                                     rs.getDate("enrollment_date").toString());
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving : " + e.getMessage());
        }
    }

    // --- CREATE Operation ---
    public static void addStudent(String firstName, String lastName, String email, String enrollmentDate) {
        System.out.printf("\n--- CREATE: Adding %s %s ---%n", firstName, lastName);
        String sql = "INSERT students (first_name, last_name, email, enrollment_date)  (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (conn == null) return;

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(enrollmentDate));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("SUCCESS: New student added with ID: " + generatedKeys.getInt(1));
                    }
                }
            } else {
                System.out.println("ERROR: Failed to insert new student.");
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { 
                System.err.println("ERROR: Email already exists for " + firstName + ".");
            } else {
                System.err.println("ERROR: Could not add student: " + e.getMessage());
            }
        }
    }

    // --- UPDATE Operation ---
    public static void updateStudentEmail(int studentId, String newEmail) {
        System.out.printf("\n--- UPDATE: Changing ID %d email to %s ---%n", studentId, newEmail);
        String sql = "UPDATE  email = ? WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return;

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("SUCCESS: Student ID " + studentId + "'s email updated.");
            } else {
                System.out.println("NOTICE: No student found with ID " + studentId + ". No update performed.");
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                 System.err.println("ERROR: The email '" + newEmail + "' is already in use.");
            } else {
                 System.err.println("ERROR: Could not update email: " + e.getMessage());
            }
        }
    }

    // --- DELETE Operation ---
    public static void deleteStudent(int studentId) {
        System.out.printf("\n--- DELETE: Removing student ID %d ---%n", studentId);
        String sql = "DELETE WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return;

            pstmt.setInt(1, studentId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("SUCCESS: Student with ID " + studentId + " has been deleted.");
            } else {
                System.out.println("NOTICE: No student found with ID " + studentId + ".");
            }

        } catch (SQLException e) {
            System.err.println("ERROR: Could not delete student: " + e.getMessage());
        }
    }


    /** Main method . */
    public static void main(String[] args) {
        System.out.println("Welcome to the Student Management Application Demo.");
            
        //Initial 
        getAllStudents();

        // 2. CREATE
        addStudent("Alice", "Brown", "alice.brown@example.com", "2024-01-15");
        
        // READ 
        getAllStudents();

        int targetId = 4; 

        // 3. UPDATE
        updateStudentEmail(targetId, "alice.b@newmail.com");
        
        // READ 
        getAllStudents();
        
        // 4. DELETE
        deleteStudent(targetId);
        
        // READ 
        getAllStudents();
        
        System.out.println("\nDemo Complete.");
    }
}