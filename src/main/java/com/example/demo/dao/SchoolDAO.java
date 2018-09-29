package com.example.demo.dao;

import com.example.demo.bean.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolDAO extends JpaRepository<School, Integer>
{
}
