package Instances;
import java.util.ArrayList;

public class Project {

    public enum progress {
        NOT_STARTED,
        IN_PROGRESS,
        FINISHED

        //probably needs like "NEGOTIATING", "REFUSED", "ABANDONED", and more
    }

    String name, description;
    int start_date, end_date;
    // logs is the list of previous versions of the project
    ArrayList<Project> logs;
    User organiser;
    District district;

    public Project(String name, int start_date, int end_date, User organiser, District district, String description){
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        // initializes logs as an empty list
        this.logs = new ArrayList<Project>();
        this.organiser = organiser;
        this.district = district;
        this.description = description;
    }

    @Override
    public String toString(){
        // find a good way to represent the project as a string
        // must be usable wihtin the logs context
        return "placeholder";
    }

    public String getLogs(){
        return "placeholder";
    }
}
