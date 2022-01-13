package com.service.statusservice;

import com.config.SingletonConnection;
import com.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StatusService implements IStatusService {
    private final Connection connection= SingletonConnection.getConnection();
    private static final String FIND_BY_STATUS_ID= "select * from status where id= ?";

    @Override
    public void add(Status status) {

    }

    @Override
    public List<Status> findAll() {
        return null;
    }

    @Override
    public Status findById(int id) {
        Status status= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_STATUS_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs=preparedStatement.executeQuery();

            while (rs.next()){
               String type= rs.getString("type");
               status= new Status(id,type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;

    }

    @Override
    public void update(int id, Status status) {

    }

    @Override
    public void delete(int id) {

    }
}
