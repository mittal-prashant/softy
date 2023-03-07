package org.example.AcademicOffice;

import org.example.Faculty.Faculty;
import org.example.Student.Student;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AcademicOfficeTest {

    AcademicOffice academicOffice = new AcademicOffice();

    @BeforeAll
    @Test
    void login() {
        assertTrue(academicOffice.login("dean_ug", "admin"));
    }

    @AfterAll
    @Test
    void logout() {
        assertTrue(academicOffice.logout());
    }

    @Test
    void startSemester() {
        if (!academicOffice.viewSemester().equals("No semester is running.")) {
            assertEquals(academicOffice.startSemester("20XX", "summer"), "A semester is currently running");
            academicOffice.endSemester();
        }
        assertEquals(academicOffice.startSemester("20XX", "summer"), "20XX-summer semester started successfully");
        academicOffice.endSemester();
    }


    @Test
    void viewSemester() {
        assertEquals(academicOffice.viewSemester(), "No semester is running.");
        academicOffice.startSemester("20XX", "summer");
        assertEquals(academicOffice.viewSemester(), "20XX-summer semester is running");
        academicOffice.endSemester();
    }

    @Test
    void endSemester() {
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(academicOffice.endSemester());
            academicOffice.startSemester("20XX", "summer");
            assertTrue(academicOffice.endSemester());
        } else {
            assertTrue(academicOffice.endSemester());
        }
    }

    @Test
    void addBatch() {
        assertTrue(academicOffice.addBatch("2020XXX", "2020", "CS"));
        assertFalse(academicOffice.addBatch("2019EEB", "2020", "XX"));
        assertFalse(academicOffice.addBatch("2020EEB", "2020", "EE"));
        academicOffice.deleteBatch("2020XXX");
    }

    @Test
    void viewBatch() {
        assertTrue(academicOffice.viewBatch());
    }

    @Test
    void deleteBatch() {
        academicOffice.addBatch("2020XXX", "20XX", "CS");
        assertTrue(academicOffice.deleteBatch("2020XXX"));
    }

    @Test
    void viewCourses() {
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        assertTrue(academicOffice.viewCourses());
        academicOffice.deleteCourse("RD111");
    }

    @Test
    void addCourse() {
        List<String> prereqList1 = new ArrayList<>();
        assertTrue(academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList1));
        List<String> prereqList2 = new ArrayList<>();
        prereqList2.add("RD111");
        assertTrue(academicOffice.addCourse("RD112", "Random", "CS", "3", "1", "1", "4", prereqList2));
        academicOffice.viewCourses();
        academicOffice.deleteCourse("RD112");
        academicOffice.deleteCourse("RD111");
    }

    @Test
    void deleteCourse() {
        assertTrue(academicOffice.deleteCourse("RD111"));
        List<String> prereqList1 = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList1);
        List<String> prereqList2 = new ArrayList<>();
        prereqList2.add("RD111");
        academicOffice.addCourse("RD112", "Random", "CS", "3", "1", "1", "4", prereqList2);
        academicOffice.viewCourses();
        assertTrue(academicOffice.deleteCourse("RD111"));
        assertTrue(academicOffice.deleteCourse("RD112"));
        assertTrue(academicOffice.deleteCourse("RD111"));
    }

    @Test
    void showGrades() {
        Faculty faculty = new Faculty();
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertTrue(academicOffice.showGrades());
        } else {
            academicOffice.endSemester();
        }
        faculty.login("apurva@iitrpr.ac.in", "apurva");
        academicOffice.startSemester("20XX", "summer");
        List<String> prereqList = new ArrayList<>();
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
        assertTrue(academicOffice.showGrades());
        academicOffice.deleteGrades();
        student1.deregisterCourse("RD111");
        student2.deregisterCourse("RD111");
        student1.logout();
        student2.logout();
        faculty.deregisterCourse("RD111");
        faculty.logout();
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
    }

    @Test
    void deleteGrades() {
        assertTrue(academicOffice.deleteGrades());
    }

    @Test
    void generateTranscript() {
        academicOffice.startSemester("20XX", "summer");
        Faculty faculty1 = new Faculty();
        Faculty faculty2 = new Faculty();
        faculty1.login("apurva@iitrpr.ac.in", "apurva");
        faculty2.login("balwinder@iitrpr.ac.in", "balwinder");
        List<String> prereqList1 = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList1);
        academicOffice.addCourseToCourseCatalogue("RD111");
        List<String> prereqList2 = new ArrayList<>();
        prereqList2.add("RD111");
        assertTrue(academicOffice.addCourse("RD112", "Random", "CS", "3", "1", "1", "4", prereqList2));
        academicOffice.addCourseToCourseCatalogue("RD112");
        faculty1.registerCourse("RD111", "7");
        faculty2.registerCourse("RD112", "7");
        Student student = new Student();
        assertTrue(student.login("2019csb1110@iitrpr.ac.in", "hello"));
        student.registerCourse("RD111");
        student.registerCourse("RD112");
        faculty1.submitGrades();
        assertTrue(academicOffice.generateTranscript("2019CSB1110"));
        academicOffice.deleteGrades();
        student.deregisterCourse("RD111");
        student.deregisterCourse("RD112");
        student.logout();
        faculty1.deregisterCourse("RD111");
        faculty2.deregisterCourse("RD112");
        faculty1.logout();
        faculty2.logout();
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourseFromCourseCatalogue("RD112");
        academicOffice.deleteCourse("RD111");
        academicOffice.deleteCourse("RD112");
        academicOffice.endSemester();
    }

    @Test
    void addUser() {
        List<String> data1 = new ArrayList<>();
        data1.add("2019CSBXXXX");
        data1.add("Random");
        data1.add("2019CSB");
        data1.add("2019csbxxxx@iitrpr.ac.in");
        data1.add("hello");
        data1.add("9213936496");
        assertTrue(academicOffice.addUser("1", data1));
        List<String> data2 = new ArrayList<>();
        data2.add("XXXX");
        data2.add("Random");
        data2.add("xxxx@iitrpr.ac.in");
        data2.add("CS");
        data2.add("xxxx");
        data2.add("9213936496");
        assertTrue(academicOffice.addUser("2", data2));
        academicOffice.deleteUser("1", "2019CSBXXXX");
        academicOffice.deleteUser("2", "XXXX");
    }

    @Test
    void viewUsers() {
        assertTrue(academicOffice.viewUsers("1"));
        assertTrue(academicOffice.viewUsers("2"));
    }

    @Test
    void deleteUsers() {
        List<String> data1 = new ArrayList<>();
        data1.add("2019CSBXXXX");
        data1.add("Random");
        data1.add("2019CSB");
        data1.add("2019csbxxxx@iitrpr.ac.in");
        data1.add("hello");
        data1.add("9213936496");
        assertTrue(academicOffice.addUser("1", data1));
        List<String> data2 = new ArrayList<>();
        data2.add("XXXX");
        data2.add("Random");
        data2.add("xxxx@iitrpr.ac.in");
        data2.add("CS");
        data2.add("xxxx");
        data2.add("9213936496");
        assertTrue(academicOffice.addUser("2", data2));
        assertTrue(academicOffice.deleteUser("1", "2019CSBXXXX"));
        assertTrue(academicOffice.deleteUser("2", "XXXX"));
    }

    @Test
    void addCourseToCurriculum() {
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addBatch("2020XXX", "20XX", "CS");
        assertTrue(academicOffice.addCourseToCurriculum("RD111", "core", "2020XXX"));
        academicOffice.deleteCourseFromCurriculum("RD111", "2020XXX");
        academicOffice.deleteBatch("2020XXX");
        academicOffice.deleteCourse("RD111");
    }

    @Test
    void deleteCourseFromCurriculum() {
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addBatch("2020XXX", "20XX", "CS");
        academicOffice.addCourseToCurriculum("RD111", "core", "2020XXX");
        assertTrue(academicOffice.deleteCourseFromCurriculum("RD111", "2020XXX"));
        academicOffice.deleteBatch("2020XXX");
        academicOffice.deleteCourse("RD111");
    }

    @Test
    void addCourseToCourseCatalogue() {
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(academicOffice.addCourseToCourseCatalogue("RD111"));
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        assertTrue(academicOffice.addCourseToCourseCatalogue("RD111"));
        academicOffice.deleteCourseFromCourseCatalogue("RD111");
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
    }

    @Test
    void deleteCourseFromCourseCatalogue() {
        if (academicOffice.viewSemester().equals("No semester is running.")) {
            assertFalse(academicOffice.deleteCourseFromCourseCatalogue("RD111"));
        } else {
            academicOffice.endSemester();
        }
        academicOffice.startSemester("20XX", "summer");
        List<String> prereqList = new ArrayList<>();
        academicOffice.addCourse("RD111", "Random", "CS", "3", "1", "1", "4", prereqList);
        academicOffice.addCourseToCourseCatalogue("RD111");
        assertTrue(academicOffice.deleteCourseFromCourseCatalogue("RD111"));
        academicOffice.deleteCourse("RD111");
        academicOffice.endSemester();
    }
}