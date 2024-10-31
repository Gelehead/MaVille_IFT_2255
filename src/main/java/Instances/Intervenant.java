package Instances;
public class Intervenant extends User{

    // TODO: find more things to differentiate this from the user class
    private String fname, lname, mail, pw, enterprise;

    public Intervenant(){}
    
    public Intervenant(String fname, String lname, String mail, String pw, String enterprise){
        super(fname, lname, mail, pw);
        this.enterprise = enterprise;
    }

    @Override
    public String toString() {
        return super.toString() + ", enterprise : " + enterprise;
    }


    // getters 
    public String getEnterprise() {return this.enterprise;}
    public String getLname()      {return lname;}
    public String getMail()       {return this.mail;}
    public String getPw()         {return this.pw;}
    public String getFname()      {return this.fname;}

    //setters
    public void setLname(String lname)           {this.lname = lname;}
    public void setMail(String mail)             {this.mail = mail;}
    public void setPw(String pw)                 {this.pw = pw;}
    public void setfName(String fName)           {this.fname = fName;}
    public void setEnterprise(String enterprise) {this.enterprise = enterprise;}
}
