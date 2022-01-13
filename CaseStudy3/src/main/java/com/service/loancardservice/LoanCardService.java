package com.service.loancardservice;

import com.config.SingletonConnection;
import com.model.*;
import com.service.bookservice.BookService;
import com.service.bookservice.IBookService;
import com.service.loancardstatusservice.ILoanCardStatusService;
import com.service.loancardstatusservice.LoanCardStatusService;
import com.service.statusservice.IStatusService;
import com.service.statusservice.StatusService;
import com.service.studentservice.IStudentService;
import com.service.studentservice.StudentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoanCardService implements ILoanCardService {
    private ILoanCardStatusService loanCardStatusService= new LoanCardStatusService();
    private IStatusService statusService= new StatusService();
    private IStudentService studentService= new StudentService();
    private IBookService bookService= new BookService();
    private final Connection connection= SingletonConnection.getConnection();
    private static final String INSERT_LOAN_CARD_SQL= "insert into loancard (code,book,student_id,status_id,borrowDate,returnDate) values (?,?,?,?,?,?)";
    private static final String LIST_ALL_LOAN_CARD= "select * from loancard";
    private static final String FIND_BY_LOAN_CARD_ID= "select * from loancard where id=?";
    private static final String UPDATE_LOAN_CARD= "update loancard set code=?, book=?, student_id=?, status_id=?, borrowDate=?, returnDate=? where id=?";
    private static final String DELETE_FROM_LOAN_CARD= "delete from loancard where id=?";
    private static final String GET_ALL_CODE= "select code from loancard";
    private static final String GET_LOAN_CARD_BY_STUDENT_ID= "select * from loancard where student_id=?";
    private static final String FIND_BY_BOOK_NAME= "select * from loancard inner join book on loancard.book = book.id where book.name=?";

    @Override
    public void add(LoanCard loanCard) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(INSERT_LOAN_CARD_SQL);
            preparedStatement.setString(1,loanCard.getCode());
            preparedStatement.setInt(2, loanCard.getBook().getId());
            preparedStatement.setInt(3, loanCard.getStudent().getId());
            preparedStatement.setInt(4,loanCard.getStatus().getId());
            preparedStatement.setDate(5,loanCard.getDayBorrow());
            preparedStatement.setDate(6,loanCard.getDayReturn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LoanCard> findAll() {
        List<LoanCard> loanCardList= new ArrayList<>();

        try {
            PreparedStatement preparedStatement= connection.prepareStatement(LIST_ALL_LOAN_CARD);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int loanCardId=rs.getInt("id");
                String code= rs.getString("code");
                Book book = bookService.findById(rs.getInt("book"));
                Student student= studentService.findById(rs.getInt("student_id"));
                LoanCardStatus status= loanCardStatusService.findById(rs.getInt("status_id"));
                Date borrowDate= rs.getDate("borrowDate");
                Date returnDate= rs.getDate("returnDate");
                loanCardList.add(new LoanCard(loanCardId,code,book,student,status,borrowDate,returnDate));

                System.out.println(new LoanCard(loanCardId,code,book,student,status,borrowDate,returnDate));
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanCardList;
    }

    @Override
    public LoanCard findById(int id) {
        LoanCard loanCard= null;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_LOAN_CARD_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int loanCardId= rs.getInt("id");
                String code= rs.getString("code");
                Book book = bookService.findById(rs.getInt("book"));
                Student student= studentService.findById(rs.getInt("student_id"));
                LoanCardStatus status= loanCardStatusService.findById(rs.getInt("status_id"));
                Date borrowDate= rs.getDate("borrowDate");
                Date returnDate= rs.getDate("returnDate");
                loanCard= new LoanCard(loanCardId,code,book,student,status,borrowDate,returnDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanCard;
    }

    @Override
    public void update(int id, LoanCard loanCard) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(UPDATE_LOAN_CARD);
            preparedStatement.setString(1, loanCard.getCode());
            preparedStatement.setInt(2, loanCard.getBook().getId());
            preparedStatement.setInt(3, loanCard.getStudent().getId());
            preparedStatement.setInt(4, loanCard.getStatus().getId());
            preparedStatement.setDate(5,loanCard.getDayBorrow());
            preparedStatement.setDate(6,loanCard.getDayReturn());
            preparedStatement.setInt(7,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(DELETE_FROM_LOAN_CARD);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getAllCode() {
        List<String> codeList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(GET_ALL_CODE);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                String code= rs.getString("code");
                codeList.add(code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codeList;
    }

    @Override
    public List<LoanCard> findByStudentId(int id) {
        List<LoanCard> loanCard= new ArrayList<>();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(GET_LOAN_CARD_BY_STUDENT_ID);
            preparedStatement.setInt(1,id);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int loanCardId= rs.getInt("id");
                String code= rs.getString("code");
                Book book = bookService.findById(rs.getInt("book"));
                Student student= studentService.findById(rs.getInt("student_id"));
                LoanCardStatus status= loanCardStatusService.findById(rs.getInt("status_id"));
                Date borrowDate= rs.getDate("borrowDate");
                Date returnDate= rs.getDate("returnDate");
                loanCard.add(new LoanCard(loanCardId,code,book,student,status,borrowDate,returnDate));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanCard;
    }

    @Override
    public List<LoanCard> findByBookName(String bookName) {
        List<LoanCard> loanCardList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(FIND_BY_BOOK_NAME);
            preparedStatement.setString(1,bookName);
            ResultSet rs= preparedStatement.executeQuery();

            while (rs.next()){
                int loanCardId= rs.getInt("id");
                String code= rs.getString("code");
                Book book = bookService.findById(rs.getInt("book"));
                Student student= studentService.findById(rs.getInt("student_id"));
                LoanCardStatus status= loanCardStatusService.findById(rs.getInt("status_id"));
                Date borrowDate= rs.getDate("borrowDate");
                Date returnDate= rs.getDate("returnDate");
                loanCardList.add(new LoanCard(loanCardId,code,book,student,status,borrowDate,returnDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loanCardList;
    }


}
