package org.example.AcademicOffice;

import org.example.ConnectDB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.List;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class AcademicOffice extends ConnectDB {
    static Connection conn = connectDB();
    static Statement stmt;
    public String username;

    public boolean status = false;

    public boolean login(String username, String password) {
        String query = "SELECT * FROM admins WHERE username=? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                this.username = username;
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
                String time = dtf.format(now);
                String logMessage = "Academic Office " + username + " logged in on " + time + "\n";
                Files.write(Paths.get("src/main/resources/Transcript_Login_Logout.txt"), logMessage.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                System.out.println("Logged in successfully\n");
                status = true;
                return true;
            } else {
                System.err.println("Wrong Credentials!\n");
                return false;
            }
        } catch (IOException | SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }

    public boolean logout() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);
            String logMessage = "Academic Office " + username + " logged out on " + time + "\n";
            Files.write(Paths.get("src/main/resources/Transcript_Login_Logout.txt"), logMessage.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            System.out.println("Logged out successfully\n");
            status = false;
            return true;
        } catch (IOException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }

    public String startSemester(String academicYear, String semester) {
        String response;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM semester;");
            if (rs.next()) {
                response = "A semester is currently running";
                System.out.println(response);
                return response;
            }
        } catch (SQLException e) {
            response = e.getMessage();
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return response;
        }

        String catalogue_query = "CREATE TABLE course_catalogue (" +
                "course_id VARCHAR(20) PRIMARY KEY," +
                "FOREIGN KEY (course_id) REFERENCES courses (id)" +
                ")";
        String offering_query = "CREATE TABLE course_offering (" +
                "course_id VARCHAR(20) PRIMARY KEY," +
                "cgpa_limit INTEGER," +
                "faculty_id VARCHAR(20)," +
                "FOREIGN KEY (course_id) REFERENCES course_catalogue (course_id)," +
                "FOREIGN KEY (faculty_id) REFERENCES faculties (id)" +
                ")";
        String registration_query = "CREATE TABLE registration_status (" +
                "course_id VARCHAR(20)," +
                "student_id VARCHAR(20)," +
                "faculty_id VARCHAR(20)," +
                "status VARCHAR(100)," +
                "FOREIGN KEY (course_id) REFERENCES course_offering (course_id)," +
                "FOREIGN KEY (student_id) REFERENCES students (id)," +
                "FOREIGN KEY (faculty_id) REFERENCES faculties (id)" +
                ")";
        String student_query = "UPDATE students set credits=0;";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(catalogue_query);
            stmt.execute(offering_query);
            stmt.execute(registration_query);
            stmt.execute(student_query);

            String query = "INSERT INTO semester (academic_year, semester) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, academicYear);
            pstmt.setString(2, semester);
            pstmt.executeUpdate();
            response = academicYear + "-" + semester + " semester started successfully";
            System.out.println(response);
            return response;
        } catch (SQLException e) {
            response = e.getMessage();
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return response;
        }
    }

    public String viewSemester() {
        String academicYear;
        String semester;
        String response;

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT academic_year, semester FROM semester");
            if (rs.next()) {
                academicYear = rs.getString("academic_year");
                semester = rs.getString("semester");
                System.out.println(academicYear + "-" + semester + " semester is running");
                return academicYear + "-" + semester + " semester is running";
            } else {
                response = "No semester is running.";
                System.out.println(response);
                return response;
            }

        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return e.getMessage();
        }
    }

    public boolean endSemester() {
        String academic_year = "";
        String sem = "";
        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM semester;");
            if (rs.next()) {
                academic_year = rs.getString("academic_year");
                sem = rs.getString("semester");
            } else {
                System.err.println("No semester is running.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        try {
            String s1 = "DROP TABLE IF EXISTS course_catalogue;";
            String s2 = "DROP TABLE IF EXISTS course_offering;";
            String s3 = "DROP TABLE IF EXISTS registration_status;";
            String s4 = "DELETE FROM semester;";
            stmt = conn.createStatement();
            stmt.execute(s3);
            stmt.execute(s2);
            stmt.execute(s1);
            stmt.execute(s4);
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        System.out.println(academic_year + "-" + sem + " semester ended successfully");
        return true;
    }

    public boolean addBatch(String batch_id, String year, String dep_id) {
        try {
            String query = "INSERT INTO batch(id,year,dep_id) VALUES (?,?,?);";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, batch_id);
            pstmt.setString(2, year);
            pstmt.setString(3, dep_id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        return true;
    }

    public boolean viewBatch() {
        String query = "SELECT id, year, dep_id FROM batch;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            StringBuilder responseQuery = new StringBuilder();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        responseQuery.append("\n");
                    }
                    responseQuery.append(rsmd.getColumnName(i));
                    responseQuery.append(": ");
                    responseQuery.append(rs.getString(i));
                }
                responseQuery.append("\n");
            }
            System.out.println(responseQuery);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        return true;
    }

    public boolean deleteBatch(String batch_id) {
        String query = "DELETE FROM batch WHERE id = ?;";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, batch_id);
            int rowsAffected = ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }


    public boolean viewCourses() {
        String query = "SELECT id, name, dep_id, lec, tut, pra, credits FROM courses;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            StringBuilder responseQuery = new StringBuilder();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) {
                        responseQuery.append("\n");
                    }
                    responseQuery.append(rsmd.getColumnName(i));
                    responseQuery.append(": ");
                    responseQuery.append(rs.getString(i));
                }
                responseQuery.append("\n");
            }
            System.out.println(responseQuery);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        return true;
    }

    public boolean addCourse(String courseId, String courseName, String depId, String lec, String tut, String pra, String credits, List<String> prereqIds) {
        String insertCourseQuery = "INSERT INTO courses(id, name, dep_id, lec, tut, pra, credits) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement stmt = conn.prepareStatement(insertCourseQuery)) {
            stmt.setString(1, courseId);
            stmt.setString(2, courseName);
            stmt.setString(3, depId);
            stmt.setInt(4, Integer.parseInt(lec));
            stmt.setInt(5, Integer.parseInt(tut));
            stmt.setInt(6, Integer.parseInt(pra));
            stmt.setInt(7, Integer.parseInt(credits));
            stmt.executeUpdate();

            String insertPrereqQuery = "INSERT INTO prereq(course_id, prereq_id) VALUES (?, ?);";

            try (PreparedStatement prereqStmt = conn.prepareStatement(insertPrereqQuery)) {
                for (String prereqId : prereqIds) {
                    prereqStmt.setString(1, courseId);
                    prereqStmt.setString(2, prereqId);
                    prereqStmt.executeUpdate();
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }


    public boolean deleteCourse(String courseId) {
        String deletePrereqQuery = "DELETE FROM prereq WHERE course_id=? OR prereq_id=?;";
        String deleteCourseQuery = "DELETE FROM courses WHERE id=?;";

        try (PreparedStatement ps1 = conn.prepareStatement(deletePrereqQuery);
             PreparedStatement ps2 = conn.prepareStatement(deleteCourseQuery)) {

            ps1.setString(1, courseId);
            ps1.setString(2, courseId);
            ps1.executeUpdate();

            ps2.setString(1, courseId);
            ps2.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }


    public boolean showGrades() {
        String query = "SELECT * FROM grades;";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            StringBuilder responseBuilder = new StringBuilder();

            while (rs.next()) {
                for (int i = 1; i <= numColumns; i++) {
                    if (i == 1) {
                        responseBuilder.append("Student ID: ");
                    } else if (i == 2) {
                        responseBuilder.append("Faculty ID: ");
                    } else if (i == 3) {
                        responseBuilder.append("Course ID: ");
                    } else if (i == 4) {
                        responseBuilder.append("Grade: ");
                    } else if (i == 5) {
                        responseBuilder.append("Semester: ");
                    } else if (i == 6) {
                        responseBuilder.append("Academic Year: ");
                    }
                    String columnValue = rs.getString(i);
                    responseBuilder.append(columnValue).append("\n");
                }
                responseBuilder.append("\n");
            }
            String responseQuery = responseBuilder.toString().trim();
            if (responseQuery.isEmpty()) {
                System.out.println("No grades found.");
            } else {
                System.out.println(responseQuery);
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        return true;
    }


    public boolean deleteGrades() {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM grades;");
            pstmt.executeUpdate();
            System.out.println("Grades deleted successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }

    public boolean generateTranscript(String studentId) {
        String query = "SELECT c.id, c.name, c.credits, g.faculty_id, g.grade, g.academic_year, g.semester FROM grades g JOIN courses c ON g.course_id = c.id WHERE g.student_id = ?";
        StringBuilder transcriptBuilder = new StringBuilder();

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    switch (i) {
                        case 1 -> transcriptBuilder.append("Course ID: ");
                        case 2 -> transcriptBuilder.append("Course Name: ");
                        case 3 -> transcriptBuilder.append("Credits earned: ");
                        case 4 -> transcriptBuilder.append("Faculty ID: ");
                        case 5 -> transcriptBuilder.append("Grade: ");
                        case 6 -> transcriptBuilder.append("Academic Year: ");
                        case 7 -> transcriptBuilder.append("Semester: ");
                    }
                    String columnValue = resultSet.getString(i);
                    transcriptBuilder.append(columnValue).append("\n");
                }
                transcriptBuilder.append("\n");
            }
            try (BufferedWriter out = new BufferedWriter(new FileWriter("src/main/resources/Transcript_Student/Transcript_" + studentId + ".txt"))) {
                out.write(transcriptBuilder.toString());
            } catch (IOException e) {
                System.err.println("Exception occurred: " + e.getMessage() + '\n');
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        return true;
    }

    public boolean addUser(String role, List<String> data) {
        String id = null;
        if (role.equals("1")) {
            try {
                String insertQuery = "INSERT INTO students(id, name, batch_id, email, password, phone_number, credits) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, data.get(0));
                insertStmt.setString(2, data.get(1));
                insertStmt.setString(3, data.get(2));
                insertStmt.setString(4, data.get(3));
                insertStmt.setString(5, data.get(4));
                insertStmt.setString(6, data.get(5));
                insertStmt.setInt(7, 0);
                insertStmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Exception occurred: " + e.getMessage() + '\n');
                return false;
            }
        } else if (role.equals("2")) {
            try {
                String insertQuery = "INSERT INTO faculties(id, name, email, dep_id, password, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, data.get(0));
                insertStmt.setString(2, data.get(1));
                insertStmt.setString(3, data.get(2));
                insertStmt.setString(4, data.get(3));
                insertStmt.setString(5, data.get(4));
                insertStmt.setString(6, data.get(5));
                insertStmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.err.println("Exception occurred: " + e.getMessage() + '\n');
                return false;
            }
        }
        return true;
    }


    public boolean viewUsers(String c) {

        String table = null;
        String idColumn = null;
        String nameColumn = null;
        String otherColumn1 = null;
        String otherColumn2 = null;
        String otherColumn3 = null;

        if (c.equals("1")) {
            table = "students";
            idColumn = "student_id";
            nameColumn = "name";
            otherColumn1 = "batch_id";
            otherColumn2 = "email";
            otherColumn3 = "phone_number";
        } else if (c.equals("2")) {
            table = "faculties";
            idColumn = "faculty_id";
            nameColumn = "name";
            otherColumn1 = "email";
            otherColumn2 = "dep_id";
            otherColumn3 = "phone_number";
        }

        String query = "SELECT * FROM " + table;

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            StringBuilder responseQuery = new StringBuilder();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {

                    if (i == 1)
                        responseQuery.append(idColumn).append(": ");
                    if (i == 2)
                        responseQuery.append(nameColumn).append(": ");
                    if (i == 3)
                        responseQuery.append(otherColumn1).append(": ");
                    if (i == 4)
                        responseQuery.append(otherColumn2).append(": ");
                    if (i == 5) continue;
                    if (i == 6)
                        responseQuery.append(otherColumn3).append(": ");
                    if (i == 7)
                        responseQuery.append("credits").append(": ");
                    String columnValue = rs.getString(i);
                    responseQuery.append(columnValue).append("\n");
                }
                responseQuery.append("\n\n");
            }
            System.out.println(responseQuery);
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }

        return true;
    }


    public boolean deleteUser(String role, String id) {
        String query = null;
        if (role.equals("1")) {
            query = "DELETE FROM students WHERE id = ?";
        } else if (role.equals("2")) {
            query = "DELETE FROM faculties WHERE id = ?";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }

    public boolean addCourseToCurriculum(String courseId, String courseType, String batchId) {
        String query = "INSERT INTO ug_curriculum(course_id, batch_id, course_type) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, courseId);
            ps.setString(2, batchId);
            ps.setString(3, courseType);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }

    public boolean deleteCourseFromCurriculum(String courseId, String batchId) {
        String query = "DELETE FROM ug_curriculum WHERE course_id=? AND batch_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, courseId);
            ps.setString(2, batchId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }


    public boolean addCourseToCourseCatalogue(String courseId) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM semester");
            if (!rs.next()) {
                System.out.println("Semester is not yet started");
                return false;
            }

            String query = "INSERT INTO course_catalogue (course_id) VALUES ('" + courseId + "')";
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }

    }

    public boolean deleteCourseFromCourseCatalogue(String courseId) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM course_catalogue WHERE course_id = ?");
            pstmt.setString(1, courseId);
            int rows = pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }
}