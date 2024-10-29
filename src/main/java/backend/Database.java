package backend;

import java.util.ArrayList;
import java.util.Hashtable;

import Users.*;

public class Database implements java.io.Serializable {
    
    // users are registered and saved via their email, most unique and clear identification method
    private static final Hashtable<String, Intervenant> intervenantHashtable = new Hashtable<>();
    private static final Hashtable<String, Resident> residentHashtable = new Hashtable<>();


    public enum District_name{

    }

    public Database(){
    }

    /**
     * 
     * @param i Intervenant
     */
    public static void addIntervenant(Intervenant i){intervenantHashtable.put(i.getMail(), i);}

    /**
     * 
     * @param r resident
     */
    public static void addResident(Resident r){residentHashtable.put(r.getMail(), r);}

    public Resident getResident(String mail){return residentHashtable.get(mail);}


    /** Method to get Intervenant list from the database
     * 
     * @return Arraylist<Intervenant> intervenantList
     */
    public ArrayList<Intervenant> getIntervenantList(){
        ArrayList<Intervenant> intervenantList = new ArrayList<>();
        for (Intervenant i : intervenantHashtable.values()){
            intervenantList.add(i);
        }
        return intervenantList;
    }

    /**
     * 
     * @return ArrayList<Resident> residentList
     */
    public ArrayList<Resident> getResidentList(){
        ArrayList<Resident> residentList = new ArrayList<>();
        for (Resident i : residentHashtable.values()){
            residentList.add(i);
        }
        return residentList;
    }
}
