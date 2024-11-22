package Instances;

import java.util.ArrayList;

public class User {

    public static enum Type {
        USER,
        RESIDENT,
        INTERVENANT,
        ADMIN,

        PLACEHOLER,

        // trust, this is relevant 
        // used in the database.printall
        PROJECT,
        IMPEDIMENT,
    }

    // first name, last name, email, password
    private String fname, lname, mail, pw;
    public Type type = Type.USER;
    private ArrayList<District> subscriptions;

    public User(){}


    // case user = particulier (not part of an enterprise)
    public User(String fname, String lname, String mail, String pw){
        this.fname = fname;
        this.lname = lname;
        this.mail = mail;
        this.pw = pw;
    }

    public void addToSubscriptions(District d){
        this.subscriptions.add(d);
    }

    // getters
    public String getLname() {return this.lname;}
    public String getMail() {return this.mail;}
    public String getPw() {return this.pw;}
    public String getFname() {return this.fname;}
    public ArrayList<District> getSubscriptions() {return subscriptions;}

    // setters
    public void setLname(String lname) {this.lname = lname;}
    public void setMail(String mail) {this.mail = mail;}
    public void setPw(String pw) {this.pw = pw;}
    public void setFname(String fName) {this.fname = fName;}

    @Override
    public String toString() {
        return fname + " " + lname + " : " + mail + " , " + "*".repeat(pw.length());
    }

}
