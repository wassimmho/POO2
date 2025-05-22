package model;

import java.util.ArrayList;
import model.Movie.MovieGenre;

public class Client extends Account{

    public int UserID;
    public String FirstName;
    public String LastName;
    public int Age;
    public String Email;
    public String PhoneNumber;
    public double CardNumber;
    public int CcvNumber;
    public int Balance;
    public ArrayList<MovieGenre> Intrests = new ArrayList<>();
    

    public Client(int userID, String firstName, String lastName, int Age, int Balance, String email, String phoneNumber, String username, String password) {
        super(username, password);
        this.UserID = userID;
        this.FirstName = firstName;
        this.Balance = Balance;
        this.Age = Age;
        this.LastName = lastName;
        this.Email = email;
        this.PhoneNumber = phoneNumber;
    }



}

