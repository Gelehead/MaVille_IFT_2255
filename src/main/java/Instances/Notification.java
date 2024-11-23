package Instances;

public class Notification {
    public String resume, description;
    private District[] concerned_districts;

    public Notification(String resume, String description, District[] affects){
        this.resume = resume;
        this.description = description;
        this.concerned_districts = affects;
    }

    public District[] getConcerned_districts() {return concerned_districts;}
}
