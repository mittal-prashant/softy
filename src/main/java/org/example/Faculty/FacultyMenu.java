package org.example.Faculty;

import java.util.Scanner;

public class FacultyMenu  {
    public static boolean facultyMenu() {
        Scanner input = new Scanner(System.in);
        Faculty faculty = new Faculty();

        String email, pass = null;
        String action = "1";

        while (!faculty.status) {
            if (action.equals("0")) {
                break;
            }

            /*
            faculty can not access any function before logging in with proper email and password
             */

            System.out.print("Enter your email: ");
            email = input.nextLine();
            System.out.print("Enter your password: ");
            pass = input.nextLine();

            if (faculty.login(email, pass)) {
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
        list for various operations available for the faculty
         */

        while (faculty.status) {
            System.out.println("""
                    Press
                    0. To logout
                    1. To view profile details
                    2. To update profile details
                    3. To view enrollment requests
                    4. To approve/disapprove enrollment requests
                    5. To view grades of students
                    6. To view your courses
                    7. To view the course catalog
                    8. To register a course
                    9. To deregister a Course
                    10. To submit the grades
                    11. To change password""");

            action = input.nextLine();

            switch (action) {
                case "0" -> faculty.logout();
                case "1" -> faculty.viewProfile();
                case "2" -> {
                    /*
                    operation to update the information of faculty
                    it first displays current information
                    then ask the new information for the faculty
                     */

                    System.out.println("Current name: " + faculty.name);
                    System.out.print("Enter updated name: ");
                    String name = input.nextLine();
                    System.out.println("Current phone number: " + faculty.phone_number);
                    System.out.print("Enter updated phone: ");
                    String phoneNumber = input.nextLine();
                    System.out.print("Enter password to make updates: ");
                    String password = input.nextLine();

                    /*
                    proper validation is required before updating information
                    password entered should match the current password
                     */

                    if (password.equals(pass)) {
                        faculty.updateProfile(name, phoneNumber);
                        faculty.viewProfile();
                    } else
                        System.err.println("Invalid password!");
                }
                case "3" -> faculty.getEnrollmentRequests();
                case "4" -> {
                    /*
                    operation to approve or disapprove the course requested by a student
                    1 to approve the course
                    2 to disapprove the course
                     */

                    String course_id, student_id;
                    System.out.print("Enter Course ID: ");
                    course_id = input.nextLine();
                    System.out.print("Enter Student ID: ");
                    student_id = input.nextLine();
                    String approve;
                    System.out.print("Press 1 to Approve and 2 to Disapprove: ");
                    approve = input.nextLine();
                    faculty.approveOrDisapprove(course_id, student_id, approve);
                }
                case "5" -> faculty.showGrades();
                case "6" -> faculty.viewMyCourses();
                case "7" -> faculty.viewOfferedCourses();
                case "8" -> {
                    /*
                    operation to register(offer) for a course
                    set the CGPA limit for the course
                     */

                    String courseId;
                    System.out.print("Enter the Course ID to register: ");
                    courseId = input.nextLine();
                    String cgpa_limit;
                    System.out.print("Set CGPA limit for " + courseId + " : ");
                    cgpa_limit = input.nextLine();
                    faculty.registerCourse(courseId, cgpa_limit);
                }
                case "9" -> {
                    /*
                    operation to deregister from a course faculty has offered
                     */

                    String courseId;
                    System.out.println("Enter Course ID to deregister");
                    courseId = input.nextLine();
                    faculty.deregisterCourse(courseId);
                }
                case "10" -> faculty.submitGrades();
                case "11" -> {
                    /*
                    operation to change the current password
                    it is changes with proper validation first
                    current password should match the entered current password
                     */

                    String newPassword, curPassword;
                    System.out.print("Enter Current Password: ");
                    curPassword = input.nextLine();
                    System.out.print("Enter New Password: ");
                    newPassword = input.nextLine();
                    if (curPassword.equals(pass)) {
                        faculty.updatePassword(newPassword);
                    } else
                        System.err.println("Invalid password!");
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