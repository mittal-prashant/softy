package org.example;

import java.util.Scanner;

import static org.example.AcademicOffice.AcademicOfficeMenu.academicOfficeMenu;
import static org.example.Faculty.FacultyMenu.facultyMenu;
import static org.example.Student.StudentMenu.studentMenu;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the IIT ROPAR Academic Information Management Portal!!");
        Scanner input = new Scanner(System.in);

        /*
        There are 3 roles for using the Academic Information Management Portal
        1. AcademicOffice: dean_ug, admin
        2. Faculty
        3. Student
         */

        while (true) {
            System.out.print("""
                    Press
                    0. To exit
                    1. For Academic Office login
                    2. For Faculty login
                    3. For Student login
                    Enter your Role:\s""");

            String c = input.nextLine();

            switch (c) {
                case "0" -> {
                    return;
                }
                case "1" -> academicOfficeMenu();
                case "2" -> facultyMenu();
                case "3" -> studentMenu();
                default -> {
                    System.err.println("Invalid Role!\n");
                    System.out.println("Press any key to continue");
                    input.nextLine();
                }
            }
        }
    }
}