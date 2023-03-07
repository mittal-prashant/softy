package org.example.Faculty;

import org.example.ConnectDB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Faculty extends ConnectDB {
    static Connection conn = connectDB();
    static Statement stmt = null;
    public String user_id = "";
    public String name = "";
    public String email = "";
    public String phone_number = "";

    public boolean status = false;

    public boolean login(String email, String password) {
        String query = "SELECT * FROM faculties WHERE email=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user_id = resultSet.getString("id");
                name = resultSet.getString("name");
                phone_number = resultSet.getString("phone_number");
                this.email = resultSet.getString("email");
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
                String time = dtf.format(now);
                String logMessage = "Faculty " + user_id + " logged in on " + time + "\n";
                Files.write(Paths.get("src/main/resources/Transcript_Login_Logout.txt"), logMessage.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                System.out.println("Logged in successfully\n");
                status = true;
                return true;
            } else {
                System.err.println("Wrong Credentials!\n");
                return false;
            }
        } catch (IOException | SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
            return false;
        }
    }

    public boolean logout() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);
            String logMessage = "Faculty " + user_id + " logged out on " + time + "\n";
            Files.write(Paths.get("src/main/resources/Transcript_Login_Logout.txt"), logMessage.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            System.out.println("Logged out successfully\n");
            status = false;
            return true;
        } catch (IOException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }

    public boolean viewProfile() {
        String query = "SELECT * FROM faculties WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user_id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                StringBuilder responseBuilder = new StringBuilder();
                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        if ("id".equals(columnName)) {
                            responseBuilder.append("ID: ");
                        } else if ("name".equals(columnName)) {
                            responseBuilder.append("Name: ");
                        } else if ("dep_id".equals(columnName)) {
                            responseBuilder.append("Dep ID: ");
                        } else if ("email".equals(columnName)) {
                            responseBuilder.append("Email: ");
                        } else if ("phone_number".equals(columnName)) {
                            responseBuilder.append("Phone No.: ");
                        } else {
                            continue;
                        }
                        responseBuilder.append(resultSet.getString(i));
                        responseBuilder.append('\n');
                    }
                }
                System.out.println(responseBuilder);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }

    public boolean updateProfile(String name, String phone_number) {
        String query = "UPDATE faculties SET name=?, phone_number=? WHERE id=?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phone_number);
            stmt.setString(3, user_id);

            int numRowsUpdated = stmt.executeUpdate();
            System.out.println("Profile updated successfully.\n");
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }


    public boolean getEnrollmentRequests() {
        String query = "SELECT * FROM registration_status WHERE faculty_id=? AND status='Pending faculty approval';";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user_id);
            ResultSet rs = ps.executeQuery();
            StringBuilder responseQuery = new StringBuilder();
            int count = 0;
            while (rs.next()) {
                count++;
                String course_id = rs.getString("course_id");
                String student_id = rs.getString("student_id");
                responseQuery.append("Course ID: ").append(course_id).append("\n");
                responseQuery.append("Student ID: ").append(student_id).append("\n\n");
            }
            if (count == 0) {
                System.out.println("No enrollment requests.");
            }
            System.out.println(responseQuery);
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }


    public boolean approveOrDisapprove(String courseId, String studentId, String resp) {
        String query;
        try {
            stmt = conn.createStatement();
            if (resp.equals("1")) {
                query = "UPDATE registration_status SET status='Approved' WHERE course_id=? AND student_id=?";
            } else {
                query = "UPDATE registration_status SET status='Disapproved' WHERE course_id=? AND student_id=?";
            }
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, courseId);
            ps.setString(2, studentId);
            ps.executeUpdate();
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


    public String viewMyCourses() {

        String query = "SELECT * FROM course_offering WHERE faculty_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                StringBuilder responseQuery = new StringBuilder();
                int count = 0;
                while (rs.next()) {
                    count++;
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i == 1)
                            responseQuery.append("Course ID: ");
                        if (i == 2)
                            responseQuery.append("CGPA limit: ");
                        if (i == 3)
                            continue;
                        String columnValue = rs.getString(i);
                        responseQuery.append(columnValue).append("\n");
                    }
                    responseQuery.append("\n");
                }
                if (count == 0) {
                    System.out.println("You have not offered any course.");
                    return "You have not offered any course.";
                }
                System.out.println(responseQuery);
                return responseQuery.toString();
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return "Error occurred!";
        }

    }

    public boolean viewOfferedCourses() {

        String query = "SELECT * FROM course_catalogue;";

        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(query)) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String responseQuery = "";
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i == 1) {
                            responseQuery += "Course ID: ";
                        }
                        String columnValue = rs.getString(i);
                        responseQuery += columnValue;
                    }
                    responseQuery += "\n";
                }
                System.out.println(responseQuery);
            } catch (SQLException e) {
                System.err.println("Semester has not been started.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        return true;
    }

    public boolean registerCourse(String course_id, String cgpa_limit) {
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM course_catalogue WHERE course_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, course_id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("No course in course catalogue with course ID: " + course_id);
                return false;
            }

            String query2 = "SELECT * FROM course_offering WHERE course_id=?";
            ps = conn.prepareStatement(query2);
            ps.setString(1, course_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Course is already offered.");
                return false;
            }

            String query3 = "INSERT INTO course_offering(course_id, cgpa_limit, faculty_id) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(query3);
            ps.setString(1, course_id);
            ps.setInt(2, Integer.parseInt(cgpa_limit));
            ps.setString(3, user_id);
            ps.executeUpdate();
            System.out.println("Course registered successfully");

        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        return true;
    }


    public boolean deregisterCourse(String courseId) {
        String offeringQuery = "DELETE FROM course_offering WHERE course_id = ? AND faculty_id = ?";
        String statusQuery = "DELETE FROM registration_status WHERE course_id = ?";

        try (PreparedStatement offeringStmt = conn.prepareStatement(offeringQuery);
             PreparedStatement statusStmt = conn.prepareStatement(statusQuery)) {

            offeringStmt.setString(1, courseId);
            offeringStmt.setString(2, user_id);
            int offeringResult = offeringStmt.executeUpdate();

            statusStmt.setString(1, courseId);
            int statusResult = statusStmt.executeUpdate();

            if (offeringResult == 0) {
                System.out.println("You have not offered the course with course ID: " + courseId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
        System.out.println("Course deregistered successfully.");
        return true;
    }

    public Integer getScore(String grade) {
        Integer score = 0;
        switch (grade) {
            case "A" -> score = 10;
            case "A-" -> score = 9;
            case "B" -> score = 8;
            case "B-" -> score = 7;
            case "C" -> score = 6;
            case "C-" -> score = 5;
            case "D" -> score = 4;
            case "E" -> score = 3;
            default -> score = 0;
        }
        return score;
    }


    public boolean submitGrades() {
        String csvFilePath = "src/main/resources/grades.csv";
        String courseId = "";
        String insertSql = "INSERT INTO grades (student_id, faculty_id, course_id, grade, semester, academic_year) VALUES (?, ?, ?, ?, ?, ?)";
        int insertedCount = 0;

        try (PreparedStatement statement = conn.prepareStatement(insertSql);
             BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

            reader.readLine(); // skip header line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 5) {
                    System.out.println("Skipping line because of wrong format: " + line);
                    continue;
                }

                String studentId = data[0];
                String course_Id = data[1];
                String grade = data[2];
                String semester = data[3];
                String academicYear = data[4];

                statement.setString(1, studentId);
                statement.setString(2, user_id);
                statement.setString(3, course_Id);
                statement.setString(4, grade);
                statement.setString(5, semester);
                statement.setString(6, academicYear);


                if (statement.executeUpdate() == 1) {
                    insertedCount++;
                }

                if (insertedCount == 0) {
                    System.err.println("File is empty!");
                    return false;
                }


                if (getScore(grade) != 0) {
                    Integer credits = 0, course_cred = 0;
                    String updatesql1 = "SELECT credits from students WHERE id = ?;";
                    String updatesql2 = "SELECT credits from courses WHERE id = ?;";
                    PreparedStatement statement1 = conn.prepareStatement(updatesql1);
                    statement1.setString(1, studentId);
                    PreparedStatement statement2 = conn.prepareStatement(updatesql2);
                    statement2.setString(1, course_Id);
                    ResultSet rs1 = statement1.executeQuery();
                    if (rs1.next()) {
                        credits = rs1.getInt("credits");
                    }
                    ResultSet rs2 = statement2.executeQuery();
                    if (rs2.next()) {
                        course_cred = rs2.getInt("credits");
                    }
                    credits = credits + course_cred;
                    String updatesql3 = "UPDATE students SET credits = ? WHERE id = ?;";
                    PreparedStatement statement3 = conn.prepareStatement(updatesql3);
                    statement3.setInt(1, credits);
                    statement3.setString(2, studentId);
                    statement3.executeUpdate();
                }
            }

            String query = "SELECT * FROM registration_status WHERE course_id = ?";
            try (PreparedStatement registrationStatement = conn.prepareStatement(query)) {
                registrationStatement.setString(1, courseId);
                ResultSet registrationResult = registrationStatement.executeQuery();

                while (registrationResult.next()) {
                    String studentId = registrationResult.getString(2);
                    String gradeQuery = "SELECT * FROM grades WHERE student_id = ? AND course_id = ?";
                    try (PreparedStatement gradeStatement = conn.prepareStatement(gradeQuery)) {
                        gradeStatement.setString(1, studentId);
                        gradeStatement.setString(2, courseId);
                        ResultSet gradeResult = gradeStatement.executeQuery();
                        int count = 0;
                        while (gradeResult.next()) {
                            count++;
                        }
                        if (count == 0) {
                            System.out.println("No grade submitted for student with student ID: " + studentId);
                            String deleteQuery = "DELETE FROM grades WHERE faculty_id = ? AND course_id = ?";
                            try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
                                deleteStatement.setString(1, user_id);
                                deleteStatement.setString(2, courseId);
                                deleteStatement.executeUpdate();
                            }
                            return false;
                        } else if (count > 1) {
                            System.out.println("Multiple grades submitted for student with student ID:  " + studentId);
                            String deleteQuery = "DELETE FROM grades WHERE faculty_id = ? AND course_id = ?";
                            try (PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
                                deleteStatement.setString(1, user_id);
                                deleteStatement.setString(2, courseId);
                                deleteStatement.executeUpdate();
                            }
                            return false;
                        }
                    }
                }
            }

            System.out.println("Grades submitted successfully");
            return true;

        } catch (SQLException | IOException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return false;
        }
    }

    public boolean updatePassword(String password) {
        String query = "UPDATE faculties SET password=? WHERE id=?;";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, user_id);
            int numRowsUpdated = stmt.executeUpdate();
            if (numRowsUpdated > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.err.println("Password update unsuccessful!");
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
            return false;
        }
        return true;
    }
}
