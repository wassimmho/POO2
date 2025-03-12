package model;

public class Account {

    public String username;
    public String password;
    public Status status;

    public enum Status{
        Admin,
        Manager
    }

    // Admin Account
    public Account(String username, String password, Status status) {

        this.username = username;
        this.password = password;
        this.status = status;

    }

    // Client Account
    public Account(String username, String password) {

        this.username = username;
        this.password = password;
    }

}
