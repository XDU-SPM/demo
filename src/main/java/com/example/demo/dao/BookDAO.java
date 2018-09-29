package com.example.demo.dao;

import com.example.demo.bean.Author;
import com.example.demo.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDAO extends JpaRepository<Book, Integer>
{
    Book findByName(String name);

    List<Book> findAllByAuthorsContaining(Author author);

    List<Book> findAllByAuthorsIsContaining(Author author);

    List<Book> findAllByAuthorsContains(Author author);
}
