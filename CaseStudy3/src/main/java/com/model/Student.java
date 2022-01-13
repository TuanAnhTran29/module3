package com.model;

public class Student {
    private int id;
    private String name;
    private String studentClass;
    private Account account;

    public Student(){}

    public Student(int id, String name, String studentClass,Account account) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.account=account;
    }

    public Student(String name, String studentClass, Account account) {
        this.name = name;
        this.studentClass = studentClass;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
