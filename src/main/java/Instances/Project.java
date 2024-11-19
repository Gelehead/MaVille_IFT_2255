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

    private String reason;
    
    private String occupancy;
    private String organization;
    private String submitter_category;

    // not that useful parameters
    private String permit_id;
    private String permit_category;
    private int contract_number;

    public enum Progress {
        NOT_STARTED,
        IN_PROGRESS,
        FINISHED,

        PLACEHOLDER

        //probably needs like "NEGOTIATING", "REFUSED", "ABANDONED", and more
    }

    public Project
    (
        int id, 
        String permit_id, String permit_category, String contract_number,
        String status, String reason,
        District district, 
        String start, String end, 
        String occupancy, String organization, String submitter_category,
        Coordinates co)
    {
        this.id = id;

        this.permit_id = permit_id;
        this.permit_category = permit_category;
        this.contract_number = contract_number == null ? 0 : Integer.parseInt(contract_number);

        this.status = toProgress(status);
        this.reason = reason;

        this.district = district;

        this.start_date = Date.format(start);
        this.end_date = Date.format(end);

        this.occupancy = occupancy;
        this.organization = organization;
        this.submitter_category = submitter_category;

        this.coordinates = co;
    }

    private Progress toProgress(String s){
        return Progress.PLACEHOLDER;
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
        return res;
    }

    // getters 
    public int getContract_number() {return contract_number;}
    public Coordinates getCoordinates() {return coordinates;}
    public District getDistrict() {return district;}
    public Date getEnd_date() {return end_date;}
    public String getOccupancy() {return occupancy;}
    public String getOrganization() {return organization;}
    public String getPermit_category() {return permit_category;}
    public String getPermit_id() {return permit_id;}
    public String getReason() {return reason;}
    public Date getStart_date() {return start_date;}
    public Progress getStatus() {return status;}
    public String getSubmitter_category() {return submitter_category;}
}
