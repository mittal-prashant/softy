package org.example.AcademicOffice;

import static org.example.AcademicOffice.AcademicOfficeMenu.academicOfficeMenu;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AcademicOfficeMenuTest {
    InputStream input;

    @BeforeAll
    @Test
    void login() {
        input=new ByteArrayInputStream("dean_ug\nadmin\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @AfterAll
    @Test
    void logout() {
        input=new ByteArrayInputStream("dean_ug\niitropar\n1\ndean_ug\nadmin\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action1(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n1\n20XX\nsummer\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action2(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n2\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }


    @Test
    void action3(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n3\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }


    @Test
    void action4(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n4\n2020XXX\n20XX\nCS\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action5(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n5\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action6(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n6\n2020XXX\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action7(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n7\nCS\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action8(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n8\nRD111\nRandom\nCS\n3\n1\n1\n4\n0\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action9(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n9\nRD111\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action10(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n10\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action11(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n11\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action12(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n12\n2020EEB1048\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }


    @Test
    void action13(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n13\n1\n2020EEBXXXX\nRandom\n2020EEB\n2020eebxxxx@iitrpr.ac.in\n89207700XX\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n13\n2\nXXXX\nRandom\nxxxx@iitrpr.ac.in\nCS\n89207700XX\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n13\n3\n2020EEBXXXX\nRandom\n2020EEB\n2020eebxxxx@iitrpr.ac.in\n89207700XX\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action14(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n14\n1\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n14\n2\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n14\n3\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }


    @Test
    void action15(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n15\n1\n2020EEBXXXX\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n15\n2\nXXXX\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n15\n3\n2020EEBXXXX\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action16(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n16\n1\nRD111\n2020EEB\ncore\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n16\n2\nRD111\n2020EEB\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n16\n3\n2020EEBXXXX\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("admin\niitropar\n16\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action17(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n17\n1\nRD111\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n17\n2\nRD111\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("dean_ug\nadmin\n17\n3\nRD111\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
        input=new ByteArrayInputStream("admin\niitropar\n17\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }

    @Test
    void action18(){
        input=new ByteArrayInputStream("dean_ug\nadmin\n18\n0\n0\n0".getBytes());
        System.setIn(input);
        assertTrue(academicOfficeMenu());
    }
}
