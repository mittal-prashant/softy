package org.example.Student;

import org.example.AcademicOffice.AcademicOffice;
import org.example.Faculty.Faculty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentTest {
    Student student = new Student();

    @BeforeAll
    @Test
    void login() {
        assertTrue(student.login("2019csb1110@iitrpr.ac.in", "hello"));
        assertTrue(student.status);
    }

    @AfterAll
    @Test
    void logout() {
        student.logout();
        assertFalse(student.status);
    }

    @Test
    void viewProfile() {
        assertTrue(student.viewProfile());
    }

    @Test
    void updateProfile() {
        assertTrue(student.updateProfile("Ramjesh", "9876543210"));
    }

    @Test
    void viewOfferedCourses() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertEquals(student.viewOfferedCourses(), "An error occurred while fetching the data");
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertEquals(student.viewOfferedCourses(), "No courses offered yet.\n");
        Faculty faculty = new Faculty();
        faculty.login("apurva@iitrpr.ac.in", "apurva");
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        academicOffice.addCourseToCurriculum("RD111", "core", "2019CSB");
        faculty.registerCourse("RD111", "7");
        assertEquals(student.viewOfferedCourses(), """
                Course ID: RD111
                CGPA limit: 7
                Course Type: core
                Faculty ID: 1113
                                
                """);
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCurriculum("RD111", "2019CSB");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        assertEquals(student.viewOfferedCourses(), "No courses offered yet.\n");
        faculty.logout();
        academicOffice.endSemester();
    }

    @Test
    void viewRegisteredCourses() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertEquals(student.viewRegisteredCourses(), "Error");
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertEquals(student.viewRegisteredCourses(), "You have no courses.");
        Faculty faculty = new Faculty();
        faculty.login("apurva@iitrpr.ac.in", "apurva");
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        academicOffice.addCourseToCurriculum("RD111", "core", "2019CSB");
        faculty.registerCourse("RD111", "7");
        student.registerCourse("RD111");
        assertEquals(student.viewRegisteredCourses(), """
                Course ID: RD111
                Faculty ID: 1113
                Status: Pending faculty approval

                """);
        student.deregisterCourse("RD111");
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCurriculum("RD111", "2019CSB");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        assertEquals(student.viewRegisteredCourses(), "You have no courses.");
        faculty.logout();
        academicOffice.endSemester();
    }

    @Test
    void registerCourse() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(student.deregisterCourse("RD111"));
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertFalse(student.deregisterCourse("RD111"));
        Faculty faculty = new Faculty();
        faculty.login("apurva@iitrpr.ac.in", "apurva");
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        academicOffice.addCourseToCurriculum("RD111", "core", "2019CSB");
        faculty.registerCourse("RD111", "7");
        assertTrue(student.registerCourse("RD111"));
        student.deregisterCourse("RD111");
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCurriculum("RD111", "2019CSB");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        assertFalse(student.deregisterCourse("RD111"));
        faculty.logout();
        academicOffice.endSemester();
    }

    @Test
    void deregisterCourse() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(student.deregisterCourse("RD111"));
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertFalse(student.deregisterCourse("RD111"));
        Faculty faculty = new Faculty();
        faculty.login("apurva@iitrpr.ac.in", "apurva");
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        academicOffice.addCourseToCurriculum("RD111", "core", "2019CSB");
        faculty.registerCourse("RD111", "7");
        student.registerCourse("RD111");
        assertTrue(student.deregisterCourse("RD111"));
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCurriculum("RD111", "2019CSB");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        assertFalse(student.deregisterCourse("RD111"));
        faculty.logout();
        academicOffice.endSemester();
    }

    @Test
    void viewGrades() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertEquals(student.viewGrades(), "No grades to show");
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertEquals(student.viewGrades(), "No grades to show");
        Faculty faculty = new Faculty();
        faculty.login("apurva@iitrpr.ac.in", "apurva");
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        academicOffice.addCourseToCurriculum("RD111", "core", "2019CSB");
        faculty.registerCourse("RD111", "7");
        student.registerCourse("RD111");
        faculty.submitGrades();
        assertEquals(student.viewGrades(), """
                student_id: 2019CSB1110
                faculty_id: 1113
                course_id: RD111
                grade: D
                semester: winter
                academic_year: 2023
                Score: 4
                                

                """);
        student.deregisterCourse("RD111");
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCurriculum("RD111", "2019CSB");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteGrades();
        academicOffice.deleteCourse("RD111");
        assertEquals(student.viewGrades(), "No grades to show");
        faculty.logout();
        academicOffice.endSemester();
    }

    @Test
    void getCGPA() {
        assertEquals(student.getCGPA(), 0.0);
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertEquals(student.getCGPA(), 0.0);
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        Faculty faculty = new Faculty();
        faculty.login("apurva@iitrpr.ac.in", "apurva");
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        academicOffice.addCourseToCurriculum("RD111", "core", "2019CSB");
        faculty.registerCourse("RD111", "7");
        student.registerCourse("RD111");
        faculty.submitGrades();
        assertEquals(student.getCGPA(), 4.0);
        student.deregisterCourse("RD111");
        faculty.deregisterCourse("RD111");
        academicOffice.deleteCourseFromCurriculum("RD111", "2019CSB");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteGrades();
        academicOffice.deleteCourse("RD111");
        faculty.logout();
        academicOffice.endSemester();
    }

    @Test
    void isEligibleToGraduate() {
        AcademicOffice academicOffice = new AcademicOffice();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(student.isEligibleToGraduate());
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        assertFalse(student.isEligibleToGraduate());
        Faculty faculty = new Faculty();
        faculty.login("apurva@iitrpr.ac.in", "apurva");
        List<String> prereqList = new ArrayList<>();
        assertFalse(student.isEligibleToGraduate());
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "20", prereqList);
        academicOffice.addCourse("RD112", "Random", "CS", "3", "1", "1", "20", prereqList);
        academicOffice.addCourse("RD113", "Random", "CS", "3", "1", "1", "20", prereqList);
        academicOffice.addCourse("CP302", "Random", "CS", "3", "1", "1", "20", prereqList);
        academicOffice.addCourse("CP303", "Random", "CS", "3", "1", "1", "20", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        academicOffice.addCourseToCourseCatalogue("RD112");
        academicOffice.addCourseToCourseCatalogue("RD113");
        academicOffice.addCourseToCourseCatalogue("CP302");
        academicOffice.addCourseToCourseCatalogue("CP303");
        academicOffice.addCourseToCurriculum("RD111", "core", "2019CSB");
        academicOffice.addCourseToCurriculum("RD112", "elective", "2019CSB");
        academicOffice.addCourseToCurriculum("RD113", "elective", "2019CSB");
        academicOffice.addCourseToCurriculum("CP302", "general engineering", "2019CSB");
        academicOffice.addCourseToCurriculum("CP303", "general engineering", "2019CSB");
        faculty.registerCourse("RD111", "7");
        faculty.registerCourse("RD112", "7");
        faculty.registerCourse("RD113", "7");
        faculty.registerCourse("CP302", "7");
        faculty.registerCourse("CP303", "7");
        student.registerCourse("RD111");
        student.registerCourse("RD112");
        student.registerCourse("RD113");
        student.registerCourse("CP302");
        student.registerCourse("CP303");
        assertFalse(student.isEligibleToGraduate());
        faculty.submitGrades();
        assertFalse(student.isEligibleToGraduate());
        student.deregisterCourse("RD111");
        student.deregisterCourse("RD112");
        student.deregisterCourse("RD113");
        student.deregisterCourse("CP302");
        student.deregisterCourse("CP303");
        faculty.deregisterCourse("RD111");
        faculty.deregisterCourse("RD112");
        faculty.deregisterCourse("RD113");
        faculty.deregisterCourse("CP302");
        faculty.deregisterCourse("CP303");
        academicOffice.deleteCourseFromCurriculum("RD111", "2019CSB");
        academicOffice.deleteCourseFromCurriculum("RD112", "2019CSB");
        academicOffice.deleteCourseFromCurriculum("RD113", "2019CSB");
        academicOffice.deleteCourseFromCurriculum("CP302", "2019CSB");
        academicOffice.deleteCourseFromCurriculum("CP303", "2019CSB");
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourseFromCourseCatalogue("RD112");
        academicOffice.deleteCourseFromCourseCatalogue("RD113");
        academicOffice.deleteCourseFromCourseCatalogue("CP302");
        academicOffice.deleteCourseFromCourseCatalogue("Cp303");
        academicOffice.deleteGrades();
        assertFalse(student.isEligibleToGraduate());
        academicOffice.deleteCourse("RD111");
        academicOffice.deleteCourse("RD112");
        academicOffice.deleteCourse("RD113");
        academicOffice.deleteCourse("CP302");
        academicOffice.deleteCourse("CP303");
        faculty.logout();
        academicOffice.endSemester();
    }

    @Test
    void updatePassword() {
        assertTrue(student.updatePassword("hemlo"));
        assertTrue(student.updatePassword("hello"));
    }
}