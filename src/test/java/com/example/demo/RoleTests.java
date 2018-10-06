package com.example.demo;

import com.example.demo.bean.Author;
import com.example.demo.bean.Book;
import com.example.demo.bean.Role;
import com.example.demo.dao.AuthorDAO;
import com.example.demo.dao.BookDAO;
import com.example.demo.dao.RoleDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTests
{
    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private BookDAO bookDAO;

    @Before
    public void init()
    {
        Author lewis = new Author("Lewis");
        Author mark = new Author("Mark");
        Author peter = new Author("Peter");

        Role role11 = new Role("role1");
        Role role12 = new Role("role2");

        lewis.setRole(role11);
        mark.setRole(role11);
        peter.setRole(role12);

        Book spring = new Book("Spring in Action");
        Book springboot = new Book("Spring Boot in Action");

        lewis.getBooks().addAll(Arrays.asList(spring, springboot));
        mark.getBooks().add(spring);
        peter.getBooks().add(springboot);

        authorDAO.saveAll(Arrays.asList(lewis, mark, peter));
    }

    @After
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void deleteAll()
    {
        List<Author> authors = authorDAO.findAll();
        // 从主表解除关系
        for (Author author : authors)
        {
            author.setRole(null);
        }
        authorDAO.saveAll(authors);
        assertThat(roleDAO.findByName("role1").getAuthors().size()).isEqualTo(0);
        roleDAO.deleteAll();
        authorDAO.deleteAll();
    }

    @Test
    public void test()
    {

    }

    @Test
    public void aVoid()
    {
        Author lewis = authorDAO.findByName("Lewis");
        assertThat(lewis.getBooks().size()).isEqualTo(2);
    }

    @Test
    public void deleteOne()
    {
        Role role = roleDAO.findByName("role1");
        Set<Author> authors = role.getAuthors();
        for (Author author : authors)
        {
            author.setRole(null);
        }
        authorDAO.saveAll(authors);
        roleDAO.delete(role);
    }

    @Test
    public void deleteMany()
    {
        Author author = authorDAO.findByName("Lewis");
        authorDAO.delete(author);
    }
}
