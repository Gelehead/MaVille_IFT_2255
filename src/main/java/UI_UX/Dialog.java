package UI_UX;
import Users.*;
import Utils.*;
import Utils.Language.language;;

// TODO : change every dialog option to be be dependant of language chosen by the user


public class Dialog {
    public static Language.language choice_language = language.FRENCH;

    /* ------------------------ Graphic part ------------------------ */

    public static void init(){
        Speaker.welcome();

        Speaker.STATE state = Speaker.STATE.INITIAL;
        while (state != Speaker.STATE.QUIT) {

            // after each menu interaction, the state changes and a new menu is handled
            state = Speaker.menu(state);
        }
    }




    /* ------------------------ Dialog Part ------------------------ */

    // this only works if there are only 2 types of user, if scope gets bigger, replace with switch case
    public static User getUserInfos(String userType){
        return userType == "resident" ? getResidentInfos() : getIntervenantInfos();
    }

    /** Asks the user, assuming they are a resident, all useful information
     * @return Resident object
     */
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

    /** Asks the user information related to its role as an intervenant
     * @return Intervenant with all pertinent informations
     */
    public static Intervenant getIntervenantInfos(){
        String[] mI = getMainInfos();

        String id = getId();
        String enterprise = getEnterprise();

        return new Intervenant(mI[0], mI[1], mI[2], mI[3], enterprise, id);
    }
    
    // getters
    public static String getfirstName(){    return Speaker.ask(Language.Qfname(choice_language));}
    public static String getLastName(){     return Speaker.ask(Language.Qlname(choice_language));}
    public static String getMail(){         return Speaker.ask(Language.Qmail(choice_language));}
    public static String getPassword(){     return Speaker.ask(Language.Qpassword(choice_language));}
    public static String getId(){           return Speaker.ask(Language.Qid(choice_language));}
    public static String getEnterprise(){   return Speaker.ask(Language.Qenterprise(choice_language));}
    public static int getPhoneNum(){        return Integer.parseInt(Speaker.ask(Language.Qphone(choice_language)));}
    public static String getAddress(){      return Speaker.ask(Language.Qaddress(choice_language));}
    public static int getBirthDay(){        return Integer.parseInt(Speaker.ask(Language.Qbirthday(choice_language)));}
}
