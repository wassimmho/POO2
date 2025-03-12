package model;

public class Admin extends Account{

    public int ID;
    public String FirstName;
    public String LastName;
    public String Email;
    public String PhoneNumber;

    public Admin(String FirstName, String LastName, String Email, String username, String password, Status status) {

        super(username, password, status.Admin);
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

}
