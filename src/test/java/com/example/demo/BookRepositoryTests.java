package com.example.demo;

import com.example.demo.bean.Author;
import com.example.demo.bean.Book;
import com.example.demo.dao.AuthorDAO;
import com.example.demo.dao.BookDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTests
{
    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @Before
    public void init()
    {
        Author lewis = new Author("Lewis");
        Author mark = new Author("Mark");
        Author peter = new Author("Peter");

        Book spring = new Book("Spring in Action");
        Book springboot = new Book("Spring Boot in Action");

        lewis.getBooks().addAll(Arrays.asList(spring, springboot));
        mark.getBooks().add(spring);
        peter.getBooks().add(springboot);
        authorDAO.saveAll(Arrays.asList(lewis, mark, peter));
    }

    @After
    public void deleteAll()
    {
        // 删除所有作者，级联删除关联的书籍，但是没有与作者关联的书籍不会被删除
        authorDAO.deleteAll();

        // 删除所有的书籍，只能删除没有与作者关联的书籍，与作者关联的书籍无法被删除
        bookDAO.deleteAll();
    }

    @Test
    public void aVoid()
    {

    }

    @Test
    public void findAll()
    {
        assertThat(bookDAO.findAll()).hasSize(2);
        assertThat(authorDAO.findAll()).hasSize(3);
    }

    @Test
    public void findByName()
    {
        assertThat(bookDAO.findByName("Spring in Action")).isNotNull();
        assertThat(authorDAO.findByName("Lewis")).isNotNull();
    }

    @Test
    public void findAllByAuthorsContaining()
    {
        Author lewis = authorDAO.findByName("Lewis");
        Author mark = authorDAO.findByName("Mark");
        List<Book> books = bookDAO.findAllByAuthorsContaining(lewis);
        for (Book book : books)
        {
            System.out.println(book.getName());
        }
        books = bookDAO.findAllByAuthorsContaining(mark);
        for (Book book : books)
        {
            System.out.println(book.getName());
        }
    }

    @Test
    public void findAllByAuthorsIsContaining()
    {
        Author lewis = authorDAO.findByName("Lewis");
        Author mark = authorDAO.findByName("Mark");
        List<Book> books = bookDAO.findAllByAuthorsIsContaining(lewis);
        for (Book book : books)
        {
            System.out.println(book.getName());
        }
        books = bookDAO.findAllByAuthorsIsContaining(mark);
        for (Book book : books)
        {
            System.out.println(book.getName());
        }
    }

    @Test
    public void findAllByAuthorsContains()
    {
        Author lewis = authorDAO.findByName("Lewis");
        Author mark = authorDAO.findByName("Mark");
        List<Book> books = bookDAO.findAllByAuthorsContains(lewis);
        for (Book book : books)
        {
            System.out.println(book.getName());
        }
        books = bookDAO.findAllByAuthorsContains(mark);
        for (Book book : books)
        {
            System.out.println(book.getName());
        }
    }

    @Test
    public void deleteAuthor1()
    {
        Author mark = authorDAO.findByName("Mark");
        // 主表可以直接删除
        authorDAO.delete(mark);
        assertThat(authorDAO.findAll()).hasSize(2);
        // 还有一位作者
        assertThat(bookDAO.findByName("Spring in Action")).isNotNull();
        Author lewis = authorDAO.findByName("Lewis");
        authorDAO.delete(lewis);
        assertThat(authorDAO.findAll()).hasSize(1);
        assertThat(bookDAO.findByName("Spring in Action")).isNull();
    }

    @Test
    public void deleteBook1()
    {
        Book book = bookDAO.findByName("Spring in Action");
        bookDAO.delete(book);
        // 从表删除不了
        assertThat(bookDAO.findAll()).hasSize(2);
    }

    @Test
    public void deleteBook2()
    {
        Book book = bookDAO.findByName("Spring in Action");
        Set<Author> authors = book.getAuthors();
        // 解除所有的关系
        for (Author author : authors)
        {
            author.getBooks().remove(book);
        }
        // 记得保存
        authorDAO.saveAll(authors);
        bookDAO.delete(book);
        assertThat(bookDAO.findByName("Spring in Action")).isNull();
        assertThat(authorDAO.findByName("Mark").getBooks().size()).isEqualTo(0);
    }
}
