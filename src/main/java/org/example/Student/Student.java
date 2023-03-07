package org.example.Student;

import org.example.ConnectDB;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Student extends ConnectDB {
    Connection conn = connectDB();
    public String user_id = "";
    public String name = "";
    public String email = "";
    public String phone_number = "";
    public String batch_id = "";
    public int credits = 0;
    public boolean status = false;

    public boolean login(String email, String password) {
        String query = "SELECT * FROM students WHERE email=? AND password=?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                user_id = resultSet.getString("id");
                name = resultSet.getString("name");
                batch_id = resultSet.getString("batch_id");
                this.email = resultSet.getString("email"); // use "this" to distinguish from the parameter
                phone_number = resultSet.getString("phone_number");
                credits = resultSet.getInt("credits");
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
                String time = dtf.format(now);
                String logMessage = "Student " + user_id + " logged in on " + time + "\n";
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
            String logMessage = "Student " + user_id + " logged out on " + time + "\n";
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
        String query = "SELECT * FROM students WHERE id = ?";
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
                        } else if ("batch_id".equals(columnName)) {
                            responseBuilder.append("Batch: ");
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
        String query = "UPDATE students SET name=?, phone_number=? WHERE id=?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phone_number);
            stmt.setString(3, user_id);
            System.out.println("Profile updated successfully.");
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage());
            return false;
        }

        return true;
    }


    public String viewOfferedCourses() {
        String query = """
                SELECT course_offering.course_id, course_offering.cgpa_limit, ug_curriculum.course_type, course_offering.faculty_id
                FROM ug_curriculum, course_offering
                WHERE ? = ug_curriculum.batch_id AND ug_curriculum.course_id = course_offering.course_id;""";

        StringBuilder responseBuilder = new StringBuilder();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, batch_id);
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i == 1) {
                        responseBuilder.append("Course ID: ");
                    } else if (i == 2) {
                        responseBuilder.append("CGPA limit: ");
                    } else if (i == 3) {
                        responseBuilder.append("Course Type: ");
                    } else if (i == 4) {
                        responseBuilder.append("Faculty ID: ");
                    }
                    String columnValue = resultSet.getString(i);
                    responseBuilder.append(columnValue).append("\n");
                }
                responseBuilder.append("\n");
            }
            if (responseBuilder.length() == 0) {
                responseBuilder.append("No courses offered yet.\n");
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            responseBuilder.append("An error occurred while fetching the data");
        }
        return responseBuilder.toString();
    }


    public String viewRegisteredCourses() {
        String query = "SELECT course_id, faculty_id, status FROM registration_status WHERE student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user_id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (!resultSet.next()) {
                    return "You have no courses.";
                }
                StringBuilder responseBuilder = new StringBuilder();
                do {
                    responseBuilder.append("Course ID: ").append(resultSet.getString("course_id"))
                            .append("\nFaculty ID: ").append(resultSet.getString("faculty_id"))
                            .append("\nStatus: ").append(resultSet.getString("status"))
                            .append("\n\n");
                } while (resultSet.next());
                return responseBuilder.toString();
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
            return "Error";
        }
    }

    public boolean lessThanAYear() {
        String query = "SELECT COUNT (*) FROM ( SELECT DISTINCT academic_year, semester FROM grades WHERE student_id = ? ) AS subquery;";
        int count = 0;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user_id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
            return false;
        }
        return count < 2;
    }

    public boolean registerCourse(String courseId) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM course_offering WHERE course_id = ?");
            stmt.setString(1, courseId);
            double cgpaLimit = 0.0;
            String facultyId = "";
            int count = 0;
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count++;
                cgpaLimit = rs.getDouble("cgpa_limit");
                facultyId = rs.getString("faculty_id");
            }
            if (count == 0) {
                System.err.println("Course with course ID: " + courseId + " dis not offered.");
                return false;
            }
            if (!lessThanAYear()) {
                double cgpa = getCGPA();

                if (cgpa < cgpaLimit) {
                    System.err.println("Your CGPA is less than the CGPA limit for course.");
                    return false;
                }
            }
            stmt = conn.prepareStatement(
                    "SELECT * FROM courses WHERE id = ?");
            stmt.setString(1, courseId);

            int creditsNeeded = 0;

            rs = stmt.executeQuery();
            if (rs.next()) {
                creditsNeeded = rs.getInt("credits");
            }

            if (credits + creditsNeeded > 24) {
                System.out.println("Your credit limit has exceeded for this semester");
                return false;
            }

            stmt = conn.prepareStatement(
                    "SELECT * FROM prereq WHERE course_id = ?");
            stmt.setString(1, courseId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                String prereq = rs.getString("prereq");

                stmt = conn.prepareStatement(
                        "SELECT * FROM grades WHERE course_id = ? AND student_id = ?");
                stmt.setString(1, prereq);
                stmt.setString(2, user_id);

                try (ResultSet rs2 = stmt.executeQuery()) {
                    String grade = "";
                    while (rs2.next()) {
                        grade = rs2.getString("grade");
                    }

                    if (grade.equals("F") || grade.equals("")) {
                        System.out.println("Complete the course " + prereq + " first to register this course");
                        return false;
                    }
                }
            }

            stmt = conn.prepareStatement(
                    "INSERT INTO registration_status(course_id, student_id, faculty_id, status) VALUES (?, ?, ?, 'Pending faculty approval')");
            stmt.setString(1, courseId);
            stmt.setString(2, user_id);
            stmt.setString(3, facultyId);
            stmt.executeUpdate();

            System.out.println("Course registered successfully.");
            return true;

        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
            return false;
        }
    }


    public boolean deregisterCourse(String courseId) {
        String query = "DELETE FROM registration_status WHERE course_id = ? AND student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseId);
            stmt.setString(2, user_id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("You have not registered for the course with Course ID: " + courseId);
                return false;
            } else {
                System.out.println("Course deregistered successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
            return false;
        }
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


    public String viewGrades() {
        String query = "SELECT * FROM grades WHERE student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user_id);
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            StringBuilder responseQuery = new StringBuilder();
            int f = 0;
            while (resultSet.next()) {
                f++;
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnName = rsmd.getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    responseQuery.append(columnName).append(": ").append(columnValue).append("\n");
                }
                responseQuery.append("Score: ").append(getScore(resultSet.getString("grade")));
                responseQuery.append("\n\n\n");
            }
            if (f == 0) {
                System.out.println("No grades to show");
                return "No grades to show";
            }
            System.out.println(responseQuery);
            return responseQuery.toString();
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + '\n');
            return e.getMessage();
        }
    }

    public double getCGPA() {
        String query = "SELECT grades.grade, courses.credits " +
                "FROM grades " +
                "JOIN courses ON grades.course_id = courses.id " +
                "WHERE student_id = ?";
        double cgpa = 0.0;
        double creds = 0.0;
        double points = 0.0;
        int count = 0;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user_id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                count++;

                String grade = resultSet.getString("grade");
                double credits = resultSet.getDouble("credits");
                creds += credits;

                switch (grade) {
                    case "A" -> points += 10 * credits;
                    case "A-" -> points += 9 * credits;
                    case "B" -> points += 8 * credits;
                    case "B-" -> points += 7 * credits;
                    case "C" -> points += 6 * credits;
                    case "C-" -> points += 5 * credits;
                    case "D" -> points += 4 * credits;
                    case "E" -> points += 2 * credits;
                    default -> {
                    }
                }
            }

            if (count > 0) {
                cgpa = points / creds;
            }
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
        }
        return cgpa;
    }

    public boolean isEligibleToGraduate() {
        try (Statement stmt = conn.createStatement()) {
            String query = String.format("SELECT ug_curriculum.course_id FROM ug_curriculum WHERE course_type='core' AND batch_id='%s' EXCEPT SELECT grades.course_id FROM grades WHERE grades.grade!='F' AND grades.student_id='%s';", batch_id, user_id);
            ResultSet rs = stmt.executeQuery(query);
            int count = 0;
            StringBuilder responsequery = new StringBuilder();
            if (rs.next()) {
                System.out.println("Unable to graduate: Not completed core courses.");
                return false;
            }
            String q1 = "SELECT * FROM ug_curriculum, grades WHERE ug_curriculum.course_id=grades.course_id AND ug_curriculum.course_type='elective' AND grades.grade!='F' AND grades.student_id=" + user_id + ";";
            rs = stmt.executeQuery(q1);
            count = 0;
            while (rs.next()) {
                count++;
            }
            if (count < 2) {
                System.out.println("Unable to graduate: Not completed elective courses.");
                return false;
            }
            count = 0;
            String q2 = "SELECT * FROM grades WHERE (grades.course_id='CP302' OR grades.course_id='CP303') AND grades.grade!='F' and grades.student_id=" + user_id + ";";
            rs = stmt.executeQuery(q2);
            while (rs.next()) {
                count++;
            }
            if (count != 2) {
                System.out.println("Unable to graduate: Not completed capstone projects.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        int coreCourseCredits = 0, engineeringCourseCredits = 0, electiveCourseCredits = 0;

        try (Statement stmt = conn.createStatement()) {
            String coreQuery = String.format("SELECT c FROM courses, ug_curriculum, grades WHERE courses.id=grades.course_id AND ug_curriculum.course_id=courses.id AND (ug_curriculum.course_type='core' OR ug_curriculum.course_type='program elective') AND grades.grade!='F' AND grades.student_id='%s';", user_id);
            ResultSet rs = stmt.executeQuery(coreQuery);
            while (rs.next()) {
                coreCourseCredits += rs.getInt(1);
            }
            if (coreCourseCredits < 70) {
                System.out.println("Unable to graduate: Program courses not completed.");
                return false;
            }
            String engineeringQuery = String.format("SELECT c FROM courses, ug_curriculum, grades WHERE courses.id=grades.course_id AND ug_curriculum.course_id=courses.id AND ug_curriculum.course_type='general engineering' AND grades.grade!='F' AND grades.student_id='%s';", user_id);
            rs = stmt.executeQuery(engineeringQuery);
            while (rs.next()) {
                engineeringCourseCredits += rs.getInt(1);
            }
            if (engineeringCourseCredits < 30) {
                System.out.println("Unable to graduate: Engineering courses not completed.");
                return false;
            }
            String electiveQuery = String.format("SELECT c FROM courses, ug_curriculum, grades WHERE courses.id=grades.course_id AND ug_curriculum.course_id=courses.id AND ug_curriculum.course_type='elective' AND grades.grade!='F' AND grades.student_id='%s';", user_id);
            rs = stmt.executeQuery(electiveQuery);
            while (rs.next()) {
                electiveCourseCredits += rs.getInt(1);
            }
            if (electiveCourseCredits < 35) {
                System.out.println("Unable to graduate: Elective courses not complete.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println(coreCourseCredits + engineeringCourseCredits + electiveCourseCredits);
        if (coreCourseCredits + engineeringCourseCredits + electiveCourseCredits < 140) {
            System.out.println("Unable to graduate: Total credits less than 140");
            return false;
        }
        System.out.println("Eligible to graduate!");
        return true;
    }

    public boolean updatePassword(String password) {
        String query = "UPDATE students SET password=? WHERE id=?;";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setString(2, user_id);
            int numRowsUpdated = stmt.executeUpdate();
            if (numRowsUpdated > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.err.println("Password update unsuccessful!");
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
            return false;
        }
    }
}


