import Utils.Language;
import Utils.Speaker;

public class Dialog {
    public static User getUserInfos(String userType){

        String fname = getfirstName();
        String lname = getLastName();
        String mail = getMail();
        String pw = getPassword();
        String id = getId();
        String enterprise = null;
        if (userType == "particulier"){enterprise = getEnterprise();}

        return userType == "particulier" ? new User(fname, lname, mail, pw, id) : new User(fname, lname, mail, pw, id, enterprise);
    }
    
    public static String getfirstName(){    return Speaker.ask(Language.Qfname("french"));}
    public static String getLastName(){     return Speaker.ask(Language.Qlname("french"));}
    public static String getMail(){         return Speaker.ask(Language.Qmail("french"));}
    public static String getPassword(){     return Speaker.ask(Language.Qpassword("french"));}
    public static String getId(){           return Speaker.ask(Language.Qid("french"));}
    public static String getEnterprise(){   return Speaker.ask(Language.Qenterprise("french"));}
}
