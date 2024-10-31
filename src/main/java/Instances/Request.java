package Instances;

public class Request {
    String description;
    Project project;
    public Request(String description, Project project){
        this.description = description;
        this.project = project;
    }

    // getters
    public String getDescription() {return description;}
    public Project getProject() {return project;}
    
    // setters 
    public void setDescription(String description) {this.description = description;}
    public void setProject(Project project) {this.project = project;}
}
