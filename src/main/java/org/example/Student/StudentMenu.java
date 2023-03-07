package org.example.Student;

import java.util.Scanner;

public class StudentMenu {
    public static boolean studentMenu() {
        Scanner input = new Scanner(System.in);
        Student student = new Student();

        String email, pass = null;
        String action = "1";

        while (!student.status) {
            if (action.equals("0")) {
                break;
            }

            /*
            Doesn't allow to perform operations until the user has logged in as Student.
             */

            System.out.print("Enter your email: ");
            email = input.nextLine();
            System.out.print("Enter your password: ");
            pass = input.nextLine();

            if (student.login(email, pass)) {
                break;
            }

            /*
            When wrong credentials you can retry or quit.
             */

            System.out.println("""
                    Press
                    Any key to retry
                    0. To exit""");

            action = input.nextLine();
        }

        while (student.status) {
            System.out.println("""
                    Press
                    0. To logout
                    1. To view profile details
                    2. To update profile details
                    3. To view registered courses
                    4. To view offered courses
                    5. To register a course
                    6. To deregister a course
                    7. To view grades
                    8. To compute your cgpa
                    9. To check graduation
                    10. To change password""");

            action = input.nextLine();

            switch (action) {
                /*
                Various cases for the input of action whichever Student wants to operate
                 */
                case "0" -> student.logout();
                case "1" -> student.viewProfile();
                case "2" -> {
                    /*
                    operation to update the information of student and display it
                     */

                    System.out.println("Current name: " + student.name);
                    System.out.print("Enter updated name: ");
                    String name = input.nextLine();
                    System.out.println("Current phone number: " + student.phone_number);
                    System.out.print("Enter updated phone: ");
                    String phoneNumber = input.nextLine();
                    System.out.print("Enter password to make updates: ");
                    String password = input.nextLine();
                    if (password.equals(pass)) {
                        student.updateProfile(name, phoneNumber);
                        student.viewProfile();
                    } else
                        System.err.println("Invalid password!");
                }
                case "3" -> System.out.print(student.viewRegisteredCourses());
                case "4" -> System.out.print(student.viewOfferedCourses());
                case "5" -> {
                    /*
                    operation to register for a course
                     */

                    String courseId;
                    System.out.print("Enter course id to register: ");
                    courseId = input.nextLine();
                    student.registerCourse(courseId);
                }
                case "6" -> {
                    /*
                    operation to deregister a course
                     */

                    String courseId;
                    System.out.print("Enter course id to deregister: ");
                    courseId = input.nextLine();
                    student.deregisterCourse(courseId);
                }
                case "7" -> student.viewGrades();
                case "8" -> System.out.println("Current CGPA: " + student.getCGPA());
                case "9" -> System.out.println(student.isEligibleToGraduate());
                case "10" -> {
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
                        student.updatePassword(newPassword);
                    } else
                        System.err.println("Invalid password!");
                }
                default -> System.err.println("Invalid Input!");
            }

            /*
            Press any key to continue so that input is not misunderstood.
             */

            System.out.println("Press any key to continue.");
            input.nextLine();
        }
        return true;
    }
}