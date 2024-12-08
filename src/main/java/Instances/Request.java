package Instances;

import java.util.ArrayList;
import java.util.Random;

import metrics.Date;

/**
 * Représente une requête soumise par un utilisateur.
 * Contient des informations sur la raison, le district, les dates, les intervenants, etc.
 */
public class Request {

    /**
     * Planning de la requête, structuré en jours et créneaux horaires.
     * La première dimension représente les jours de la semaine (0 = Dimanche, 6 = Samedi),
     * et la seconde dimension représente les créneaux horaires de la journée.
     */
    private short[][] Schedule = new short[7][14];

    /**
     * Raison du projet associé à la requête.
     */
    private Project.Reason reason;

    /**
     * Avancement actuel du projet.
     */
    private Project.Progress progress;

    /**
     * Identifiant unique de la requête, généré aléatoirement.
     */
    public long id = new Random().nextLong();

    /**
     * District où la requête est effectuée.
     */
    private District district;

    /**
     * Date de début de la requête.
     */
    private Date start;

    /**
     * Date de fin de la requête.
     */
    private Date end;

    /**
     * Identifiant de la rue concernée.
     */
    private String streetid;

    /**
     * Nom de départ pour la requête.
     */
    private String fromname;

    /**
     * Nom d'arrivée pour la requête.
     */
    private String toname;

    /**
     * Longueur concernée par la requête.
     */
    private double length;

    /**
     * Description détaillée de la requête.
     */
    private String description;

    /**
     * Titre de la requête.
     */
    private String title;

    /**
     * Utilisateur qui a soumis la requête.
     */
    private User requestingUser;

    /**
     * Liste des intervenants soutenant la requête.
     */
    private ArrayList<Intervenant> supportingIntervenants = new ArrayList<>();

    // Nouveaux champs

    /**
     * Intervenant choisi par le résident.
     */
    private Intervenant chosenIntervenant;     // Intervenant choisi par le résident

    /**
     * Message optionnel du résident à l'intervenant choisi.
     */
    private String choiceMessage;              // Message optionnel du résident à l'intervenant choisi

    /**
     * Indique si l'intervenant choisi a confirmé.
     */
    private boolean confirmed = false;         // Vrai si l'intervenant choisi a confirmé

    /**
     * Indique si le résident a fermé la requête.
     */
    private boolean closed = false;            // Vrai si le résident a fermé la requête

    /**
     * Constructeur pour créer une nouvelle requête.
     *
     * @param reason      La raison du projet.
     * @param district    Le district concerné.
     * @param start       La date de début du projet.
     * @param streetid    L'identifiant de la rue.
     * @param fromname    Le nom de départ.
     * @param toname      Le nom de destination.
     * @param length      La longueur du projet.
     * @param description La description du projet.
     * @param title       Le titre du projet.
     * @param user        L'utilisateur qui soumet la requête.
     */
    public Request(
        Project.Reason reason, District district,
        Date start, /*Date end,*/ String streetid, String fromname, String toname,
        double length, String description, String title,
        User user
    ) {
        this.reason = reason;
        this.district = district;
        this.start = start;
        this.streetid = streetid;
        this.fromname = fromname;
        this.toname = toname;
        this.description = description;
        this.length = length;
        this.title = title;
        this.requestingUser = user;

        this.progress = Project.Progress.SUBMITTED; 
    }

    /**
     * Gère la conversion d'une chaîne de caractères en une raison de projet.
     *
     * @param s La chaîne de caractères représentant la raison.
     * @return La raison du projet correspondante.
     */
    public static Project.Reason handle_reason(String s){
        switch (s) {
            case "1": return Project.Reason.Travaux_routiers;
            case "2": return Project.Reason.Travaux_de_gaz_ou_électricité;
            case "3": return Project.Reason.Construction_ou_rénovation;
            case "4": return Project.Reason.Entretien_paysager;
            case "5": return Project.Reason.Travaux_liés_aux_transports_en_commun;
            case "6": return Project.Reason.Travaux_de_signalisation_et_éclairage;
            case "7": return Project.Reason.Travaux_souterrains;
            case "8": return Project.Reason.Travaux_résidentiel;
            case "9": return Project.Reason.Entretien_urbain;
            case "10": return Project.Reason.Entretien_des_réseaux_de_télécommunication;
            case "11": return Project.Reason.UNHANDLED_REASON;
            case "12": return Project.Reason.Autre;
            default:
                return Project.Reason.UNHANDLED_REASON;
        }
    }

