package com.service.bookservice;

import com.model.Book;
import com.service.IGeneralService;

import java.util.List;

public interface IBookService extends IGeneralService<Book> {
    public void add(Book book);

    public List<Book> findAll();

    public Book findById(int id);

    public void update(int id,Book book);

    public void delete(int id);

    public Book findByBookName(String bookName);
}
