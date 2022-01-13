package com.model;

public class Librarian {
    private int id;
    private String name;
    private Account account;

    public Librarian(){}

    public Librarian(int id, String name, Account account) {
        this.id = id;
        this.name = name;
        this.account = account;
    }

    public Librarian(String name, Account account) {
        this.name = name;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
