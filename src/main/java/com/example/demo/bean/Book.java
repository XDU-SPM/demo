package com.example.demo.bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book_")
public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    /**
     * 多对多的从表
     * 没有 @JoinTable, 需要有 mappedBy, 值为主表对应的属性
     * 没有 CascadeType.ALL
     * 不能通过从表添加
     */
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "books")
    private Set<Author> authors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
    private Set<AuthorBook> authorBooks;

    public Book()
    {
        this.authors = new HashSet<>();
    }

    public Book(String name)
    {
        this();
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<Author> getAuthors()
    {
        return authors;
    }

    public void setAuthors(Set<Author> authors)
    {
        this.authors = authors;
    }

    public Set<AuthorBook> getAuthorBooks()
    {
        return authorBooks;
    }

    public void setAuthorBooks(Set<AuthorBook> authorBooks)
    {
        this.authorBooks = authorBooks;
    }
}
