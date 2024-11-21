package Instances;
import metrics.*;

public class Project {

    //actual parameters
    public int id;
    private Date start_date;
    private Date end_date;
    private District district;

    private Progress status;
    private Coordinates coordinates;

    private Reason reason;
    
    private String occupancy;
    private String organization;
    private String submitter_category;

    // not that useful parameters
    private String permit_id;
    private String permit_category;
    private long contract_number;

    //probably needs like "NEGOTIATING", "REFUSED", "ABANDONED", and more
    public enum Progress {
        NOT_STARTED,
        IN_PROGRESS,
        FINISHED,

        PLACEHOLDER
    }


    public enum Reason {
        Construction_rénovation_sans_excavation,
        Construction_rénovation_avec_excavation,
        Égouts_et_aqueducs__Réhabilitation,
        Égouts_et_aqueducs__Excavation,
        Égouts_et_aqueducs__Inspection_et_nettoyage,
        Autre,
        Réseaux_routier__Réfection_et_travaux_corrélatifs,
        S_3_Infrastructure_souterraine_majeure__Massifs_et_conduits,

        UNHANDLED_REASON
    }

    public Project
    (
        int id, 
        String permit_id, String permit_category, String contract_number,
        String reason,
        District district, 
        String start, String end, 
        String occupancy, String organization, String submitter_category,
        Coordinates co)
    {
        this.id = id;

        this.permit_id = permit_id;
        this.permit_category = permit_category;
        this.contract_number = contract_number == null ? 0 : Long.parseLong(contract_number);

        this.reason = toReason(reason);

        // TODO: adjust parsing for districts
        this.district = district;

        this.start_date = Date.format(start);
        this.end_date = Date.format(end);
        this.status = getProgress();

        this.occupancy = occupancy;
        this.organization = organization;
        this.submitter_category = submitter_category;

        this.coordinates = co;
    }

    private Progress getProgress(){
        return Date.projectProgress(this.start_date, this.end_date);
    }

    private Reason toReason(String s){
        switch (s) {
            case "S-3 Infrastructure souterraine majeure - Massifs et conduits":
                return Reason.S_3_Infrastructure_souterraine_majeure__Massifs_et_conduits;
            case "Autre":
                return Reason.Autre;
            case "Construction/rénovation sans excavation":
                return Reason.Construction_rénovation_sans_excavation;
            case "Construction/rénovation avec excavation" :
                return Reason.Construction_rénovation_avec_excavation;
            case "Égouts et aqueducs - Réhabilitation" : 
                return Reason.Égouts_et_aqueducs__Réhabilitation;
            case "Égouts et aqueducs - Inspection et nettoyage" : 
                return Reason.Égouts_et_aqueducs__Inspection_et_nettoyage;
            case "Égouts et aqueducs - Excavation" :
                return Reason.Égouts_et_aqueducs__Excavation;
            case "Réseaux routier - Réfection et travaux corrélatifs":
                return Reason.Réseaux_routier__Réfection_et_travaux_corrélatifs;
            default:
                return Reason.UNHANDLED_REASON;
        }
    }

    @Override
    public String toString() {
        String res = "";
        res += ("  ID: " + id + "\n");
        res += ("  Borough: " + (district == null ? "district is null" : district.name) + "\n");
        res += ("  Start Date: " + start_date+ "\n");
        res += ("  End Date: " + end_date + "\n");
        res += ("  Longitude: " + coordinates.longitude+ "\n");
        res += ("  Latitude: " + coordinates.latitude + "\n");
        res += ("  reason: " + this.reason + "\n");
        res += ("  Submitter category: " + this.submitter_category + "\n");
        res += ("  contract number: " + this.contract_number + "\n");
        res += ("  occupancy: " + this.occupancy + "\n");
        res += ("  organization: " + this.organization + "\n");
        res += ("  progress: " + this.status + "\n");

        return res;
    }

    // getters 
    public long getContract_number() {return contract_number;}
    public Coordinates getCoordinates() {return coordinates;}
    public District getDistrict() {return district;}
    public Date getEnd_date() {return end_date;}
    public String getOccupancy() {return occupancy;}
    public String getOrganization() {return organization;}
    public String getPermit_category() {return permit_category;}
    public String getPermit_id() {return permit_id;}
    public Reason getReason() {return reason;}
    public Date getStart_date() {return start_date;}
    public Progress getStatus() {return status;}
    public String getSubmitter_category() {return submitter_category;}
}
