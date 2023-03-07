package org.example.Faculty;

import static org.example.Faculty.FacultyMenu.facultyMenu;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FacultyMenuTest {
    InputStream input;

    @BeforeAll
    @Test
    void login() {
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @AfterAll
    @Test
    void logout() {
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napoorva\n1\napurva@iitrpr.ac.in\napurva\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action1(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n1\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action2(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n2\nApoorva\n8920770053\napurva\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n2\nApoorva\n8920770053\napoorva\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }


    @Test
    void action3(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n3\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }


    @Test
    void action4(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n4\nRD111\n2020EEBXXXX\n1\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n4\nRD111\n2020EEBXXXX\n2\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action5(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n5\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action6(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n6\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action7(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n7\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action8(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n8\nRD111\n7\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action9(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n9\nRD111\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action10(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n10\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action11(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n11\napurva\napoorva\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napoorva\n11\napoorva\napurva\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n11\napoorva\napurva\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }

    @Test
    void action12(){
        input=new ByteArrayInputStream("apurva@iitrpr.ac.in\napurva\n12\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(facultyMenu());
    }
}
