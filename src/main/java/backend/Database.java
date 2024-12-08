package backend;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Random;

import com.github.javafaker.Faker;

import Instances.*;
import Instances.Intervenant.InType;
import Instances.Project.Progress;
import Instances.Project.Reason;
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

/**
 * Classe Database qui implémente Serializable pour la gestion de la base de données.
 * Elle contient les tables de hachage pour les utilisateurs, intervenants, résidents, administrateurs, projets, entraves, arrondissements et requêtes.
 */
public class Database implements java.io.Serializable {
    
    /**
     * Hashtable pour stocker tous les utilisateurs avec leur email comme clé.
     */
    private static Hashtable<String, User> userHashtable = new Hashtable<>();
    
    /**
     * Hashtable pour stocker les intervenants avec leur email comme clé.
     */
    private static Hashtable<String, Intervenant> intervenantHashtable = new Hashtable<>();
    
    /**
     * Hashtable pour stocker les résidents avec leur email comme clé.
     */
    private static Hashtable<String, Resident> residentHashtable = new Hashtable<>();
    
    /**
     * Hashtable pour stocker les administrateurs avec leur nom d'utilisateur comme clé.
     */
    private static Hashtable<String, Admin> adminHashtable = new Hashtable<>();

    /**
     * Hashtable pour stocker les projets avec leur ID comme clé.
     */
    private static Hashtable<Integer, Project> projectHashtable = new Hashtable<>();
    
    /**
     * Hashtable pour stocker les entraves avec leur ID comme clé.
     */
    private static Hashtable<Integer, Impediment> impedimentHashtable = new Hashtable<>();
    
    /**
     * Hashtable pour stocker les arrondissements avec leur nom comme clé.
     */
    private static Hashtable<District_name, District> districtHashtable = new Hashtable<>();
    
    /**
     * Hashtable pour stocker les requêtes avec leur ID comme clé.
     */
    private static Hashtable<Long, Request> requestHashtable = new Hashtable<>();

    /**
     * Utilisateur actuellement actif (connexion en cours).
     */
    private static User activeUser;
    
    /**
     * URL JSON pour obtenir les projets en cours.
     */
    String projectsURL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    
    /**
     * URL JSON pour obtenir les entraves.
     */
    String impedimentsURL = "https://donnees.montreal.ca/api/3/action/datastore_search?resource_id=a2bc8014-488c-495d-941b-e7ae1999d1bd";
    
    /**
     * Chemin du fichier GeoJSON contenant les limites administratives de l'agglomération.
     */
    public String geoJSONfilePath = "extern/data/limites-administratives-agglomeration-nad83.geojson";

    /**
     * Enumération des noms des arrondissements.
     */
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

    /**
     * Constructeur par défaut de la classe Database.
     * Initialise la base de données avec des valeurs par défaut.
     */
    public Database(){
        init();
    }

    /**
     * Constructeur de la classe Database avec paramètres pour le mock.
     * @param mockIntervenants Nombre d'intervenants à générer pour le mock.
     * @param mockResidents Nombre de résidents à générer pour le mock.
     * @param mockUsers Nombre d'utilisateurs à générer pour le mock.
     */
    public Database(int mockIntervenants, int mockResidents, int mockUsers){
        init(mockUsers, mockIntervenants, mockResidents);
    }

    /**
     * Affiche toutes les instances d'un type donné dans la base de données.
     * @param utype Type d'utilisateur à afficher.
     */
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
     * Ajoute un utilisateur à la table de hachage des utilisateurs.
     * @param u L'utilisateur à ajouter.
     */
    public void addUser(User u){
        userHashtable.put(u.getMail(), u);
    }

    /**
     * Ajoute un intervenant à la table de hachage des intervenants et des utilisateurs.
     * @param i L'intervenant à ajouter.
     */
    public void addIntervenant(Intervenant i){
        userHashtable.put(i.getMail(), i);
        intervenantHashtable.put(i.getMail(), i);
    }

