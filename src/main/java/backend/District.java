package backend;
import Utils.*;
// specific convex hull problem
// https://en.wikipedia.org/wiki/Gift_wrapping_algorithm
public class District {
    Vec2[] frontiers;
    Line2[] sides;

    public District(Vec2[] frontiers){
        this.frontiers = frontiers;
        int[][] sortedPoints = Algorythm.SortByIndex(0, Vec2.toCoordinates(frontiers));
    }

}
