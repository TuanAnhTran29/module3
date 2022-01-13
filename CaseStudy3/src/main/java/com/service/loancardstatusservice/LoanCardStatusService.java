package com.service.loancardstatusservice;

import com.config.SingletonConnection;
import com.model.*;

import java.sql.*;
import java.util.List;

public class LoanCardStatusService implements ILoanCardStatusService {

    private final Connection connection= SingletonConnection.getConnection();
    private static final String FIND_BY_LOAN_CARD_STATUS_ID= "select * from loancard_status where id=?";


    @Override
    public void add(LoanCardStatus loanCardStatus) {

    }

    @Override
    public List<LoanCardStatus> findAll() {
        return null;
    }

    @Override
    public LoanCardStatus findById(int id) {
        LoanCardStatus loanCardStatus= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_LOAN_CARD_STATUS_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int statusId= rs.getInt("id");
                String type= rs.getString("type");
                loanCardStatus= new LoanCardStatus(id,type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanCardStatus;
    }

    @Override
    public void update(int id, LoanCardStatus loanCardStatus) {

    }

    @Override
    public void delete(int id) {

    }
}
