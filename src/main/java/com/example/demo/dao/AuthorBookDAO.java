package com.example.demo.dao;

import com.example.demo.bean.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorBookDAO extends JpaRepository<AuthorBook, Integer>
{
}
