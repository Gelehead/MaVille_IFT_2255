package Instances;
import Utils.*;
import backend.Database;
import backend.Database.District_name;
import metrics.Address;
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

    // TODO: placeholder resolve the district bs
    public static District getDistrict(String address){
        return new District(Database.District_name.placeholder);
    }

    public static District handleDistrictChoice(Address address){
        return new District(Database.District_name.placeholder);
    }

    // handles the district choice in Langugae.districtMenu
    public static District handleDistrictChoice(String choice_district) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleDistrictChoice'");
    }

}
