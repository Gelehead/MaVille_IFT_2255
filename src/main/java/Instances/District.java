package Instances;
import Utils.GeoJSON.Geometry;
import backend.Database.District_name;
public class District {
    District_name name;
    Geometry geometry;
    String mamh, type, code3c;
    int codeId;

    // placeholder, should contain all informationo from the GeoJSON
    public District(
        District_name name, Geometry geometry, String mamh,
        String type, int codeId, String code3c
    )
    {
        this.name = name;
        this.geometry = geometry;
        this.mamh = mamh;
        this.code3c = code3c;
        this.type = type;
        this.codeId = codeId;
    }

    public boolean containsPoint(double latitude, double longitude) {
        return this.geometry.contains(latitude, longitude);
    }

    public static District_name handleDistrictChoice(String choice_district) {
        switch (choice_district) {
            case "1":
                return District_name.LaSalle;
            case "2":
                return District_name.Dollard_des_Ormeaux;
            case "3":
                return District_name.Côte_Saint_Luc;
            case "4":
                return District_name.Villeray_Saint_Michel_Parc_Extension;
            case "5":
                return District_name.Rosemont_La_Petite_Patrie;
            case "6":
                return District_name.Hampstead;
            case "7":
                return District_name.Senneville;
            case "8":
                return District_name.Le_Plateau_Mont_Royal;
            case "9":
                return District_name.Sainte_Anne_de_Bellevue;
            case "10":
                return District_name.Montreal_Ouest;
            case "11":
                return District_name.Cote_des_Neiges_Notre_Dame_de_Grace;
            case "12":
                return District_name.Ile_Bizard_Sainte_Genevieve;
            case "13":
                return District_name.Beaconsfield;
            case "14":
                return District_name.Anjou;
            case "15":
                return District_name.Verdun;
            case "16":
                return District_name.Le_Sud_Ouest;
            case "17":
                return District_name.Mercier_Hochelaga_Maisonneuve;
            case "18":
                return District_name.Montreal_Est;
            case "19":
                return District_name.Lachine;
            case "20":
                return District_name.Saint_Leonard;
            case "21":
                return District_name.Montreal_Nord;
            case "22":
                return District_name.Outremont;
            case "23":
                return District_name.Ile_Dorval;
            case "24":
                return District_name.Mont_Royal;
            case "25":
                return District_name.Pointe_Claire;
            case "26":
                return District_name.Dorval;
            case "27":
                return District_name.Pierrefonds_Roxboro;
            case "28":
                return District_name.Riviere_des_Prairies_Pointe_aux_Trembl;
            case "29":
                return District_name.Ahuntsic_Cartierville;
            case "30":
                return District_name.Saint_Laurent;
            case "31":
                return District_name.Ville_Marie;
            case "32":
                return District_name.Kirkland;
            case "33":
                return District_name.Baie_D_Urfe;
            case "34":
                return District_name.Westmount;
            default:
                throw new IllegalArgumentException("Invalid district choice: " + choice_district);
        }
    }

    @Override
    public String toString() {
        return name + "\n"
        + "type : " + type + "\n"
        + "mamh : " + mamh + "\n"
        + "code3C : " + code3c + "\n"
        + "code ID : " + codeId + "\n";
    }

    //getters 
    public Geometry getGeometry() {
        return geometry;
    }
    public String getMamh() {
        return mamh;
    }
    public District_name getName() {
        return name;
    }


    // setters 
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    public void setMamh(String mamh) {
        this.mamh = mamh;
    }
    public void setName(District_name name) {
        this.name = name;
    }
    

}
