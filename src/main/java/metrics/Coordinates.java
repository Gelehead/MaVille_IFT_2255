package metrics;

/**
 * Classe représentant les coordonnées géographiques d'un point.
 */
public class Coordinates{
    public double longitude, latitude;
    /**
     * Constructeur par défaut de la classe Coordinates.
     */
    public Coordinates(double longitude, double latitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}