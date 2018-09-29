package com.example.demo.dao;

import com.example.demo.bean.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDAO extends JpaRepository<Author, Integer>
{
    Author findByName(String name);
}
