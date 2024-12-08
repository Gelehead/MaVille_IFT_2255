package Instances;

import java.util.ArrayList;

import Utils.Schedule;
import metrics.*;

/**
 * Classe représentant un projet.
 *
 * Contient toutes les informations relatives à un projet, telles que l'identifiant, le titre, la raison,
 * l'arrondissement associé, les dates de début et de fin, les coordonnées géographiques, le statut,
 * l'occupation, l'organisation, la catégorie du soumissionnaire, les permis associés et les contrats.
 */
public class Project {

    /**
     * Identifiant unique du projet.
     */
    public int id;

    /**
     * Grand identifiant du projet.
     */
    public String bigId;

    /**
     * Date de début du projet.
     */
    private Date start_date;

    /**
     * Date de fin du projet.
     */
    private Date end_date;

    /**
     * Arrondissement associé au projet.
     */
    private District district;

    /**
     * État d'avancement du projet.
     */
    private Progress status;

    /**
     * Coordonnées géographiques du projet.
     */
    private Coordinates coordinates;

    /**
     * Raison du projet.
     */
    private Reason reason;

    /**
     * Titre du projet.
     */
    private String title;

    /**
     * Occupation associée au projet.
     */
    private String occupancy;

    /**
     * Organisation soumettant le projet.
     */
    private String organization;

    /**
     * Catégorie du soumissionnaire du projet.
     */
    private String submitter_category;

    /**
     * Identifiant du permis associé au projet.
     */
    private String permit_id;

    /**
     * Catégorie du permis associé au projet.
     */
    private String permit_category;

    /**
     * Numéro du contrat associé au projet.
     */
    private long contract_number;

    /**
     * Historique des modifications du projet.
     */
    private ArrayList<Project> logs = new ArrayList<>();

    /**
     * Énumération représentant l'état d'avancement du projet.
     */
    public enum Progress {
        /**
         * Projet planifié.
         */
        PLANNED,
        /**
         * Projet non démarré.
         */
        NOT_STARTED,
        /**
         * Projet en cours.
         */
        IN_PROGRESS,
        /**
         * Projet terminé.
         */
        FINISHED,

        /**
         * Demande soumise.
         */
        SUBMITTED,
        /**
         * Demande refusée.
         */
        REFUSED,
        /**
         * Demande acceptée.
         */
        ACCEPTED,

        /**
         * État d'avancement placeholder.
         */
        PLACEHOLDER
    }

    /**
     * Énumération représentant les raisons possibles d'un projet.
     */
    public enum Reason {
        /**
         * Travaux routiers.
         */
        Travaux_routiers,
        /**
         * Travaux de gaz ou électricité.
         */
        Travaux_de_gaz_ou_électricité,
        /**
         * Construction ou rénovation.
         */
        Construction_ou_rénovation,
        /**
         * Entretien paysager.
         */
        Entretien_paysager,
        /**
         * Travaux liés aux transports en commun.
         */
        Travaux_liés_aux_transports_en_commun,
        /**
         * Travaux de signalisation et éclairage.
         */
        Travaux_de_signalisation_et_éclairage,
        /**
         * Travaux souterrains.
         */
        Travaux_souterrains,
        /**
         * Travaux résidentiels.
         */
        Travaux_résidentiel,
        /**
         * Entretien urbain.
         */
        Entretien_urbain,
        /**
         * Entretien des réseaux de télécommunication.
         */
        Entretien_des_réseaux_de_télécommunication,

        /**
         * Autre raison.
         */
        Autre,

        /**
         * Raison non traitée.
         */
        UNHANDLED_REASON
    }

    /**
     * Constructeur du projet.
     *
     * @param title       Titre du projet.
     * @param description Description du projet.
     * @param id          Identifiant du projet.
     * @param bigId       Grand identifiant du projet.
     * @param reason      Raison du projet.
     * @param district    Arrondissement associé au projet.
     * @param streets     Rues concernées par le projet.
     * @param start       Date de début du projet.
     * @param end         Date de fin du projet.
     * @param schedule    Horaire du projet.
     * @param submitter   Soumissionnaire du projet.
     */
    public Project(
        String title, String description,
        int id, String bigId,
        Reason reason,
        District district, 
        String[] streets,
        Date start, Date end, 
        Schedule schedule,
        User submitter
    ) {
        this.id = id;
        this.bigId = bigId;
        this.reason = reason;
        this.title = title;

        // TODO: adjust parsing for districts
        this.district = district;

        this.start_date = start;
        this.end_date = end;
        this.status = Progress.PLANNED;

        // logs will be used to keep track of project changes
        logs.add(this);
    }

    /**
     * Constructeur du projet à partir de chaînes de caractères.
     *
     * @param id                Identifiant du projet.
     * @param bigId             Grand identifiant du projet.
     * @param permit_id         Identifiant du permis.
     * @param permit_category   Catégorie du permis.
     * @param contract_number   Numéro du contrat.
     * @param reason            Raison du projet.
     * @param title             Titre du projet.
     * @param district          Arrondissement associé au projet.
     * @param start             Date de début du projet au format chaîne.
     * @param end               Date de fin du projet au format chaîne.
     * @param occupancy         Occupation associée au projet.
     * @param organization      Organisation soumettant le projet.
     * @param submitter_category Catégorie du soumissionnaire.
     * @param co                Coordonnées géographiques du projet.
     */
    public Project(
        int id, String bigId,
        String permit_id, String permit_category, String contract_number,
        String reason, String title,
        District district, 
        String start, String end, 
        String occupancy, String organization, String submitter_category,
        Coordinates co
    ) {
        this.id = id;
        this.bigId = bigId;

        this.permit_id = permit_id;
        this.permit_category = permit_category;
        this.contract_number = contract_number == null ? 0 : Long.parseLong(contract_number);

        this.reason = toReason(reason);
        this.title = title;

        // TODO: adjust parsing for districts
        this.district = district;

        this.start_date = Date.format(start);
        this.end_date = Date.format(end);
        this.status = getProgress();

        this.occupancy = occupancy;
        this.organization = organization;
        this.submitter_category = submitter_category;

        this.coordinates = co;

        // logs will be used to keep track of project changes
        logs.add(this);
    }

