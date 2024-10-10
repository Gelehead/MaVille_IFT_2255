import java.util.ArrayList;
import java.util.Hashtable;

import Users.*;

public class Database implements java.io.Serializable {
    private final Hashtable<Intervenant, String> intervenantHashtable;
    private final Hashtable<Resident, String> residentHashtable;

    public Database(){
        intervenantHashtable = new Hashtable<>();
        residentHashtable = new Hashtable<>();
    }

    /**
     * 
     * @param i Intervenant
     */
    public void addIntervenant(Intervenant i){intervenantHashtable.put(i, i.getMail());}
    public void addResident(Resident r){residentHashtable.put(r, r.getMail());}


    /** Method to get Intervenant list from the database
     * 
     * @return Arraylist<Intervenant> intervenantList
     */
    public ArrayList<Intervenant> getIntervenantList(){
        ArrayList<Intervenant> intervenantList = new ArrayList<>();
        for (Intervenant i : intervenantHashtable.keySet()){
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
        for (Resident i : residentHashtable.keySet()){
            residentList.add(i);
        }
        return residentList;
    }
}