    /**
     * Ajoute un intervenant de soutien à la liste des intervenants.
     *
     * @param i L'intervenant à ajouter.
     */
    public void addSupportingIntervenant(Intervenant i){
        this.supportingIntervenants.add(i);
    }

    // Setters pour les nouveaux champs

    /**
     * Définit l'intervenant choisi par le résident.
     *
     * @param chosenIntervenant L'intervenant choisi.
     */
    public void setChosenIntervenant(Intervenant chosenIntervenant) {
        this.chosenIntervenant = chosenIntervenant;
    }

    /**
     * Définit le message optionnel du résident à l'intervenant choisi.
     *
     * @param choiceMessage Le message optionnel.
     */
    public void setChoiceMessage(String choiceMessage) {
        this.choiceMessage = choiceMessage;
    }

    /**
     * Définit si l'intervenant choisi a confirmé.
     *
     * @param confirmed Vrai si confirmé, faux sinon.
     */
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    /**
     * Définit si le résident a fermé la requête.
     *
     * @param closed Vrai si fermé, faux sinon.
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    // Getters 

    /**
     * Retourne le district concerné par la requête.
     *
     * @return Le district.
     */
    public District getDistrict() {
        return district;
    }

    /**
     * Retourne la date de fin du projet.
     *
     * @return La date de fin.
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Retourne le nom de départ.
     *
     * @return Le nom de départ.
     */
    public String getFromname() {
        return fromname;
    }

    /**
     * Retourne l'identifiant de la requête.
     *
     * @return L'identifiant.
     */
    public long getId() {
        return id;
    }

    /**
     * Retourne la longueur du projet.
     *
     * @return La longueur.
     */
    public double getLength() {
        return length;
    }

    /**
     * Retourne l'état d'avancement du projet.
     *
     * @return L'état d'avancement.
     */
    public Project.Progress getProgress() {
        return progress;
    }

    /**
     * Retourne la raison du projet.
     *
     * @return La raison.
     */
    public Project.Reason getReason() {
        return reason;
    }

    /**
     * Retourne le calendrier du projet.
     *
     * @return Le calendrier.
     */
    public short[][] getSchedule() {
        return Schedule;
    }

    /**
     * Retourne la date de début du projet.
     *
     * @return La date de début.
     */
    public Date getStart() {
        return start;
    }

    /**
     * Retourne l'identifiant de la rue.
     *
     * @return L'identifiant de la rue.
     */
    public String getStreetid() {
        return streetid;
    }

    /**
     * Retourne le nom de destination.
     *
     * @return Le nom de destination.
     */
    public String getToname() {
        return toname;
    }

    /**
     * Retourne la description du projet.
     *
     * @return La description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retourne le titre du projet.
     *
     * @return Le titre.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retourne l'utilisateur qui a soumis la requête.
     *
     * @return L'utilisateur.
     */
    public User getUser() {
        return requestingUser;
    }

    /**
     * Retourne l'utilisateur qui a soumis la requête.
     *
     * @return L'utilisateur.
     */
    public User getRequestingUser() {
        return requestingUser;
    }

    /**
     * Retourne la liste des intervenants de soutien.
     *
     * @return La liste des intervenants de soutien.
     */
    public ArrayList<Intervenant> getSupportingIntervenants() {
        return supportingIntervenants;
    }

    // Getters pour les nouveaux champs

    /**
     * Retourne l'intervenant choisi par le résident.
     *
     * @return L'intervenant choisi.
     */
    public Intervenant getChosenIntervenant() {
        return chosenIntervenant;
    }

    /**
     * Retourne le message optionnel du résident à l'intervenant choisi.
     *
     * @return Le message optionnel.
     */
    public String getChoiceMessage() {
        return choiceMessage;
    }

    /**
     * Retourne vrai si l'intervenant choisi a confirmé.
     *
     * @return Vrai si confirmé, faux sinon.
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Retourne vrai si le résident a fermé la requête.
     *
     * @return Vrai si fermé, faux sinon.
     */
    public boolean isClosed() {
        return closed;
    }
}
