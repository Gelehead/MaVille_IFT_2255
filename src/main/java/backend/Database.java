package backend;

import java.util.ArrayList;
import java.util.Hashtable;

import com.github.javafaker.Faker;

import Instances.*;

public class Database implements java.io.Serializable {
    
    // users are registered and saved via their email, most unique and clear identification method
    private static Hashtable<String, User> userHashtable = new Hashtable<>();
    private static Hashtable<String, Intervenant> intervenantHashtable = new Hashtable<>();
    private static Hashtable<String, Resident> residentHashtable = new Hashtable<>();


    public enum District_name{

    }

    public Database(){
        init();
    }

    public Database(int mockIntervenants, int mockResidents, int mockUsers){
        init(mockUsers, mockIntervenants, mockResidents);
    }

    /**
     * 
     * @param u
     */
    public void addUser(User u){
        userHashtable.put(u.getMail(), u);
    }

    /**
     * 
     * @param i Intervenant
     */
    public void addIntervenant(Intervenant i){
        userHashtable.put(i.getMail(), i);
        intervenantHashtable.put(i.getMail(), i);
    }

    /**
     * 
     * @param r resident
     */
    public void addResident(Resident r){
        userHashtable.put(r.getMail(), r);
        residentHashtable.put(r.getMail(), r);
    }

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

    public boolean authentify(String mail, String pw){return userHashtable.get(mail).getPw().equals(pw);}
    public boolean exists(String mail){return userHashtable.containsKey(mail);}

    private void init(){
        Faker faker = new Faker();
        for (int i = 0; i < 3; i++) {
            addUser(new User(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.internet().emailAddress(), 
                faker.internet().password()
            ));
        }

        for (int i = 0; i < 3; i++) {
            addResident(new Resident(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.internet().emailAddress(), 
                faker.internet().password(),
                Integer.parseInt(faker.phoneNumber().phoneNumber()),
                faker.address().fullAddress().toLowerCase(),
                (int) faker.date().birthday().getTime()
            ));
        }

        for (int i = 0; i < 3; i++) {
            addIntervenant(new Intervenant(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.internet().emailAddress(), 
                faker.internet().password(),
                faker.funnyName().toString()
            ));
        }
    }

    // idk if this is useful but still cool to have
    private void init(int mockUsers, int mockIntervenants, int mockResidents){
        Faker faker = new Faker();
        for (int i = 0; i < mockUsers; i++) {
            addUser(new User(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.internet().emailAddress(), 
                faker.internet().password()
            ));
        }

        for (int i = 0; i < mockResidents; i++) {
            addResident(new Resident(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.internet().emailAddress(), 
                faker.internet().password(),
                Integer.parseInt(faker.phoneNumber().phoneNumber()),
                faker.address().fullAddress().toLowerCase(),
                (int) faker.date().birthday().getTime()
            ));
        }

        for (int i = 0; i < mockUsers; i++) {
            addIntervenant(new Intervenant(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.internet().emailAddress(), 
                faker.internet().password(),
                faker.funnyName().toString()
            ));
        }
    }
}
