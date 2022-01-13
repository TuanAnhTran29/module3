package com.service.bookservice;

import com.config.SingletonConnection;
import com.model.Account;
import com.model.Book;
import com.model.Role;
import com.model.Status;
import com.service.statusservice.IStatusService;
import com.service.statusservice.StatusService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService implements IBookService {

    private final Connection connection= SingletonConnection.getConnection();

    private static final String INSERT_BOOK_SQL= "insert into book (name,author,description,quantity) values (?,?,?,?)";
    private static final String LIST_ALL_BOOK= "select * from book";
    private static final String FIND_BY_BOOK_ID= "select * from book where id=?";
    private static final String UPDATE_BOOK= "update book set name=?, author=?,description=?,quantity=? where id=?";
    private static final String DELETE_FROM_BOOK= "delete from book where id=?";
    private static final String FIND_BY_BOOK_NAME= "select * from book where name=?";

    @Override
    public void add(Book book) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(INSERT_BOOK_SQL);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4,book.getQuantity());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList= new ArrayList<>();

        try {
            PreparedStatement preparedStatement= connection.prepareStatement(LIST_ALL_BOOK);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String name= rs.getString("name");
                String author= rs.getString("author");
                String description= rs.getString("description");
                int quantity= rs.getInt("quantity");
                bookList.add(new Book(id,name,author,description,quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public Book findById(int id) {
        Book book= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_BOOK_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int bookId= rs.getInt("id");
                String name= rs.getString("name");
                String author= rs.getString("author");
                String description= rs.getString("description");
                int quantity= rs.getInt("quantity");
                book= new Book(bookId,name,author,description,quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void update(int id, Book book) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(UPDATE_BOOK);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4,book.getQuantity());
            preparedStatement.setInt(5,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(DELETE_FROM_BOOK);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book findByBookName(String bookName) {
        Book book= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_BOOK_NAME);
            preparedStatement.setString(1,bookName);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String name= rs.getString("name");
                String author= rs.getString("author");
                String description= rs.getString("description");
                int quantity= rs.getInt("quantity");
                book= new Book(id,name,author,description,quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
}
