package backend;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Random;

import com.github.javafaker.Faker;

import Instances.*;
import Instances.Intervenant.InType;
import Instances.User.Type;
import UI_UX.Dialog;
import Utils.GeoJSON;
import Utils.Language;
import Utils.Parser;
import Utils.GeoJSON.Feature;
import Utils.Geocoding;
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
    public String geoJSONfilePath = "extern/data/limites-administratives-agglomeration-nad83.geojson";

    public enum District_name {
        LaSalle,
        Dollard_des_Ormeaux,
        Côte_Saint_Luc,
        Villeray_Saint_Michel_Parc_Extension,
        Rosemont_La_Petite_Patrie,
        Hampstead,
        Senneville,
        Le_Plateau_Mont_Royal,
        Sainte_Anne_de_Bellevue,
        Montreal_Ouest,
        Cote_des_Neiges_Notre_Dame_de_Grace,
        Ile_Bizard_Sainte_Genevieve,
        Beaconsfield,
        Anjou,
        Verdun,
        Le_Sud_Ouest,
        Mercier_Hochelaga_Maisonneuve,
        Montreal_Est,
        Lachine,
        Saint_Leonard,
        Montreal_Nord,
        Outremont,
        Ile_Dorval,
        Mont_Royal,
        Pointe_Claire,
        Dorval,
        Pierrefonds_Roxboro,
        Riviere_des_Prairies_Pointe_aux_Trembl,
        Ahuntsic_Cartierville,
        Saint_Laurent,
        Ville_Marie,
        Kirkland,
        Baie_D_Urfe,
        Westmount
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
    public District getDistrict(District_name name){return districtHashtable.get(name);}

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
        GeoJSON geoJSON = Parser.getgeojson(geoJSONfilePath);
        for (Feature f : geoJSON.getFeatures()) {
            District d = new District(
                toDistrict_name(f.getProperties().getNom()),
                f.getGeometry(),
                f.getProperties().getCodeMamh()
            );
            districtHashtable.put(toDistrict_name(f.getProperties().getNom()), d);
        }
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

    public static District getDistrict(String address) {
        try {
            // Get the coordinates of the address
            double[] coordinates = Geocoding.getCoordinates(address);
            double latitude = coordinates[0];
            double longitude = coordinates[1];

            // Iterate through all districts in the database to check which district contains the point
            for (District d : districtHashtable.values()) {
                if (d.getGeometry().contains(latitude, longitude)) {
                    return d; // Return the district if it contains the point
                }
            }

            // If no district contains the point, return null or throw an exception
            return null; // or throw new IllegalArgumentException("No district found for the given address.");
        } catch (Exception e) {
            // Handle errors, such as invalid address or API failure
            e.printStackTrace();
            return null;
        }
    }

    private District_name toDistrict_name(String s) {
        switch (s) {
            case "LaSalle":                              return District_name.LaSalle;
            case "Dollard-des-Ormeaux":                  return District_name.Dollard_des_Ormeaux;
            case "Côte-Saint-Luc":                       return District_name.Côte_Saint_Luc;
            case "Villeray-Saint-Michel-Parc-Extension": return District_name.Villeray_Saint_Michel_Parc_Extension;
            case "Rosemont-La Petite-Patrie":            return District_name.Rosemont_La_Petite_Patrie;
            case "Hampstead":                            return District_name.Hampstead;
            case "Senneville":                           return District_name.Senneville;
            case "Le Plateau-Mont-Royal":                return District_name.Le_Plateau_Mont_Royal;
            case "Sainte-Anne-de-Bellevue":              return District_name.Sainte_Anne_de_Bellevue;
            case "Montréal-Ouest":                       return District_name.Montreal_Ouest;
            case "Côte-des-Neiges-Notre-Dame-de-Grâce":  return District_name.Cote_des_Neiges_Notre_Dame_de_Grace;
            case "L'Île-Bizard-Sainte-Geneviève":        return District_name.Ile_Bizard_Sainte_Genevieve;
            case "Beaconsfield":                         return District_name.Beaconsfield;
            case "Anjou":                                return District_name.Anjou;
            case "Verdun":                               return District_name.Verdun;
            case "Le Sud-Ouest":                         return District_name.Le_Sud_Ouest;
            case "Mercier-Hochelaga-Maisonneuve":        return District_name.Mercier_Hochelaga_Maisonneuve;
            case "Montréal-Est":                         return District_name.Montreal_Est;
            case "Lachine":                              return District_name.Lachine;
            case "Saint-Léonard":                        return District_name.Saint_Leonard;
            case "Montréal-Nord":                        return District_name.Montreal_Nord;
            case "Outremont":                            return District_name.Outremont;
            case "L'Île-Dorval" :                        return District_name.Ile_Dorval;
            case "Mont-Royal":                           return District_name.Mont_Royal;
            case "Pointe-Claire":                        return District_name.Pointe_Claire;
            case "Dorval":                               return District_name.Dorval;
            case "Pierrefonds-Roxboro":                  return District_name.Pierrefonds_Roxboro;
            case "Rivière-des-Prairies-Pointe-aux-Trembles": return District_name.Riviere_des_Prairies_Pointe_aux_Trembl;
            case "Ahuntsic-Cartierville":                return District_name.Ahuntsic_Cartierville;
            case "St-Laurent":                        return District_name.Saint_Laurent;
            case "Saint-Laurent":                        return District_name.Saint_Laurent;
            case "Ville-Marie":                          return District_name.Ville_Marie;
            case "Kirkland":                             return District_name.Kirkland;
            case "Baie-D'Urfé":                          return District_name.Baie_D_Urfe;
            case "Westmount":                            return District_name.Westmount;
            default:
                throw new IllegalArgumentException("Unknown district name: " + s);
        }
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
            Resident newRes = new Resident(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.internet().emailAddress(), 
                faker.internet().password(),
                faker.phoneNumber().phoneNumber().intern(),
                faker.address().fullAddress().toLowerCase(),
                (int) faker.date().birthday().getTime()
            );
            newRes.getSchedule().generateMockSchedule();
            addResident(newRes);
        }

        for (int i = 0; i < mockUsers; i++) {
            addIntervenant(new Intervenant(
                faker.name().firstName(), 
                faker.name().lastName(), 
                faker.internet().emailAddress(), 
                faker.internet().password(),
                intypeRoulette(),
                new Random().nextInt(100000000)
            ));
        }

        addAdmin(new Admin(null, null, null, null, "Herobrine"));
    }

    public void send_notif(District_name project_district, Project project) {
        for (Resident r : getResidentList()){
            if ( r.geDistrict() == project_district){
                
            }
        }
    }

    private InType intypeRoulette(){
        int n = new Random().nextInt(4);
        switch (n) {
            case 0: return InType.Individual;
            case 1: return InType.Private_entrepreneur;
            case 2: return InType.Public_enterprise;
            default: return InType.Unhandled;
        }
    }

    public Resident getActiveUser_Resident() {return (Resident) activeUser;}
    public Intervenant getActiveUser_Intervenant() {return (Intervenant) activeUser;}

    public User getActiveUser() {return activeUser;}

    //setters
    public void setActiveUser(User activeUser) {Database.activeUser = activeUser;}
}
