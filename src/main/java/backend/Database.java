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
import Utils.Parser.Impediment;
import Utils.Parser.Record;
import metrics.*;

public class Database implements java.io.Serializable {
    
    // users are registered and saved via their email, most unique and clear identification method
    private static Hashtable<String, User>              userHashtable = new Hashtable<>();
    private static Hashtable<String, Intervenant>       intervenantHashtable = new Hashtable<>();
    private static Hashtable<String, Resident>          residentHashtable = new Hashtable<>();
    private static Hashtable<String, Admin>             adminHashtable = new Hashtable<>();

    private static Hashtable<Integer, Project>          projectHashtable = new Hashtable<>();
    private static Hashtable<Integer, Impediment>       impedimentHashtable = new Hashtable<>();
    private static Hashtable<District_name, District>   districtHashtable = new Hashtable<>();
    private static Hashtable<Long, Request>             requestHashtable = new Hashtable<>();

    private static User activeUser;
    // JSON URL to get the ongoing projects
    String projectsURL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    String impedimentsURL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";

    public enum District_name{
        placeholder
    }

    public Database(){
        init();
    }

    public Database(int mockIntervenants, int mockResidents, int mockUsers){
        init(mockUsers, mockIntervenants, mockResidents);
    }

    public void printAll(Type utype){
        switch (utype) {
            case USER: 
                for (String uk : userHashtable.keySet()) {
                    System.out.println(userHashtable.get(uk).toString());
                }
                break;
            case RESIDENT :
                for (String rk : residentHashtable.keySet()) {
                    System.out.println(residentHashtable.get(rk).toString());
                }
                break;
            case INTERVENANT :
                for (String ik : intervenantHashtable.keySet()) {
                    System.out.println(intervenantHashtable.get(ik).toString());
                }
                break;
            case PROJECT :
                for (int pid : projectHashtable.keySet()) {
                    System.out.println(projectHashtable.get(pid).toString());
                }
                break;
            case IMPEDIMENT:
                for (int iid : impedimentHashtable.keySet()) {
                    System.out.println(projectHashtable.get(iid).toString());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 
     * @param u User
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

    /**
     * 
     * @param r request
     */
    public void addRequest(Request r){
        requestHashtable.put(r.getId(), r);
    }

    // TODO: placeholder, change when time allows
    public District getDistrict(District_name name){return new District(name);}

    public Request getRequest(long id){return requestHashtable.get(id);}
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

        /**
     * 
     * @return ArrayList<Project> projectlist
     */
    public ArrayList<Project> getProjectList(){
        ArrayList<Project> projecList = new ArrayList<>();
        for (Project p : projectHashtable.values()){
            projecList.add(p);
        }
        return projecList;
    }

    /**
     * 
     * @return ArrayList<Resident> residentList
     */
    public ArrayList<Impediment> getImpedimentList(){
        ArrayList<Impediment> impedimenList = new ArrayList<>();
        for (Impediment i : impedimentHashtable.values()){
            impedimenList.add(i);
        }
        return impedimenList;
    }

    public ArrayList<Request> getRequestList(){
        ArrayList<Request> requestList = new ArrayList<>();
        for (Request r : requestHashtable.values()){
            requestList.add(r);
        }
        return requestList;
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

    // TODO: complete placeholder
    private void init_districts(){
        districtHashtable.put(District_name.placeholder, new District(District_name.placeholder));
    }

    private void init_records(){
        for (Parser.Record record : Parser.getRecords(projectsURL)) {
            Project project = toProject(record);
            projectHashtable.put(project.id, project);
        }
    }

    private void init_impediments(){
        for (Parser.Impediment rimp : Parser.getImpediments(impedimentsURL)){
            impedimentHashtable.put(rimp.id, rimp);
        }
    }

    // TODO: placeholder 
    private District_name toDistrict_name(String s){
        return District_name.placeholder;
    }

    private Project toProject(Record record){
        Coordinates co = new Coordinates(
            record.longitude == null ? 0 : Double.parseDouble(record.longitude), 
            record.longitude == null ? 0 : Double.parseDouble(record.latitude)
        );
        return new Project(
            record.id,
            record.official_id,
            record.permit_permit_id,
            record.permitcategory,
            record.contractnumber,
            record.reason_category,
            "no given title",
            districtHashtable.get(toDistrict_name(record.boroughid)),
            record.duration_start_date,
            record.duration_end_date,
            record.occupancy_name,
            record.organizationname,
            record.submittercategory,
            co
        );
    }

    /** Usage of Faker library to ease the false information creation
     *  
     */
    private void init(){
        init(10, 3, 7);
    }

    // real init function
    private void init(int mockUsers, int mockIntervenants, int mockResidents){
        init_records();
        init_districts();
        init_impediments();

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
                faker.phoneNumber().phoneNumber().intern(),
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
