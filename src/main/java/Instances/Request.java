package Instances;

import java.util.Random;

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

    public Request(
        Project.Reason reason, Project.Progress progress, District district,
        Date start, Date end, String streetid, String fromname, String toname,
        double length
    ) {
        this.reason = reason;
        this.progress = progress;
        this.district = district;
        this.start = start;
        this.end = end;
        this.streetid = streetid;
        this.fromname = fromname;
        this.toname = toname;
        this.length = length;
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

}
