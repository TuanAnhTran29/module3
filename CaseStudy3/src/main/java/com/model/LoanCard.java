package com.model;

import java.sql.Date;

public class LoanCard {
    private int id;
    private String code;
    private Book book;
    private Student student;
    private LoanCardStatus status;
    private Date dayBorrow;
    private Date dayReturn;

    public LoanCard(){}

    public LoanCard(int id, String code, Book book, Student student, LoanCardStatus status, Date dayBorrow, Date dayReturn) {
        this.id = id;
        this.code = code;
        this.book = book;
        this.student = student;
        this.status = status;
        this.dayBorrow = dayBorrow;
        this.dayReturn = dayReturn;
    }

    public LoanCard(String code, Book book, Student student, LoanCardStatus status, Date dayBorrow, Date dayReturn) {
        this.code = code;
        this.book = book;
        this.student = student;
        this.status = status;
        this.dayBorrow = dayBorrow;
        this.dayReturn = dayReturn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LoanCardStatus getStatus() {
        return status;
    }

    public void setStatus(LoanCardStatus status) {
        this.status = status;
    }

    public Date getDayBorrow() {
        return dayBorrow;
    }

    public void setDayBorrow(Date dayBorrow) {
        this.dayBorrow = dayBorrow;
    }

    public Date getDayReturn() {
        return dayReturn;
    }

    public void setDayReturn(Date dayReturn) {
        this.dayReturn = dayReturn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LoanCard{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", book=" + book +
                ", student=" + student +
                ", status=" + status +
                ", dayBorrow=" + dayBorrow +
                ", dayReturn=" + dayReturn +
                '}';
    }
}
