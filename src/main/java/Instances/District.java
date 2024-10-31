package Instances;
import Utils.*;
// specific convex hull problem
// https://en.wikipedia.org/wiki/Gift_wrapping_algorithm
public class District {
    Vec2[] frontiers;
    Line2[] sides;

    public District(Vec2[] frontiers){
        this.frontiers = frontiers;
        double[][] sortedPoints = Algorithm.SortByIndex(0, Vec2.toCoordinates(frontiers));
    }

}
