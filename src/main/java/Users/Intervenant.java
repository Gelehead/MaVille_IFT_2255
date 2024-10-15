package Users;
public class Intervenant extends User{
    private String fname, lname, mail, pw, enterprise, id;
    
    public Intervenant(String fname, String lname, String mail, String pw, String enterprise, String id){
        super(fname, lname, mail, pw);
        this.enterprise = enterprise;
        this.id = id;
    }

    public String getEnterprise() {return this.enterprise;}
    public String getLname() {return lname;}
    public String getMail() {return this.mail;}
    public String getPw() {return this.pw;}
    public String getFname() {return this.fname;}
    public String getId() {return id;}

    //setters
    public void setLname(String lname) {this.lname = lname;}
    public void setMail(String mail) {this.mail = mail;}
    public void setPw(String pw) {this.pw = pw;}
    public void setfName(String fName) {this.fname = fName;}
    public void setId(String id) {this.id = id;}
    public void setEnterprise(String enterprise) {this.enterprise = enterprise;}
}
