package Instances;
import Utils.*;
import backend.Database.District_name;
// specific convex hull problem
// https://en.wikipedia.org/wiki/Gift_wrapping_algorithm
public class District {
    Vec2[] frontiers;
    Line2[] sides;
    District_name name;

/*     public District(Vec2[] frontiers){
        this.frontiers = frontiers;
        double[][] sortedPoints = Algorithm.SortByIndex(0, Vec2.toCoordinates(frontiers));
    } */

    // placeholder, should contain all informationo from the GeoJSON
    public District(District_name name){
        this.name = name;
    }

}
