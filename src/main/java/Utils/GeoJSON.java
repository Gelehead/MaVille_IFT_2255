package Utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoJSON {
    private String type;
    private CRS crs;
    private List<Feature> features;

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public CRS getCrs() { return crs; }
    public void setCrs(CRS crs) { this.crs = crs; }

    public List<Feature> getFeatures() { return features; }
    public void setFeatures(List<Feature> features) { this.features = features; }

    // Static Nested Classes
    public static class CRS {
        private String type;
        private Properties properties;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public Properties getProperties() { return properties; }
        public void setProperties(Properties properties) { this.properties = properties; }
    }

    public static class Properties {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class Feature {
        private String type;
        private FeatureProperties properties;
        private Geometry geometry;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public FeatureProperties getProperties() { return properties; }
        public void setProperties(FeatureProperties properties) { this.properties = properties; }

        public Geometry getGeometry() { return geometry; }
        public void setGeometry(Geometry geometry) { this.geometry = geometry; }
    }

    public static class FeatureProperties {
        @JsonProperty("CODEID")
        private int codeId;

        @JsonProperty("NOM")
        private String nom;

        @JsonProperty("NOM_OFFICIEL")
        private String nomOfficiel;

        @JsonProperty("CODEMAMH")
        private String codeMamh;

        @JsonProperty("CODE_3C")
        private String code3C;

        @JsonProperty("NUM")
        private int num;

        @JsonProperty("ABREV")
        private String abrev;

        @JsonProperty("TYPE")
        private String type;

        @JsonProperty("COMMENT")
        private String comment;

        @JsonProperty("DATEMODIF")
        private String dateModif;

        // getters 
        public String getAbrev() {return abrev;}
        public String getCode3C() {return code3C;}
        public int getCodeId() {return codeId;}
        public String getCodeMamh() {return codeMamh;}
        public String getComment() {return comment;}
        public String getDateModif() {return dateModif;}
        public String getNom() {return nom;}
        public String getNomOfficiel() {return nomOfficiel;}
        public int getNum() {return num;}
        public String getType() {return type;}

        // setters 
        public void setAbrev(String abrev) {this.abrev = abrev;}
        public void setCode3C(String code3c) {code3C = code3c;}
        public void setCodeId(int codeId) {this.codeId = codeId;}
        public void setCodeMamh(String codeMamh) {this.codeMamh = codeMamh;}
        public void setComment(String comment) {this.comment = comment;}
        public void setDateModif(String dateModif) {this.dateModif = dateModif;}
        public void setNom(String nom) {this.nom = nom;}
        public void setNomOfficiel(String nomOfficiel) {this.nomOfficiel = nomOfficiel;}
        public void setNum(int num) {this.num = num;}
        public void setType(String type) {this.type = type;}
    }

    public static class Geometry {
        private String type;
        private List<List<List<List<Double>>>> coordinates;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public List<List<List<List<Double>>>> getCoordinates() { return coordinates; }
        public void setCoordinates(List<List<List<List<Double>>>> coordinates) {
            this.coordinates = coordinates;
        }

        public boolean contains(double latitude, double longitude) {
            // Outer boundary is the first ring of the first feature
            List<List<Double>> outerBoundary = coordinates.get(0).get(0);
            if (!isPointInPolygon(latitude, longitude, outerBoundary)) {
                return false; // Point is outside the outer boundary
            }

            // Check inner boundaries (holes)
            for (int i = 1; i < coordinates.get(0).size(); i++) {
                List<List<Double>> innerBoundary = coordinates.get(0).get(i);
                if (isPointInPolygon(latitude, longitude, innerBoundary)) {
                    return false; // Point is inside a hole
                }
            }

            return true;
        }

        /**
         * Determines if a point is within a polygon using the ray-casting algorithm.
         *
         * @param latitude  The latitude of the point.
         * @param longitude The longitude of the point.
         * @param polygon   The polygon represented as a list of [longitude, latitude] points.
         * @return true if the point is within the polygon, false otherwise.
         */
        private boolean isPointInPolygon(double latitude, double longitude, List<List<Double>> polygon) {
            boolean inside = false;
            int n = polygon.size();

            for (int i = 0, j = n - 1; i < n; j = i++) {
                double xi = polygon.get(i).get(1); // Latitude of vertex i
                double yi = polygon.get(i).get(0); // Longitude of vertex i
                double xj = polygon.get(j).get(1); // Latitude of vertex j
                double yj = polygon.get(j).get(0); // Longitude of vertex j

                // Check if point is within the boundary of the edge
                boolean intersect = ((yi > longitude) != (yj > longitude)) &&
                        (latitude < (xj - xi) * (longitude - yi) / (yj - yi) + xi);
                if (intersect) {
                    inside = !inside;
                }
            }

            return inside;
        }
    }
}

    

