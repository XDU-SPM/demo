package com.example.demo.dao;

import com.example.demo.bean.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDAO extends JpaRepository<Student, Integer>
{
    Student findById(int id);
}
