package org.example.AcademicOffice;

import org.example.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AcademicOfficeMenu {
    public static boolean academicOfficeMenu() {
        Scanner input = new Scanner(System.in);
        AcademicOffice academicOffice = new AcademicOffice();

        String user = null, pass;
        String action = "1";

        while (!academicOffice.status) {
            if (action.equals("0")) {
                break;
            }

            /*
            academic office can not access the operations before authorised login
            username and password are required
             */

            System.out.print("Enter your username: ");
            user = input.nextLine();
            System.out.print("Enter your password: ");
            pass = input.nextLine();

            if (academicOffice.login(user, pass)) {
                break;
            }

            /*
            you can leave any time when entered wrong credentials or try again
             */

            System.out.println("""
                    Press
                    Any key to retry
                    0. To exit""");

            action = input.nextLine();
        }

        /*
        various operations displayed for the academic office
         */

        while (academicOffice.status) {
            System.out.println("""
                    Press
                    0. To logout
                    1. To start semester
                    2. To view semester
                    3. To end semester
                    4. To add a new batch
                    5. To view all batch
                    6. To delete a batch
                    7. To view courses
                    8. To add a new course
                    9. To delete a course
                    10. To view grades of students
                    11. To delete grades
                    12. To generate student transcript
                    13. To add users
                    14. To view users
                    15. To delete users""");

            /*
            only dean_ug is allowed to update course_catalogue and ug curriculum
             */

            if (user.equals("dean_ug")) {
                System.out.println("""
                        16. To update ug curriculum
                        17. To update course_catalog""");
            }


            action = input.nextLine();

            switch (action) {
                case "0" -> academicOffice.logout();
                case "1" -> {
                    /*
                    operation to start a semester with academic year and semester name winter/summer
                     */

                    String academicYear, semester;
                    System.out.print("Enter Academic Year: ");
                    academicYear = input.nextLine();
                    System.out.print("Enter Semester (summer/winter): ");
                    semester = input.nextLine();
                    academicOffice.startSemester(academicYear, semester);
                }
                case "2" -> academicOffice.viewSemester();
                case "3" -> academicOffice.endSemester();
                case "4" -> {
                    /*
                    operation to add a batch with batch id, year and department id
                     */

                    String batchId = "", year = "", depId = "";
                    System.out.print("Enter Batch ID: ");
                    batchId = input.nextLine();
                    System.out.print("Enter Year: ");
                    year = input.nextLine();
                    System.out.print("Enter Department ID: ");
                    depId = input.nextLine();
                    academicOffice.addBatch(batchId, year, depId);
                }
                case "5" -> academicOffice.viewBatch();
                case "6" -> {
                    /*
                    operation to delete a batch with a batch id
                     */

                    String batchId;
                    System.out.print("Enter the Batch ID to delete: ");
                    batchId = input.nextLine();
                    academicOffice.deleteBatch(batchId);
                }
                case "7" -> academicOffice.viewCourses();
                case "8" -> {
                    /*
                    operation to add the course to courses table with following parameters
                    course id
                    course name
                    department id
                    lec
                    tut
                    pra
                    credits
                    pre-requisite courses
                     */

                    String courseId = "", courseName = "", depId = "", l, t, p, credits;
                    System.out.print("Enter Course ID: ");
                    courseId = input.nextLine();
                    System.out.print("Enter Course name: ");
                    courseName = input.nextLine();
                    System.out.print("Enter Department ID: ");
                    depId = input.nextLine();
                    System.out.print("Enter number of lectures per week: ");
                    l = input.nextLine();
                    System.out.print("Enter number of tutorials per week: ");
                    t = input.nextLine();
                    System.out.print("Enter number of practicals per week: ");
                    p = input.nextLine();
                    System.out.print("Enter course credits: ");
                    credits = input.nextLine();
                    List<String> prerequisite = new ArrayList<String>();
                    while (true) {
                        /*
                        enter the course id for pre-requisite courses or 0 to exit
                         */

                        String pre;
                        System.out.print("Enter Prerequisite course code or 0 to exit: ");
                        pre = input.nextLine();
                        if (pre.equals("0"))
                            break;
                        prerequisite.add(pre);
                    }
                    academicOffice.addCourse(courseId, courseName, depId, l, t, p, credits, prerequisite);
                }
                case "9" -> {
                    /*
                    operation to delete course from courses table using course id
                     */

                    String courseId;
                    System.out.print("Enter the Course ID to delete: ");
                    courseId = input.nextLine();
                    academicOffice.deleteCourse(courseId);
                }
                case "10" -> academicOffice.showGrades();
                case "11" -> academicOffice.deleteGrades();
                case "12" -> {
                    /*
                    operation to generate transcript for a student
                    it is saved in the transcript_student folder
                     */

                    String studentId;
                    System.out.print("Enter Student ID to generate transcript: ");
                    studentId = input.nextLine();
                    academicOffice.generateTranscript(studentId);
                }
                case "13" -> {
                    /*
                    operation to add a user (student/faculty)
                    1 for student
                    2 for faculty
                     */

                    System.out.println("""
                            Press
                            1 To add Student
                            2 To add Faculty""");
                    String role = input.nextLine();
                    List<String> data = new ArrayList<String>();
                    if (role.equals("1")) {
                        System.out.println("Enter Student Details.");
                        String id, student_name, batch_id, email, password, phone_number;
                        System.out.print("Enter ID: ");
                        id = input.nextLine();
                        System.out.print("Enter Name: ");
                        student_name = input.nextLine();
                        System.out.print("Enter Batch ID: ");
                        batch_id = input.nextLine();
                        System.out.print("Enter Email: ");
                        email = input.nextLine();
                        password = "hello";
                        System.out.print("Enter Phone No: ");
                        phone_number = input.nextLine();
                        data.add(id);
                        data.add(student_name);
                        data.add(batch_id);
                        data.add(email);
                        data.add(password);
                        data.add(phone_number);
                        academicOffice.addUser("1", data);
                    } else if (role.equals("2")) {
                        System.out.println("Enter Faculty Details.");
                        String id, faculty_name, email, dep_id, password, phone_number;
                        System.out.print("Enter ID: ");
                        id = input.nextLine();
                        System.out.print("Enter Name: ");
                        faculty_name = input.nextLine();
                        System.out.print("Enter Email: ");
                        email = input.nextLine();
                        System.out.print("Enter Department ID: ");
                        dep_id = input.nextLine();
                        password = "hello";
                        System.out.print("Enter Phone No: ");
                        phone_number = input.nextLine();
                        data.add(id);
                        data.add(faculty_name);
                        data.add(email);
                        data.add(dep_id);
                        data.add(password);
                        data.add(phone_number);
                        academicOffice.addUser("2", data);
                    } else
                        System.err.println("Invalid Role!");
                }
                case "14" -> {
                    /*
                    operation to view the profiles of users
                    1 for student
                    2 for faculty
                     */

                    String role;
                    System.out.print("Press 1 for Student and 2 for Faculty: ");
                    role = input.nextLine();
                    if (role.equals("1") | role.equals("2"))
                        academicOffice.viewUsers(role);
                    else
                        System.err.println("Invalid Role!");
                }
                case "15" -> {
                    /*
                    operation to delete the profile of users
                    1 for student
                    2 for faculty
                     */

                    String role;
                    System.out.print("Press 1 for Student and 2 for Faculty: ");
                    role = input.nextLine();
                    if (role.equals("1") | role.equals("2")) {
                        System.out.print("Enter the ID to delete: ");
                        String id = input.nextLine();
                        academicOffice.deleteUser(role, id);
                    } else
                        System.err.println("Invalid Role!");
                }
                case "16" -> {
                    /*
                    if dean_ug is logged in, he can add a course to curriculum with batch id and course type
                    or delete from ug curriculum using course id and batch id
                     */

                    if (user.equals("dean_ug")) {
                        String addToCurriculum;
                        System.out.print("Press 1 to add 2 to delete: ");
                        addToCurriculum = input.nextLine();
                        if (addToCurriculum.equals("1")) {
                            String courseId = "", courseType, batchId;
                            System.out.print("Enter Course ID: ");
                            courseId = input.nextLine();
                            System.out.print("Enter Batch ID: ");
                            batchId = input.nextLine();
                            System.out.print("Enter Course type: ");
                            courseType = input.nextLine();
                            academicOffice.addCourseToCurriculum(courseId, courseType, batchId);
                        } else if (addToCurriculum.equals("2")) {
                            String courseId = "", courseType, batchId;
                            System.out.print("Enter Course ID: ");
                            courseId = input.nextLine();
                            System.out.print("Enter Batch ID: ");
                            batchId = input.nextLine();
                            academicOffice.deleteCourseFromCurriculum(courseId, batchId);
                        } else {
                            System.err.println("Invalid Input!");
                        }
                    } else System.err.println("Invalid Input!");
                }
                case "17" -> {
                    /*
                    if dean_ug is logged in, he can add a course to course catalogue
                    or delete from course catalogue using course id
                     */

                    if (user.equals("dean_ug")) {
                        String addToCatalogue;
                        System.out.print("Press 1 to add 2 to delete: ");
                        addToCatalogue = input.nextLine();
                        if (addToCatalogue.equals("1")) {
                            String courseId;
                            System.out.print("Enter Course code: ");
                            courseId = input.nextLine();
                            academicOffice.addCourseToCourseCatalogue(courseId);
                        } else {
                            String courseId;
                            System.out.print("Enter Course code: ");
                            courseId = input.nextLine();
                            academicOffice.deleteCourseFromCourseCatalogue(courseId);
                        }
                    } else System.err.println("Invalid Input!");
                }
                default -> System.err.println("Invalid Input!");
            }

            /*
            Press any key to continue so that input is not misunderstood.
             */

            System.out.println("Press any key to continue");
            input.nextLine();
        }
        return true;
    }
}
