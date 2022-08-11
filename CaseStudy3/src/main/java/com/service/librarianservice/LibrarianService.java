package com.service.librarianservice;

import com.config.SingletonConnection;
import com.model.Account;
import com.model.Librarian;
import com.model.Role;
import com.model.Status;
import com.service.accountservice.AccountService;
import com.service.accountservice.IAccountService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibrarianService implements ILibrarianService {
    IAccountService accountService= new AccountService();
    private final Connection connection= SingletonConnection.getConnection();
    private static final String INSERT_LIBRARIAN_SQL= "insert into librarian (name,account_id) values (?,?)";
    private static final String LIST_ALL_LIBRARIAN= "select * from librarian";
    private static final String FIND_BY_LIBRARIAN_ID= "select * from librarian where id=?";
    private static final String UPDATE_LIBRARIAN= "update librarian set name=?, account_id=? where id=?";
    private static final String DELETE_FROM_LIBRARIAN= "delete from librarian where id=?";


    @Override
    public void add(Librarian librarian) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(INSERT_LIBRARIAN_SQL);
            preparedStatement.setString(1, librarian.getName());
            preparedStatement.setInt(2, librarian.getAccount().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Librarian> findAll() {
        List<Librarian> librarianList= new ArrayList<>();

        try {
            PreparedStatement preparedStatement= connection.prepareStatement(LIST_ALL_LIBRARIAN);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                String name= rs.getString("name");
                Account account= accountService.findById(rs.getInt("account_id")); ;
                librarianList.add(new Librarian(name,account));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librarianList;
    }

    @Override
    public Librarian findById(int id) {
        Librarian librarian= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_LIBRARIAN_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                String name= rs.getString("name");
                Account account= accountService.findById(rs.getInt("account_id"));
                librarian= new Librarian(name,account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librarian;
    }

    @Override
    public void update(int id, Librarian librarian) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(UPDATE_LIBRARIAN);
            preparedStatement.setString(1, librarian.getName());
            preparedStatement.setInt(2, librarian.getAccount().getId());
            preparedStatement.setInt(3,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(DELETE_FROM_LIBRARIAN);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
