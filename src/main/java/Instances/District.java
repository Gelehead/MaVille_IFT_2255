package Instances;

import Utils.GeoJSON.Geometry;
import backend.Database.District_name;

/**
 * Classe représentant un district.
 *
 * Contient toutes les informations du district issues du GeoJSON, telles que le nom, la géométrie, le code MAMH,
 * le type, le code ID et le code 3C.
 */
public class District {
    /**
     * Le nom du district.
     */
    District_name name;
    
    /**
     * La géométrie du district.
     */
    Geometry geometry;
    
    /**
     * Le code MAMH du district.
     */
    String mamh;
    
    /**
     * Le type du district.
     */
    String type;
    
    /**
     * Le code 3C du district.
     */
    String code3c;
    
    /**
     * Le code ID du district.
     */
    int codeId;

    /**
     * Constructeur de la classe District.
     *
     * @param name      Le nom du district.
     * @param geometry  La géométrie du district.
     * @param mamh      Le code MAMH du district.
     * @param type      Le type du district.
     * @param codeId    Le code ID du district.
     * @param code3c    Le code 3C du district.
     */
    public District(
        District_name name, Geometry geometry, String mamh,
        String type, int codeId, String code3c
    ) {
        this.name = name;
        this.geometry = geometry;
        this.mamh = mamh;
        this.code3c = code3c;
        this.type = type;
        this.codeId = codeId;
    }

    /**
     * Vérifie si un point donné est contenu dans la géométrie du district.
     *
     * @param latitude    La latitude du point.
     * @param longitude   La longitude du point.
     * @return true si le point est contenu dans le district, false sinon.
     */
    public boolean containsPoint(double latitude, double longitude) {
        return this.geometry.contains(latitude, longitude);
    }

    /**
     * Gère le choix du district en fonction de l'entrée de l'utilisateur.
     *
     * @param choice_district Le choix du district sous forme de chaîne.
     * @return Le nom du district correspondant.
     * @throws IllegalArgumentException Si le choix du district est invalide.
     */
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

    /**
     * Retourne une représentation sous forme de chaîne du district.
     *
     * @return Une chaîne contenant les informations du district.
     */
    @Override
    public String toString() {
        return name + "\n"
            + "type : " + type + "\n"
            + "mamh : " + mamh + "\n"
            + "code3C : " + code3c + "\n"
            + "code ID : " + codeId + "\n";
    }

    // Getters 

    /**
     * Obtient la géométrie du district.
     *
     * @return La géométrie du district.
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Obtient le code MAMH du district.
     *
     * @return Le code MAMH du district.
     */
    public String getMamh() {
        return mamh;
    }

    /**
     * Obtient le nom du district.
     *
     * @return Le nom du district.
     */
    public District_name getName() {
        return name;
    }

    // Setters 

    /**
     * Définit la géométrie du district.
     *
     * @param geometry La nouvelle géométrie du district.
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * Définit le code MAMH du district.
     *
     * @param mamh Le nouveau code MAMH du district.
     */
    public void setMamh(String mamh) {
        this.mamh = mamh;
    }

    /**
     * Définit le nom du district.
     *
     * @param name Le nouveau nom du district.
     */
    public void setName(District_name name) {
        this.name = name;
    }
}
