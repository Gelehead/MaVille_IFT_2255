package Instances;

import java.util.ArrayList;

import Utils.Schedule;

/**
 * Classe représentant un utilisateur dans le système.
 * Un utilisateur peut être de différents types (USER, RESIDENT, INTERVENANT, ADMIN, etc.)
 * et peut s'abonner à des districts, recevoir des notifications et avoir un emploi du temps.
 */
public class User {

    /**
     * Enumération des différents types d'utilisateurs.
     */
    public static enum Type {
        USER,           // Utilisateur standard
        RESIDENT,       // Résident
        INTERVENANT,    // Intervenant
        ADMIN,          // Administrateur

        PLACEHOLER,     // Placeholder

        // trust, this is relevant 
        // used in the database.printall
        PROJECT,        // Projet (utilisé dans la base de données)
        IMPEDIMENT      // Impédiment (utilisé dans la base de données)
    }

    // first name, last name, email, password
    private String fname, lname, mail, pw;
    
    /**
     * Type d'utilisateur, ici défini comme {@code USER} par défaut.
     */
    public Type type = Type.USER;
    
    /**
     * Liste des districts auxquels l'utilisateur est abonné.
     */
    private ArrayList<District> subscriptions;
    
    /**
     * Liste des notifications reçues par l'utilisateur.
     */
    private ArrayList<Notification> notifications;
    
    /**
     * Emploi du temps de l'utilisateur.
     */
    private Schedule schedule = new Schedule();

    /**
     * Constructeur par défaut de la classe {@code User}.
     */
    public User() {}

    /**
     * Constructeur avec paramètres pour créer un nouvel {@code User}.
     * 
     * @param fname Prénom de l'utilisateur.
     * @param lname Nom de famille de l'utilisateur.
     * @param mail Adresse e-mail de l'utilisateur.
     * @param pw Mot de passe de l'utilisateur.
     */
    public User(String fname, String lname, String mail, String pw){
        this.fname = fname;
        this.lname = lname;
        this.mail = mail;
        this.pw = pw;
    }

    /**
     * Ajoute un district aux abonnements de l'utilisateur.
     * 
     * @param d District à ajouter.
     */
    public void addToSubscriptions(District d){
        this.subscriptions.add(d);
    }

    /**
     * Ajoute une notification à l'utilisateur.
     * 
     * @param notif Notification à ajouter.
     */
    public void add_notification(Notification notif){
        notifications.add(notif);
    }

    /**
     * Met à jour l'emploi du temps de l'utilisateur en exécutant l'interface en ligne de commande.
     */
    public void update_schedule(){
        schedule.runCLI();
    }

    // Getters

    /**
     * Retourne le nom de famille de l'utilisateur.
     * 
     * @return Nom de famille de l'utilisateur.
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * Retourne l'adresse e-mail de l'utilisateur.
     * 
     * @return Adresse e-mail de l'utilisateur.
     */
    public String getMail() {
        return this.mail;
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     * 
     * @return Mot de passe de l'utilisateur.
     */
    public String getPw() {
        return this.pw;
    }

    /**
     * Retourne le prénom de l'utilisateur.
     * 
     * @return Prénom de l'utilisateur.
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Retourne les notifications de l'utilisateur.
     * 
     * @return Liste des notifications de l'utilisateur.
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Retourne les abonnements de l'utilisateur.
     * 
     * @return Liste des abonnements de l'utilisateur.
     */
    public ArrayList<District> getSubscriptions() {
        return subscriptions;
    }

    /**
     * Retourne l'emploi du temps de l'utilisateur.
     * 
     * @return Emploi du temps de l'utilisateur.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    // Setters

    /**
     * Définit le nom de famille de l'utilisateur.
     * 
     * @param lname Nouveau nom de famille de l'utilisateur.
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Définit l'adresse e-mail de l'utilisateur.
     * 
     * @param mail Nouvelle adresse e-mail de l'utilisateur.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     * 
     * @param pw Nouveau mot de passe de l'utilisateur.
     */
    public void setPw(String pw) {
        this.pw = pw;
    }

    /**
     * Définit le prénom de l'utilisateur.
     * 
     * @param fName Nouveau prénom de l'utilisateur.
     */
    public void setFname(String fName) {
        this.fname = fName;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'utilisateur.
     * 
     * @return Chaîne de caractères représentant l'utilisateur.
     */
    @Override
    public String toString() {
        return fname + " " + lname + " : " + mail + " , " + "*".repeat(pw.length());
    }

}
