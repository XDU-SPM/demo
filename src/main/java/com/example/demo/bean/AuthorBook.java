package com.example.demo.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "author_book1")
public class AuthorBook
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "aid1")
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bid1")
    private Book book;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;

    public AuthorBook()
    {
    }

    public AuthorBook(Author author, Book book, Date publishDate)
    {
        this.author = author;
        this.book = book;
        this.publishDate = publishDate;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor(Author author)
    {
        this.author = author;
    }

    public Book getBook()
    {
        return book;
    }

    public void setBook(Book book)
    {
        this.book = book;
    }

    public Date getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(Date publishDate)
    {
        this.publishDate = publishDate;
    }
}
