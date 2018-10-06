package com.example.demo;

import com.example.demo.bean.Author;
import com.example.demo.bean.AuthorBook;
import com.example.demo.bean.Book;
import com.example.demo.dao.AuthorBookDAO;
import com.example.demo.dao.AuthorDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorBookTests
{
    @Autowired
    private AuthorBookDAO authorBookDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @Before
    public void init()
    {
        Author author = new Author("author");
        Book book = new Book("book");
        Book book1 = new Book("book1");
        author.getBooks().add(book1);
        Book book2 = new Book("book2");
        AuthorBook authorBook = new AuthorBook(author, book, new Date());
        AuthorBook authorBook1 = new AuthorBook(author, book2, new Date());
        authorBookDAO.saveAll(Arrays.asList(authorBook, authorBook1));
    }

    @Test
    public void aVoid()
    {
        Author author = authorDAO.findByName("author");
        authorDAO.delete(author);
    }

    @Test
    public void test()
    {
        Author author = authorDAO.findByName("author");
        Set<AuthorBook> authorBooks = author.getAuthorBooks();
        for (AuthorBook authorBook : authorBooks)
        {
            authorBook.setAuthor(null);
            authorBook.setBook(null);
        }
        authorBookDAO.saveAll(authorBooks);
        authorDAO.delete(author);
        authorBookDAO.deleteAll(authorBooks);
    }

    @Test
    public void test1()
    {
        Author author = authorDAO.findByName("author");
        authorDAO.delete(author);
    }
}
