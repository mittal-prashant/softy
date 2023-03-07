package org.example.Faculty;

import org.example.AcademicOffice.AcademicOffice;
import org.example.Faculty.Faculty;
import org.example.Student.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FacultyTest {
    Faculty faculty = new Faculty();

    @BeforeAll
    @Test
    void login() {
        assertTrue(faculty.login("apurva@iitrpr.ac.in", "apurva"));
        assertTrue(faculty.status);
    }

    @AfterAll
    @Test
    void logout() {
        faculty.logout();
        assertFalse(faculty.status);
    }

    @Test
    void viewProfile() {
        assertTrue(faculty.viewProfile());
    }

    @Test
    void updateProfile() {
        assertTrue(faculty.updateProfile("Apoorva Mudgal", "9868066946"));
    }

    @Test
    void getEnrollmentRequests() {
        AcademicOffice academicOffice = new AcademicOffice();
        academicOffice.login("dean_ug","admin");
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(faculty.getEnrollmentRequests());
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "winter");
        List<String> prereqList = new ArrayList<String>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        faculty.registerCourse("RD111", "7");
        assertTrue(faculty.getEnrollmentRequests());
        Student student = new Student();
        student.login("2019csb1110@iitrpr.ac.in", "hello");
        student.registerCourse("RD111");
        assertTrue(faculty.getEnrollmentRequests());
        student.deregisterCourse("RD111");
        student.logout();
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
        academicOffice.logout();
    }

    @Test
    void approveOrDisapprove() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(faculty.approveOrDisapprove("RD111", "2019CSB1110", "1"));
            assertFalse(faculty.approveOrDisapprove("RD111", "2020EEB1048", "2"));
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        List<String> prereqList = new ArrayList<String>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        faculty.registerCourse("RD111", "7");
        Student student1 = new Student();
        Student student2 = new Student();
        assertTrue(student1.login("2019csb1110@iitrpr.ac.in", "hello"));
        assertTrue(student2.login("2020eeb1048@iitrpr.ac.in", "hello"));
        student1.registerCourse("RD111");
        student2.registerCourse("RD111");
        assertTrue(faculty.approveOrDisapprove("RD111", "2020EEB1048", "1"));
        assertTrue(faculty.approveOrDisapprove("RD111", "2019CSB1110", "2"));;
        student1.deregisterCourse("RD111");
        student2.deregisterCourse("RD111");
        student1.logout();
        student2.logout();
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
        academicOffice.logout();
    }

    @Test
    void showGrades() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertTrue(faculty.showGrades());
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        List<String> prereqList = new ArrayList<String>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        faculty.registerCourse("RD111", "7");
        Student student1 = new Student();
        Student student2 = new Student();
        assertTrue(student1.login("2019csb1110@iitrpr.ac.in", "hello"));
        assertTrue(student2.login("2020eeb1048@iitrpr.ac.in", "hello"));
        student1.registerCourse("RD111");
        student2.registerCourse("RD111");
        faculty.submitGrades();
        assertTrue(faculty.showGrades());
        academicOffice.deleteGrades();
        student1.deregisterCourse("RD111");
        student2.deregisterCourse("RD111");
        student1.logout();
        student2.logout();
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
        academicOffice.logout();
    }

    @Test
    void getMyCourses() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertEquals(faculty.viewMyCourses(), "Error occurred!");
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        List<String> prereqList = new ArrayList<String>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        assertEquals(faculty.viewMyCourses(),"You have not offered any course.");
        faculty.registerCourse("RD111", "7");
        assertEquals(faculty.viewMyCourses(),"Course ID: RD111\nCGPA limit: 7\n\n");
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
        academicOffice.logout();
    }

    @Test
    void viewOfferedCourses() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(faculty.viewOfferedCourses());
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertTrue(faculty.viewOfferedCourses());
        List<String> prereqList = new ArrayList<String>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        assertTrue(faculty.viewOfferedCourses());
        academicOffice.addCourseToCourseCatalogue("RD111");
        assertTrue(faculty.viewOfferedCourses());
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
        academicOffice.logout();
    }

    @Test
    void registerCourse() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(faculty.registerCourse("RD111","7"));
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertFalse(faculty.registerCourse("RD111","7"));
        List<String> prereqList = new ArrayList<String>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        assertTrue(faculty.registerCourse("RD111","7"));
        assertFalse(faculty.registerCourse("RD111","7"));
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
        academicOffice.logout();
    }


    @Test
    void deregisterCourse() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(faculty.deregisterCourse("RD111"));
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertTrue(faculty.deregisterCourse("RD111"));
        List<String> prereqList = new ArrayList<String>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        assertTrue(faculty.deregisterCourse("RD111"));
        faculty.registerCourse("RD111","7");
        assertTrue(faculty.deregisterCourse("RD111"));
        assertTrue(faculty.deregisterCourse("RD111"));
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
        academicOffice.logout();
    }

    @Test
    void submitGrades() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(faculty.submitGrades());
        } else {
            academicOffice.endSemester();
        }
        assertFalse(faculty.submitGrades());
        academicOffice.startSemester("20XX", "summer");
        assertFalse(faculty.submitGrades());
        List<String> prereqList = new ArrayList<String>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        faculty.registerCourse("RD111","7");
        Student student1 = new Student();
        Student student2 = new Student();
        assertTrue(student1.login("2019csb1110@iitrpr.ac.in", "hello"));
        assertTrue(student2.login("2020eeb1048@iitrpr.ac.in", "hello"));
        student1.registerCourse("RD111");
        student2.registerCourse("RD111");
        faculty.showGrades();
        assertTrue(faculty.submitGrades());
        student1.deregisterCourse("RD111");
        student2.deregisterCourse("RD111");
        student1.logout();
        student2.logout();
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteGrades();
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
        academicOffice.deleteGrades();
        academicOffice.logout();
    }

    @Test
    void changePassword() {
        assertTrue(faculty.updatePassword("apurva"));
    }
}