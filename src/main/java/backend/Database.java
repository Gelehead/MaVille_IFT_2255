package backend;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import com.github.javafaker.Faker;

import Instances.*;
import Instances.User.Type;
import UI_UX.Dialog;
import Utils.Language;
import Utils.Parser;

public class Database implements java.io.Serializable {
    
    // users are registered and saved via their email, most unique and clear identification method
    private static Hashtable<String, User>              userHashtable = new Hashtable<>();
    private static Hashtable<String, Intervenant>       intervenantHashtable = new Hashtable<>();
    private static Hashtable<String, Resident>          residentHashtable = new Hashtable<>();
    private static Hashtable<String, Admin>             adminHashtable = new Hashtable<>();

    private static Hashtable<Integer, Project>          projectHashtable = new Hashtable<>();
    private static Hashtable<District_name, District>   districtHashtable = new Hashtable<>();

    private static User activeUser;
    // JSON URL to get the ongoing projects
    String ongoingProjectsURL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";

    public enum District_name{

    }

    public Database(){
        init(3, 3, 3);
    }

    public Database(int mockIntervenants, int mockResidents, int mockUsers){
        init(mockUsers, mockIntervenants, mockResidents);
        for (Parser.Record project : Parser.getRecords(ongoingProjectsURL)) {
            
        }
        Parser.getRecords(ongoingProjectsURL);
    }

    public void printAll(Type utype){
        switch (utype) {
            case USER: 
                break;
            case RESIDENT :
            case INTERVENANT : 
            default:
                break;
        }
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

    /**
     * 
     * @param a admin
     */
    public void addAdmin(Admin a){
        adminHashtable.put(a.getUsername(), a);
    }

    /**
     * 
     * @param p project
     */
    public void addProject(Project p){
        projectHashtable.put(p.id, p);
    }

    public District getDistrict(District_name name){return }

    public Resident getResident(String mail){return residentHashtable.get(mail);}
    public Intervenant getIntervenant(String mail){return intervenantHashtable.get(mail);}
    public User getUser(String mail){return userHashtable.get(mail);}


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

    public boolean authentify(String mail, String pw){
        return userHashtable.get(mail).getPw().equals(pw);
    }
    public boolean exists(String mail){
        return userHashtable.containsKey(mail);
    }
    public User.Type userType(User user){
        if (!exists(user.getMail())){throw new NoSuchElementException(Language.noSuchUser(Dialog.choice_language, user.getMail()));}
        return user.type;
    }

    /** Usage of Faker library to ease the false information creation
     * 
     */
    private void init(){
        init(10, 3, 7);
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

        addAdmin(new Admin(null, null, null, null, "Herobrine"));
    }

    //setters
    public void setActiveUser(User activeUser) {Database.activeUser = activeUser;}
    public User getActiveUser() {return activeUser;}
}
