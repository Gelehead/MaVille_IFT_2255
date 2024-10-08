import Utils.Language;

public class User {

    // first name, last name, email, password, enterprise
    private String fName, Lname, mail, pw, id, enterprise;

    public User(String fName, String Lname, String mail, String pw, String id, String enterprise){
        this.fName = fName;
        this.Lname = Lname;
        this.mail = mail;
        this.pw = pw;
        this.id = id;
        this.enterprise = enterprise;
    }

    // case user = particulier (not part of an firm)
    public User(String fName, String Lname, String mail, String pw, String id){
        this.fName = fName;
        this.Lname = Lname;
        this.mail = mail;
        this.pw = pw;
        this.id = id;
    }


    // getters
    public String getEnterprise() {return this.enterprise == null ? Language.notAnEnterprise("french") : this.enterprise;}
    public String getId() {return id;}
    public String getLname() {return Lname;}
    public String getMail() {return mail;}
    public String getPw() {return pw;}
    public String getfName() {return fName;}

    // setters
    public void setEnterprise(String enterprise) {this.enterprise = enterprise;}
    public void setId(String id) {this.id = id;}
    public void setLname(String lname) {Lname = lname;}
    public void setMail(String mail) {this.mail = mail;}
    public void setPw(String pw) {this.pw = pw;}
    public void setfName(String fName) {this.fName = fName;}

}
