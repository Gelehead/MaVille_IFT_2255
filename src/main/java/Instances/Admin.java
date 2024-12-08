package Instances;

/*
 * La classe Admin est une classe fille de User, elle hérite de ses attributs et méthodes.
 * 
 */
public class Admin extends User {
    String username;
    public Admin(String fname, String lname, String mail, String pw, String username){
        super(fname, lname, mail, pw);
        this.username = username;
    }

    /*
     * Les méthodes getFname(), getLname(), getMail() et getPw()
     *  sont redéfinies pour renvoyer des valeurs par défaut.
     */
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
