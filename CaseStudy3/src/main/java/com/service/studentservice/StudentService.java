package com.service.studentservice;

import com.config.SingletonConnection;
import com.model.*;
import com.service.accountservice.AccountService;
import com.service.accountservice.IAccountService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentService implements IStudentService {
    private IAccountService accountService= new AccountService();
    private final Connection connection= SingletonConnection.getConnection();
    private static final String INSERT_STUDENT_SQL= "insert into student (name,className,account_id) values (?,?,?)";
    private static final String LIST_ALL_STUDENT= "select * from student";
    private static final String FIND_BY_STUDENT_ID= "select * from student where id=?";
    private static final String UPDATE_STUDENT= "update student set name=?, className=?, account_id=? where id=?";
    private static final String DELETE_FROM_STUDENT= "delete from student where id=?";

    @Override
    public void add(Student student) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(INSERT_STUDENT_SQL);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getStudentClass());
            preparedStatement.setInt(3,student.getAccount().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> studentList= new ArrayList<>();

        try {
            PreparedStatement preparedStatement= connection.prepareStatement(LIST_ALL_STUDENT);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String name= rs.getString("name");
                String className= rs.getString("className");
                Account account= accountService.findById(rs.getInt("account_id"));
                studentList.add(new Student(id,name,className,account));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    @Override
    public Student findById(int id) {
        Student student= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_STUDENT_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int studentId= rs.getInt("id");
                String name= rs.getString("name");
                String className= rs.getString("className");
                Account account= accountService.findById(rs.getInt("account_id"));
                student= new Student(studentId,name,className,account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void update(int id, Student student) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(UPDATE_STUDENT);
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,student.getStudentClass());
            preparedStatement.setInt(3,student.getAccount().getId());
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(DELETE_FROM_STUDENT);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
