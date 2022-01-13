package com.service.roleservice;

import com.config.SingletonConnection;
import com.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleService implements IRoleService{
    Connection connection= SingletonConnection.getConnection();
    private static final String FIND_BY_ROLE_ID= "select * from role where id=?";

    @Override
    public void add(Role role) {

    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role findById(int id) {
        Role role= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_ROLE_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                String type= rs.getString("type");
                role= new Role(id,type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public void update(int id, Role role) {

    }

    @Override
    public void delete(int id) {

    }
}