    /**
     * Ajoute un résident à la table de hachage des résidents et des utilisateurs.
     * @param r Le résident à ajouter.
     */
    public void addResident(Resident r){
        userHashtable.put(r.getMail(), r);
        residentHashtable.put(r.getMail(), r);
    }

    /**
     * Ajoute un administrateur à la table de hachage des administrateurs.
     * @param a L'administrateur à ajouter.
     */
    public void addAdmin(Admin a){
        adminHashtable.put(a.getUsername(), a);
    }

    /**
     * Ajoute un projet à la table de hachage des projets.
     * @param p Le projet à ajouter.
     */
    public void addProject(Project p){
        projectHashtable.put(p.id, p);
    }

    /**
     * Ajoute une requête à la table de hachage des requêtes.
     * @param r La requête à ajouter.
     */
    public void addRequest(Request r){
        requestHashtable.put(r.getId(), r);
    }

    /**
     * Récupère un arrondissement par son nom.
     * @param name Le nom de l'arrondissement.
     * @return L'arrondissement correspondant.
     */
    public District getDistrict(District_name name){
        return districtHashtable.get(name);
    }

    /**
     * Récupère une requête par son identifiant.
     * @param id L'identifiant de la requête.
     * @return La requête correspondante.
     */
    public Request getRequest(long id){
        return requestHashtable.get(id);
    }

    /**
     * Récupère un résident par son email.
     * @param mail L'email du résident.
     * @return Le résident correspondant.
     */
    public Resident getResident(String mail){
        return residentHashtable.get(mail);
    }

    /**
     * Récupère un intervenant par son email.
     * @param mail L'email de l'intervenant.
     * @return L'intervenant correspondant.
     */
    public Intervenant getIntervenant(String mail){
        return intervenantHashtable.get(mail);
    }

    /**
     * Récupère un utilisateur par son email.
     * @param mail L'email de l'utilisateur.
     * @return L'utilisateur correspondant.
     */
    public User getUser(String mail){
        return userHashtable.get(mail);
    }

    /**
     * Récupère la liste des intervenants.
     * @return Une ArrayList d'intervenants.
     */
    public ArrayList<Intervenant> getIntervenantList(){
        ArrayList<Intervenant> intervenantList = new ArrayList<>();
        for (Intervenant i : intervenantHashtable.values()){
            intervenantList.add(i);
        }
        return intervenantList;
    }

    /**
     * Récupère la liste des résidents.
     * @return Une ArrayList de résidents.
     */
    public ArrayList<Resident> getResidentList(){
        ArrayList<Resident> residentList = new ArrayList<>();
        for (Resident i : residentHashtable.values()){
            residentList.add(i);
        }
        return residentList;
    }

    /**
     * Récupère la liste des projets.
     * @return Une ArrayList de projets.
     */
    public ArrayList<Project> getProjectList(){
        ArrayList<Project> projecList = new ArrayList<>();
        for (Project p : projectHashtable.values()){
            projecList.add(p);
        }
        return projecList;
    }

    /**
     * Récupère la liste des arrondissements.
     * @return Une ArrayList d'arrondissements.
     */
    public ArrayList<District> getDistrictList(){
        ArrayList<District> districtList = new ArrayList<>();
        for (District d : districtHashtable.values()) {
            districtList.add(d);
        }
        return districtList;
    }

