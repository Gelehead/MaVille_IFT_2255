import Utils.Language;
import Utils.Speaker;

// TODO : change every dialog option to be be dependant of language chosen by the user
public class Dialog {

    public static User getUserInfos(String userType){
        return userType == "resident" ? getResidentInfos() : getIntervenantInfos();
    }

    public static Resident getResidentInfos(){
        // mI for main Infos, makes the creation of new resident a shorter line
        String[] mI = getMainInfos();

        int phoneNum = getPhoneNum();
        String address = getAddress();
        int birthDay = getBirthDay();


        return new Resident(mI[0], mI[1], mI[2], mI[3], phoneNum, address, birthDay);
    }

    /** Returns, in order, first name, last name, mail address, password
     * 
     * @return [fname, lname, mail, pw]
     */
    public static String[] getMainInfos(){

        String fname = getfirstName();
        String lname = getLastName();
        String mail = getMail();
        String pw = getPassword();

        String[] mainInfos = {fname, lname, mail, pw};
        return mainInfos;
    }

    public static Intervenant getIntervenantInfos(){
        String[] mI = getMainInfos();

        String id = getId();
        String enterprise = getEnterprise();

        return new Intervenant(mI[0], mI[1], mI[2], mI[3], enterprise, id);
    }
    
    // getters
    public static String getfirstName(){    return Speaker.ask(Language.Qfname("french"));}
    public static String getLastName(){     return Speaker.ask(Language.Qlname("french"));}
    public static String getMail(){         return Speaker.ask(Language.Qmail("french"));}
    public static String getPassword(){     return Speaker.ask(Language.Qpassword("french"));}
    public static String getId(){           return Speaker.ask(Language.Qid("french"));}
    public static String getEnterprise(){   return Speaker.ask(Language.Qenterprise("french"));}
    public static int getPhoneNum(){        return Integer.parseInt(Speaker.ask(Language.Qphone("french")));}
    public static String getAddress(){      return Speaker.ask(Language.Qaddress("french"));}
    public static int getBirthDay(){        return Integer.parseInt(Speaker.ask(Language.Qbirthday("french")));}
}
