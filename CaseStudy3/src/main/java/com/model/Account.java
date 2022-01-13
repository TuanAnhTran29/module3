package com.model;

public class Account {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private Status status;
    private Role role;

    public Account(int id, String username, String password, String fullName, Status status, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.status = status;
        this.role = role;
    }

    public Account() {
    }

    public Account(String username, String password, String fullName, Status status, Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.status = status;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
