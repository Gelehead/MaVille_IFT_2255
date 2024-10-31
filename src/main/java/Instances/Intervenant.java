package Instances;
public class Intervenant extends User{

    public User.Type type = User.Type.INTERVENANT;
    // TODO: find more things to differentiate this from the user class
    private String enterprise;

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

    //setters
    public void setEnterprise(String enterprise) {this.enterprise = enterprise;}
}
