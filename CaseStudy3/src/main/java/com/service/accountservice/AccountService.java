package com.service.accountservice;

import com.config.SingletonConnection;
import com.model.Account;
import com.model.Role;
import com.model.Status;
import com.service.roleservice.IRoleService;
import com.service.roleservice.RoleService;
import com.service.statusservice.IStatusService;
import com.service.statusservice.StatusService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountService implements IAccountService {
    private IStatusService statusService= new StatusService();
    private IRoleService roleService= new RoleService();
    private final Connection connection= SingletonConnection.getConnection();
    private static final String INSERT_ACCOUNT_SQL= "insert into account (username,password,fullName,status_id,role_id) values (?,?,?,?,?)";
    private static final String LIST_ALL_ACCOUNT= "select * from account";
    private static final String FIND_BY_ACCOUNT_ID= "select * from account where id=?";
    private static final String UPDATE_ACCOUNT= "update account set username=?, password=?,fullName=?,status_id=?,role_id=? where id=?";
    private static final String DELETE_FROM_ACCOUNT= "delete from account where id=?";
    private static final String FIND_BY_USERNAME= "select * from account where username=?";
    @Override
    public void add(Account account) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(INSERT_ACCOUNT_SQL);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3,account.getFullName());
            preparedStatement.setInt(4,account.getStatus().getId());
            preparedStatement.setInt(5,account.getRole().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Account> findAll() {
        List<Account> accountList= new ArrayList<>();

        try {
            PreparedStatement preparedStatement= connection.prepareStatement(LIST_ALL_ACCOUNT);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String username= rs.getString("username");
                String password= rs.getString("password");
                String fullName= rs.getString("fullName");
                Status status= statusService.findById(rs.getInt("status_id"));
                Role role= roleService.findById(rs.getInt("role_id")) ;
                accountList.add(new Account(id,username,password,fullName,status,role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    @Override
    public Account findById(int id) {
        Account account= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_ACCOUNT_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int accountId= rs.getInt("id");
                String username= rs.getString("username");
                String password= rs.getString("password");
                String fullName= rs.getString("fullName");
                Status status= statusService.findById(rs.getInt("status_id"));
                Role role= roleService.findById(rs.getInt("role_id"));
                account= new Account(accountId,username,password,fullName,status,role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void update(int id, Account account) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(UPDATE_ACCOUNT);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getFullName());
            preparedStatement.setInt(4,account.getStatus().getId());
            preparedStatement.setInt(5,account.getRole().getId());
            preparedStatement.setInt(6,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(DELETE_FROM_ACCOUNT);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findByUserName(String username) {
        Account account= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_USERNAME);
            preparedStatement.setString(1,username);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                account= findById(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;

    }
}