    /**
     * Obtient l'état d'avancement du projet en fonction des dates.
     *
     * @return L'état d'avancement du projet.
     */
    private Progress getProgress() {
        return Date.projectProgress(this.start_date, this.end_date);
    }

    /**
     * Convertit une chaîne de caractères en raison du projet.
     *
     * @param s Chaîne de caractères représentant la raison.
     * @return La raison correspondante.
     */
    private Reason toReason(String s) {
        switch (s) {
            case "S-3 Infrastructure souterraine majeure - Massifs et conduits":
                return Reason.Travaux_souterrains;
            case "Autre":
                return Reason.Autre;
            case "Construction/rénovation sans excavation":
                return Reason.Construction_ou_rénovation;
            case "Construction/rénovation avec excavation":
                return Reason.Construction_ou_rénovation;
            case "Égouts et aqueducs - Réhabilitation":
                return Reason.Travaux_souterrains;
            case "Égouts et aqueducs - Inspection et nettoyage":
                return Reason.Travaux_souterrains;
            case "Égouts et aqueducs - Excavation":
                return Reason.Travaux_souterrains;
            case "Réseaux routier - Réfection et travaux corrélatifs":
                return Reason.Travaux_routiers;
            default:
                return Reason.UNHANDLED_REASON;
        }
    }

    /**
     * Analyse la raison sélectionnée dans le menu.
     *
     * @param r Représentation de la raison choisie.
     * @return La raison correspondante.
     */
    public static Reason parse_reasonMenu(String r) {
        switch (r) {
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
            default: return Project.Reason.UNHANDLED_REASON;
        }
    }

    /**
     * Analyse l'état d'avancement sélectionné dans le menu.
     *
     * @param p Représentation de l'état choisi.
     * @return L'état d'avancement correspondant.
     * @throws IllegalArgumentException Si le choix est invalide.
     */
    public static Progress parse_progressMenu(String p) {
        switch (p) {
            case "1": return Progress.NOT_STARTED;
            case "2": return Progress.IN_PROGRESS;
            case "3":
                return Progress.FINISHED;
            case "4":
                return Progress.PLACEHOLDER;
            default:
                throw new IllegalArgumentException("Invalid choice: " + p);
        }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du projet.
     *
     * @return Une chaîne de caractères représentant le projet.
     */
    @Override
    public String toString() {
        String res = "";
        res += ("  ID: " + id + "\n");
        res += ("  Borough: " + (district == null ? "district is null" : district.name) + "\n");
        res += ("  Start Date: " + start_date + "\n");
        res += ("  End Date: " + end_date + "\n");
        res += ("  Longitude: " + coordinates.longitude + "\n");
        res += ("  Latitude: " + coordinates.latitude + "\n");
        res += ("  Reason: " + this.reason + "\n");
        res += ("  Submitter Category: " + this.submitter_category + "\n");
        res += ("  Contract Number: " + this.contract_number + "\n");
        res += ("  Occupancy: " + this.occupancy + "\n");
        res += ("  Organization: " + this.organization + "\n");
        res += ("  Progress: " + this.status + "\n");

        return res;
    }

    // Getters

    /**
     * Obtient le numéro de contrat.
     *
     * @return Le numéro de contrat.
     */
    public long getContract_number() {
        return contract_number;
    }

    /**
     * Obtient les coordonnées du projet.
     *
     * @return Les coordonnées du projet.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Obtient l'arrondissement associé au projet.
     *
     * @return L'arrondissement du projet.
     */
    public District getDistrict() {
        return district;
    }

    /**
     * Obtient la date de fin du projet.
     *
     * @return La date de fin du projet.
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * Obtient l'occupation associée au projet.
     *
     * @return L'occupation du projet.
     */
    public String getOccupancy() {
        return occupancy;
    }

    /**
     * Obtient l'organisation soumettant le projet.
     *
     * @return L'organisation du projet.
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Obtient la catégorie du permis associé au projet.
     *
     * @return La catégorie du permis.
     */
    public String getPermit_category() {
        return permit_category;
    }

    /**
     * Obtient l'identifiant du permis associé au projet.
     *
     * @return L'identifiant du permis.
     */
    public String getPermit_id() {
        return permit_id;
    }

    /**
     * Obtient la raison du projet.
     *
     * @return La raison du projet.
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * Obtient la date de début du projet.
     *
     * @return La date de début du projet.
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     * Obtient l'état d'avancement du projet.
     *
     * @return L'état d'avancement du projet.
     */
    public Progress getStatus() {
        return status;
    }

    /**
     * Obtient la catégorie du soumissionnaire du projet.
     *
     * @return La catégorie du soumissionnaire.
     */
    public String getSubmitter_category() {
        return submitter_category;
    }

    /**
     * Obtient le titre du projet.
     *
     * @return Le titre du projet.
     */
    public String getTitle() {
        return title;
    }

    // Setters

    /**
     * Définit l'état d'avancement du projet.
     *
     * @param newStatus Le nouvel état du projet.
     */
    public void setStatus(Progress newStatus) {
        this.status = newStatus;
    }
}
