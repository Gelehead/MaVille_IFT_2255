package Instances;

import java.util.Random;

import backend.Database;
import metrics.Date;

public class Request {
    

    // schedule is structured as 
    /*              HH MM                       HH+1 MM
     * DAY [#approval on HH MM of DAY,  #approval on HH+1 MM of DAY, ...]
     * DAY2
     */
    private short[][] Schedule = new short[7][14]; 
    private Project.Reason reason;
    private Project.Progress progress;

    // subsequent project and impediment will have the same id
    private long id = new Random().nextLong();
    private District district;
    private Date start;
    private Date end;
    private String streetid, fromname, toname;
    private double length;
    private String description, title;
    private User user;

    public Request(
        Project.Reason reason, District district,
        Date start, /*Date end,*/ String streetid, String fromname, String toname,
        double length, String description, String title,
        User user
    ) {
        this.reason = reason;
        this.district = district;
        this.start = start;
        //this.end = end;
        this.streetid = streetid;
        this.fromname = fromname;
        this.toname = toname;
        this.description = description;
        this.length = length;
        this.title = title;
        this.user = user;
    }

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

    // TODO: finish once and for all this district thing
    public static District handle_district(String s){
        switch (s) {
            case "1": return new District(Database.District_name.placeholder);         
            default: return new District(Database.District_name.placeholder);
                
        }
    }

    // getters 
    public District getDistrict() {return district;}
    public Date getEnd() {return end;}
    public String getFromname() {return fromname;}
    public long getId() {return id;}
    public double getLength() {return length;}
    public Project.Progress getProgress() {return progress;}
    public Project.Reason getReason() {return reason;}
    public short[][] getSchedule() {return Schedule;}
    public Date getStart() {return start;}
    public String getStreetid() {return streetid;}
    public String getToname() {return toname;}
    public String getDescription() {return description;}
    public String getTitle() {return title;}
    public User getUser() {return user;}
}
