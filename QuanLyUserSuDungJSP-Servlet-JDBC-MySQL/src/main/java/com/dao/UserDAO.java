package com.dao;

import com.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "12345";

    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
    private static final String SELECT_USER_BY_COUNTRY= "select id,name,email,country from users where country=?";
    private static final String SQL_INSERT = "INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE EMPLOYEE SET SALARY=? WHERE NAME=?";
    private static final String SQL_TABLE_CREATE = "CREATE TABLE EMPLOYEE"

            + "("

            + " ID serial,"

            + " NAME varchar(100) NOT NULL,"

            + " SALARY numeric(15, 2) NOT NULL,"

            + " CREATED_DATE timestamp,"

            + " PRIMARY KEY (ID)"

            + ")";

    private static final String SQL_TABLE_DROP = "DROP TABLE IF EXISTS EMPLOYEE";



    public void UserDao() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public User selectUser(int id) {
        User user = null;

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public List<User> selectByCountry(String country) {
        List<User> userList= new ArrayList<>();
        try (Connection connection= getConnection(); PreparedStatement preparedStatement= connection.prepareStatement(SELECT_USER_BY_COUNTRY)){
            preparedStatement.setString(1,country);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String name= rs.getString("name");
                String email= rs.getString("email");
                String country1= rs.getString("country");
                userList.add(new User(id,name,email,country1));
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return userList;
    }

    @Override
    public User getUserById(int id) {
        User user= null;

        String query= "{Call get_user_by_id(?)}";

        try (Connection connection= getConnection(); CallableStatement callableStatement= connection.prepareCall(query)){
            callableStatement.setInt(1,id);
            ResultSet rs= callableStatement.executeQuery();

            while (rs.next()){
                String name = rs.getString("name");

                String email = rs.getString("email");

                String country = rs.getString("country");

                user = new User(id, name, email, country);
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return user;
    }

    @Override
    public void insertUserStore(User user) throws SQLException {
        String query= "{call insert_user(?,?,?)}";

        try (Connection connection= getConnection();CallableStatement callableStatement= connection.prepareCall(query)){
            callableStatement.setString(1, user.getName());

            callableStatement.setString(2, user.getEmail());

            callableStatement.setString(3, user.getCountry());

            System.out.println(callableStatement);
            callableStatement.executeUpdate();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    @Override
    public void addUserTransaction(User user, int[] permissions) {
        Connection connection= null;

        PreparedStatement preparedStatement= null;

        PreparedStatement pstmtAssignment = null;

        ResultSet rs= null;

        try {
            connection= getConnection();
            connection.setAutoCommit(false);

            preparedStatement= connection.prepareStatement(INSERT_USERS_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());

            int rowAffected= preparedStatement.executeUpdate();

            rs= preparedStatement.getGeneratedKeys();
            int userId= 0;

            if (rs.next()){
                userId= rs.getInt(1);
            }

            if (rowAffected == 1){
                String sqlPivot =  "INSERT INTO user_permission(user_id,permission_id) "

                        + "VALUES(?,?)";

                pstmtAssignment= connection.prepareStatement(sqlPivot);
                for (int permissionId : permissions){
                    pstmtAssignment.setInt(1,userId);
                    pstmtAssignment.setInt(2,permissionId);
                    pstmtAssignment.executeUpdate();
                }

                connection.commit();
            }else {
                connection.rollback();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();

                if (preparedStatement != null) preparedStatement.close();

                if (pstmtAssignment != null) pstmtAssignment.close();

                if (connection != null) connection.close();

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            }

        }
    }

    @Override
    public void insertUpdateWithoutTransaction() {
        try (Connection connection= getConnection();
             Statement preparedStatement= connection.createStatement();
             PreparedStatement psInsert= connection.prepareStatement(SQL_INSERT);
             PreparedStatement psUpdate= connection.prepareStatement(SQL_UPDATE)){

            preparedStatement.execute(SQL_TABLE_DROP);
            preparedStatement.execute(SQL_TABLE_CREATE);

            psInsert.setString(1,"Quynh");
            psInsert.setBigDecimal(2,new BigDecimal(10));
            psInsert.setTimestamp(3,Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            psInsert.setString(1, "Ngan");
            psInsert.setBigDecimal(2, new BigDecimal(20));
            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            psInsert.execute();

            psUpdate.setBigDecimal(2,new BigDecimal(999.99));
            psUpdate.setString(2, "Quynh");

            psUpdate.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertUpdateUseTransaction() {
        try (Connection connection=getConnection();Statement statement= connection.createStatement();PreparedStatement psInsert= connection.prepareStatement(SQL_INSERT); PreparedStatement psUpdate= connection.prepareStatement(SQL_UPDATE)){
            statement.execute(SQL_TABLE_DROP);
            statement.execute(SQL_TABLE_CREATE);

            connection.setAutoCommit(false);

            psInsert.setString(1, "Quynh");

            psInsert.setBigDecimal(2, new BigDecimal(10));

            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            psInsert.execute();

            psInsert.setString(1, "Ngan");

            psInsert.setBigDecimal(2, new BigDecimal(20));

            psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            psInsert.execute();

            psUpdate.setBigDecimal(1, new BigDecimal(999.99));

            psUpdate.setString(2, "Quynh");

            psUpdate.execute();

            connection.commit();

            connection.setAutoCommit(true);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> showAllUser() {
        List<User> userList= new ArrayList<>();
        String query= "{call show_all_user}";
        try (Connection connection= getConnection();CallableStatement callableStatement= connection.prepareCall(query)){
            ResultSet rs= callableStatement.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String name= rs.getString("name");
                String email= rs.getString("email");
                String country= rs.getString("country");
                userList.add(new User(id,name,email,country));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void updateUser(int id, String name, String email, String country) {
        String query= "{call update_user(?,?,?,?)}";
        try (Connection connection= getConnection();CallableStatement callableStatement= connection.prepareCall(query)){
            callableStatement.setInt(1,id);
            callableStatement.setString(2,name);
            callableStatement.setString(3,email);
            callableStatement.setString(4,country);
            ResultSet rs= callableStatement.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserRecord(int id) {
        String query="{call delete_user(?)}";
        try (Connection connection=getConnection();CallableStatement callableStatement= connection.prepareCall(query)){
            callableStatement.setInt(1,id);
            ResultSet rs= callableStatement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
