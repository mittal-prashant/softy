package org.example.Student;

import static org.example.Faculty.FacultyMenu.facultyMenu;
import static org.example.Student.StudentMenu.studentMenu;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentMenuTest {
    InputStream input;

    @BeforeAll
    @Test
    void login() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @AfterAll
    @Test
    void logout() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhemlo\n1\n2019csb1110@iitrpr.ac.in\nhello\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhemlo\n0\n2019csb1110@iitrpr.ac.in\nhello\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @Test
    void action1() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n1\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @Test
    void action2() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n2\nRamjesh\n8920770053\nhello\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n2\nRajesh\n8920770053\nhello\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }


    @Test
    void action3() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n3\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }


    @Test
    void action4() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n4\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @Test
    void action5() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n5\nRD111\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @Test
    void action6() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n6\nRD111\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @Test
    void action7() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n7\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @Test
    void action8() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n8\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @Test
    void action9() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n9\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }

    @Test
    void action10() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n10\nhello\nhemlo\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
        input=new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhemlo\n10\nhemlo\nhello\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
        input=new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n10\nhemlo\nhello\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action11() {
        input = new ByteArrayInputStream("2019csb1110@iitrpr.ac.in\nhello\n11\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(studentMenu());
    }
}
