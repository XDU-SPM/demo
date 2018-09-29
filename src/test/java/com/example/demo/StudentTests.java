package com.example.demo;

import com.example.demo.bean.School;
import com.example.demo.bean.Student;
import com.example.demo.dao.SchoolDAO;
import com.example.demo.dao.StudentDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentTests
{
    @Autowired
    private SchoolDAO schoolDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Before
    public void testCreate()
    {
        School school = new School();
        Student st1 = new Student();
        Student st2 = new Student();
        st1.setSchool(school);
        st2.setSchool(school);
        studentDAO.saveAll(Arrays.asList(st1, st2));
    }

    @Test
    public void test()
    {
        Student student = studentDAO.findById(1);
        student.setSchool(null);
        studentDAO.save(student);
    }
}