    /**
     * Récupère les projets par raison spécifiée.
     * @param reason La raison du projet.
     * @return Une ArrayList de projets correspondant.
     * @throws NoSuchElementException Si aucun projet n'est trouvé.
     */
    public ArrayList<Project> getProjectsBy(Reason reason){
        ArrayList<Project> byReasonList = new ArrayList<>();
        for (Project p : getProjectList()) {
            if (p.getReason() == reason){
                byReasonList.add(p);
            }
        }
        if (!byReasonList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byReasonList;
    }

    /**
     * Récupère les projets dans un arrondissement spécifié.
     * @param district L'arrondissement du projet.
     * @return Une ArrayList de projets correspondant.
     * @throws NoSuchElementException Si aucun projet n'est trouvé.
     */
    public ArrayList<Project> getProjectsBy(District district){
        ArrayList<Project> byDistrictList = new ArrayList<>();
        for (Project p : getProjectList()) {
            if (p.getDistrict() == district){
                byDistrictList.add(p);
            }
        }
        if (!byDistrictList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byDistrictList;
    }

    /**
     * Récupère les projets par titre.
     * @param title Le titre du projet.
     * @return Une ArrayList de projets correspondant.
     * @throws NoSuchElementException Si aucun projet n'est trouvé.
     */
    public ArrayList<Project> getProjectsBy(String title){
        ArrayList<Project> byList = new ArrayList<>();
        for (Project p : getProjectList()) {
            if (p.getTitle().contains(title)){
                byList.add(p);
            }
        }
        if (!byList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byList;
    }

    /**
     * Récupère les projets par statut de progression.
     * @param progress Le statut de progression.
     * @return Une ArrayList de projets correspondant.
     * @throws NoSuchElementException Si aucun projet n'est trouvé.
     */
    public ArrayList<Project> getProjectsBy(Progress progress){
        ArrayList<Project> byList = new ArrayList<>();
        for (Project p : getProjectList()) {
            if (p.getStatus() == progress){
                byList.add(p);
            }
        }
        if (!byList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byList;
    }

    /**
     * Récupère les projets actifs à une date donnée.
     * @param activeDate La date d'activité.
     * @return Une ArrayList de projets correspondant.
     * @throws NoSuchElementException Si aucun projet n'est trouvé.
     */
    public ArrayList<Project> getProjectsBy(Date activeDate){
        ArrayList<Project> byList = new ArrayList<>();
        for (Project p : getProjectList()) {
            if (Date.after(activeDate, p.getStart_date()) && Date.before(activeDate, p.getEnd_date())){
                byList.add(p);
            }
        }
        if (!byList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byList;
    }

    /**
     * Récupère la liste des entraves.
     * @return Une ArrayList d'entraves.
     */
    public ArrayList<Impediment> getImpedimentList(){
        ArrayList<Impediment> impedimenList = new ArrayList<>();
        for (Impediment i : impedimentHashtable.values()){
            impedimenList.add(i);
        }
        return impedimenList;
    }

    /**
     * Récupère la liste des requêtes.
     * @return Une ArrayList de requêtes.
     */
    public ArrayList<Request> getRequestList(){
        ArrayList<Request> requestList = new ArrayList<>();
        for (Request r : requestHashtable.values()){
            requestList.add(r);
        }
        return requestList;
    }

    /**
     * Récupère les requêtes par raison spécifiée.
     * @param reason La raison de la requête.
     * @return Une ArrayList de requêtes correspondant.
     * @throws NoSuchElementException Si aucune requête n'est trouvée.
     */
    public ArrayList<Request> getRequestsBy(Reason reason){
        ArrayList<Request> byReasonList = new ArrayList<>();
        for (Request r : getRequestList()) {
            if (r.getReason() == reason){
                byReasonList.add(r);
            }
        }
        if (!byReasonList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byReasonList;
    }

    /**
     * Récupère les requêtes par titre.
     * @param title Le titre de la requête.
     * @return Une ArrayList de requêtes correspondant.
     * @throws NoSuchElementException Si aucune requête n'est trouvée.
     */
    public ArrayList<Request> getRequestsBy(String title){
        ArrayList<Request> byReasonList = new ArrayList<>();
        for (Request r : getRequestList()) {
            if (r.getTitle().contains(title)){
                byReasonList.add(r);
            }
        }
        if (!byReasonList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byReasonList;
    }

    /**
     * Récupère les requêtes par statut de progression.
     * @param progress Le statut de progression.
     * @return Une ArrayList de requêtes correspondant.
     * @throws NoSuchElementException Si aucune requête n'est trouvée.
     */
    public ArrayList<Request> getRequestsBy(Progress progress){
        ArrayList<Request> byReasonList = new ArrayList<>();
        for (Request r : getRequestList()) {
            if (r.getProgress() == progress){
                byReasonList.add(r);
            }
        }
        if (!byReasonList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byReasonList;
    }

    /**
     * Récupère les requêtes dans un arrondissement spécifié.
     * @param district L'arrondissement de la requête.
     * @return Une ArrayList de requêtes correspondant.
     * @throws NoSuchElementException Si aucune requête n'est trouvée.
     */
    public ArrayList<Request> getRequestsBy(District district){
        ArrayList<Request> byReasonList = new ArrayList<>();
        for (Request r : getRequestList()) {
            if (r.getDistrict() == district){
                byReasonList.add(r);
            }
        }
        if (!byReasonList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byReasonList;
    }

    /**
     * Récupère les requêtes actives à une date donnée.
     * @param activeDate La date d'activité.
     * @return Une ArrayList de requêtes correspondant.
     * @throws NoSuchElementException Si aucune requête n'est trouvée.
     */
    public ArrayList<Request> getRequestsBy(Date activeDate){
        ArrayList<Request> byList = new ArrayList<>();
        for (Request r : getRequestList()) {
            if (Date.after(activeDate, r.getStart()) && Date.before(activeDate, r.getEnd())){
                byList.add(r);
            }
        }
        if (!byList.isEmpty()) {
            throw new NoSuchElementException(Language.no_project_found(Dialog.choice_language));
        }
        return byList;
    }

    /**
     * Authentifie un utilisateur en vérifiant son email et mot de passe.
     * @param mail L'email de l'utilisateur.
     * @param pw Le mot de passe de l'utilisateur.
     * @return true si l'authentification réussit, false sinon.
     */
    public boolean authentify(String mail, String pw) {
        User user = userHashtable.get(mail);
        if (user == null) {
            System.err.println("User not found with email: " + mail); // Log the error clearly
            return false; // Indicate authentication failure
        }
        return user.getPw().equals(pw); // Now safe to call .getPw()
    }

    /**
     * Vérifie si un utilisateur existe dans la base de données.
     * @param mail L'email de l'utilisateur.
     * @return true si l'utilisateur existe, false sinon.
     */
    public boolean exists(String mail){
        return userHashtable.containsKey(mail);
    }

    /**
     * Récupère le type d'un utilisateur.
     * @param user L'utilisateur concerné.
     * @return Le type de l'utilisateur.
     * @throws NoSuchElementException Si l'utilisateur n'existe pas.
     */
    public User.Type userType(User user){
        if (!exists(user.getMail())){
            throw new NoSuchElementException(Language.noSuchUser(Dialog.choice_language, user.getMail()));
        }
        return user.type;
    }

    /**
     * Initialise les arrondissements à partir d'un fichier GeoJSON.
     */
    private void init_districts(){
        // Modifier pour le jar (chemin relatif)
        GeoJSON geoJSON = Parser.getgeojson("data/limites-administratives-agglomeration-nad83.geojson");
        for (Feature f : geoJSON.getFeatures()) {
            District d = new District(
                toDistrict_name(f.getProperties().getNom()),
                f.getGeometry(),
                f.getProperties().getCodeMamh(),
                f.getProperties().getType(),
                f.getProperties().getCodeId(),
                f.getProperties().getCode3C()
            );
            districtHashtable.put(toDistrict_name(f.getProperties().getNom()), d);
        }
    }

    /**
     * Initialise les enregistrements des projets à partir d'une URL JSON.
     */
    private void init_records(){
        for (Parser.Record record : Parser.getRecords(projectsURL)) {
            Project project = toProject(record);
            projectHashtable.put(project.id, project);
        }
    }

    /**
     * Initialise les entraves à partir d'une URL JSON.
     */
    private void init_impediments(){
        for (Parser.Impediment rimp : Parser.getImpediments(impedimentsURL)){
            impedimentHashtable.put(rimp.id, rimp);
        }
    }

    /**
     * Récupère l'arrondissement correspondant à une adresse donnée.
     * @param address L'adresse à rechercher.
     * @return L'arrondissement correspondant ou null si non trouvé.
     */
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

    /**
     * Convertit une chaîne de caractères en District_name.
     * @param s La chaîne à convertir.
     * @return Le District_name correspondant.
     * @throws IllegalArgumentException Si le nom de l'arrondissement est inconnu.
     */
    public District_name toDistrict_name(String s) {
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
            case "St-Laurent":                           return District_name.Saint_Laurent;
            case "Saint-Laurent":                        return District_name.Saint_Laurent;
            case "Ville-Marie":                          return District_name.Ville_Marie;
            case "Kirkland":                             return District_name.Kirkland;
            case "Baie-D'Urfé":                          return District_name.Baie_D_Urfe;
            case "Westmount":                            return District_name.Westmount;
            default:
                throw new IllegalArgumentException("Unknown district name: " + s);
        }
    }

    /**
     * Convertit un enregistrement Record en objet Project.
     * @param record L'enregistrement à convertir.
     * @return Le projet correspondant.
     */
    private Project toProject(Record record){
        Coordinates co = new Coordinates(
            record.longitude == null ? 0 : Double.parseDouble(record.longitude), 
            record.latitude == null ? 0 : Double.parseDouble(record.latitude)
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

    /**
     * Initialise la base de données avec des valeurs par défaut.
     */
    private void init(){
        init(10, 3, 7);
    }

    /**
     * Initialise la base de données avec des valeurs mock pour les tests.
     * @param mockUsers Nombre d'utilisateurs mock.
     * @param mockIntervenants Nombre d'intervenants mock.
     * @param mockResidents Nombre de résidents mock.
     */
    private void init(int mockUsers, int mockIntervenants, int mockResidents){
        init_districts();
        init_records();
        init_impediments();

        ////////////////////////////  INITIALIZATION DE LA BASE DE DONNÉES  //////////////////////

        // Ajouter 5 résidents
        Resident r1 = new Resident("Alice", "Benoit", "alice@example.com", "password123", "514-888-0000", "123 Rue Udem, Montréal", 12345);
        Resident r2 = new Resident("Julie", "Tremblay", "julie@example.com", "password123", "514-888-0001", "234 Rue Udem, Montréal", 23456);
        Resident r3 = new Resident("Marc", "Lafleur", "marc@example.com", "password123", "514-888-0002", "345 Rue Udem, Montréal", 34567);
        Resident r4 = new Resident("Sophie", "Roy", "sophie@example.com", "password123", "514-888-0003", "456 Rue Udem, Montréal", 45678);
        Resident r5 = new Resident("Chantal", "Gagnon", "chantal@example.com", "password123", "514-888-0004", "234 Rue Udem, Montréal", 56789);

        r2.setDistrict(Database.District_name.LaSalle);
        r3.setDistrict(Database.District_name.LaSalle);
        r1.setDistrict(Database.District_name.Verdun);
        r4.setDistrict(Database.District_name.Montreal_Nord);
        r5.setDistrict(Database.District_name.Anjou);

        addResident(r1);
        addResident(r2);
        addResident(r3);
        addResident(r4);
        addResident(r5);

        // 5 intervenants avec types variés
        Intervenant i1 = new Intervenant("Carlos", "Durand", "carlos@example.com", "password123", Intervenant.InType.Private_entrepreneur, 11111111);
        Intervenant i2 = new Intervenant("Pierre", "Gauthier", "pierre@example.com", "password123", Intervenant.InType.Public_enterprise, 22222222);
        Intervenant i3 = new Intervenant("Nadia", "Fortin", "nadia@example.com", "password123", Intervenant.InType.Individual, 33333333);
        Intervenant i4 = new Intervenant("Ismael", "Caron", "ismael@example.com", "password123", Intervenant.InType.Unhandled, 44444444);
        Intervenant i5 = new Intervenant("Laura", "Picard", "laura@example.com", "password123", Intervenant.InType.Private_entrepreneur, 88888855);

        addIntervenant(i1);
        addIntervenant(i2);
        addIntervenant(i3);
        addIntervenant(i4);
        addIntervenant(i5);

        // 5 requêtes de travail
        // Requête 1 (soumise par alice@example.com)
        Request req1 = new Request(
            Project.Reason.Travaux_routiers,
            getDistrict(Database.District_name.LaSalle),
            new metrics.Date(2023,1,10), // date fictive
            "STID1","Rue A","Rue B",100.0,"Réparation trottoir","Trottoir cassé", r1
        );
        addRequest(req1);

        // Requête 2 (julie@example.com)
        Request req2 = new Request(
            Project.Reason.Travaux_résidentiel,
            getDistrict(Database.District_name.LaSalle),
            new metrics.Date(2023,2,15),
            "STID2","Rue C","Rue D",200.0,"Aménagement paysager","Jardin", r2
        );
        addRequest(req2);

        // Requête 3 (marc@example.com)
        Request req3 = new Request(
            Project.Reason.Construction_ou_rénovation,
            getDistrict(Database.District_name.Verdun),
            new metrics.Date(2023,3,20),
            "STID3","Rue E","Rue F",300.0,"Rénovation façade","Façade brisée", r3
        );
        addRequest(req3);

        // Requête 4 (sophie@example.com)
        Request req4 = new Request(
            Project.Reason.Entretien_urbain,
            getDistrict(Database.District_name.Montreal_Nord),
            new metrics.Date(2023,4,25),
            "STID4","Rue G","Rue H",400.0,"Nettoyage graffitis","Murs tagués", r4
        );
        addRequest(req4);

        // Requête 5 (chantal@example.com)
        Request req5 = new Request(
            Project.Reason.Travaux_souterrains,
            getDistrict(Database.District_name.Anjou),
            new metrics.Date(2025,5,30),
            "STID5","Rue I","Rue J",500.0,"Égout bouché","Canalisation", r5
        );
        addRequest(req5);

        // Ajouter des candidatures sur 2 requêtes (par ex. req1 et req2)
        req1.addSupportingIntervenant(i1);
        req1.addSupportingIntervenant(i2);

        req2.addSupportingIntervenant(i3);

        // 5 projets dont au moins un prévu dans les 3 prochains mois
        // Projet 1 (prévu)
        Project proj1 = new Project(
            getProjectList().size(),
            "bigId_test1",
            "permit_id_test1",
            "permit_cat_test1",
            "111111",
            "Travaux_routiers",
            "Projet route future",
            getDistrict(Database.District_name.LaSalle),
            "2024-12-10T00:00:00Z",
            "2024-12-15T00:00:00Z",
            "occupancy_test",
            "organization_test",
            "submitter_test",
            new Coordinates(0,0)
        );
        addProject(proj1);

        // Projet 2
        Project proj2 = new Project(
            getProjectList().size(),
            "bigId_test2",
            "permit_id_test2",
            "permit_cat_test2",
            "222222",
            "Travaux_résidentiel",
            "Projet residentiel",
            getDistrict(Database.District_name.Verdun),
            "2025-06-01T00:00:00Z",
            "2025-07-01T00:00:00Z",
            "occupancy_test",
            "organization_test",
            "submitter_test",
            new Coordinates(0,0)
        );
        addProject(proj2);

        // Projet 3
        Project proj3 = new Project(
            getProjectList().size(),
            "bigId_test3",
            "permit_id_test3",
            "permit_cat_test3",
            "333333",
            "Construction_ou_rénovation",
            "Projet construction",
            getDistrict(Database.District_name.Montreal_Nord),
            "2025-02-01T00:00:00Z",
            "2025-03-01T00:00:00Z",
            "occupancy_test",
            "organization_test",
            "submitter_test",
            new Coordinates(0,0)
        );
        addProject(proj3);

        // Projet 4
        Project proj4 = new Project(
            getProjectList().size(),
            "bigId_test4",
            "permit_id_test4",
            "permit_cat_test4",
            "444444",
            "Entretien_paysager",
            "Projet paysager",
            getDistrict(Database.District_name.Anjou),
            "2024-12-07T00:00:00Z",
            "2024-12-20T00:00:00Z",
            "occupancy_test",
            "organization_test",
            "submitter_test",
            new Coordinates(0,0)
        );
        addProject(proj4);

        // Projet 5
        Project proj5 = new Project(
            getProjectList().size(),
            "bigId_test5",
            "permit_id_test5",
            "permit_cat_test5",
            "888888",
            "Entretien_urbain",
            "Projet urbain",
            getDistrict(Database.District_name.Dorval),
            "2025-01-15T00:00:00Z",
            "2025-02-15T00:00:00Z",
            "occupancy_test",
            "organization_test",
            "submitter_test",
            new Coordinates(0,0)
        );
        addProject(proj5);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

            // Ajout d'utilisateurs connus pour les tests
            Resident testResident = new Resident(
                "Alice", "Benoit", "alice@example.com", "password123",
                "514-888-0000", "123 Rue Udem, Montréal", 98765
            );
            addResident(testResident);

            Intervenant testIntervenant = new Intervenant(
                "Carlos", "Durand", "carlos@example.com", "password123",
                InType.Private_entrepreneur, 12345678
            );
            addIntervenant(testIntervenant);
        }

        addAdmin(new Admin(null, null, null, null, "Herobrine"));
    }

    /**
     * Envoie une notification à tous les résidents d'un district.
     * @param project_district Le district du projet.
     * @param project Le projet associé à la notification.
     */
    public void send_notif(District_name project_district, Project project) {
        for (Resident r : getResidentList()){
            if ( r.geDistrict() == project_district){
                // Implémentation à compléter
            }
        }
    }

    /**
     * Sélectionne aléatoirement un type d'intervenant.
     * @return Un type d'intervenant sélectionné aléatoirement.
     */
    private InType intypeRoulette(){
        int n = new Random().nextInt(4);
        switch (n) {
            case 0: return InType.Individual;
            case 1: return InType.Private_entrepreneur;
            case 2: return InType.Public_enterprise;
            default: return InType.Unhandled;
        }
    }

    /**
     * Envoie une notification à tous les résidents d'un district.
     * @param districtName Le nom du district.
     * @param message Le message de la notification.
     */
    public void sendNotificationToDistrict(District_name districtName, String message) {
        District d = districtHashtable.get(districtName);
        if (d == null) return;
    
        Notification notif = new Notification("Mise à jour de projet", message, new District[]{d});
    
        for (Resident r : getResidentList()) {
            if (r.geDistrict() == districtName) {
                r.add_notification(notif);
            }
        }
    
        System.out.println("Notifications envoyées aux résidents du district " + districtName);
    }

    /**
     * Récupère le résident actif.
     * @return Le résident actif.
     */
    public Resident getActiveUser_Resident() {
        return (Resident) activeUser;
    }

    /**
     * Récupère l'intervenant actif.
     * @return L'intervenant actif.
     */
    public Intervenant getActiveUser_Intervenant() {
        return (Intervenant) activeUser;
    }

    /**
     * Récupère l'utilisateur actif.
     * @return L'utilisateur actif.
     */
    public User getActiveUser() {
        return activeUser;
    }

    /**
     * Définit l'utilisateur actif.
     * @param activeUser L'utilisateur à définir comme actif.
     */
    public void setActiveUser(User activeUser) {
        Database.activeUser = activeUser;
    }
}
