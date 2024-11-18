package Instances;

public class Admin extends User {
    String username;
    public Admin(String fname, String lname, String mail, String pw, String username){
        super(fname, lname, mail, pw);
        this.username = username;
    }

    @Override
    public String getFname() {return "??????";}
    @Override
    public String getLname() {return "??????";}
    @Override
    public String getMail() {return "??????";}
    @Override
    public String getPw() {return "??????";}

    public String getUsername() {
        return username;
    }
}
