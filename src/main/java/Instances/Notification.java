package Instances;

/*
 * Classe représentant une notification
 */
public class Notification {
    /*
     * Attributs de la classe Notification
     */
    public String resume, description;
    private District[] concerned_districts;

    /*
     * Constructeur de la classe Notification
     * @param resume : résumé de la notification
     * @param description : description de la notification
     * @param affects : districts concernés par la notification
     * @return : une instance de Notification
     * 
     */
    public Notification(String resume, String description, District[] affects){
        this.resume = resume;
        this.description = description;
        this.concerned_districts = affects;
    }

    /*
     * Méthode permettant de récupérer le résumé de la notification
     * @return : le résumé de la notification
     */
    public District[] getConcerned_districts() {return concerned_districts;}
}
