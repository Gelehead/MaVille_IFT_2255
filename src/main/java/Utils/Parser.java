package Utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import UI_UX.Dialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
public class Parser {

    public static class Root {
        public String help;
        public boolean success;
        public Result result;
    }
    @JsonIgnoreProperties({"fields"})
    public static class Result {
        @JsonProperty("include_total")
        public boolean includeTotal;
        public int limit;
        @JsonProperty("records_format")
        public String recordsFormat;
        @JsonProperty("resource_id")
        public String resourceId;
        public List<Record> records;
        @JsonProperty("total_estimation_threshold")
        public String totalEstimationThreshold;
        public List<Object> fields;  // This will be ignored during deserialization
        public Object _links;
        public int total;
        public boolean total_was_estimated;
    }

    public static class Record {
        @JsonProperty("_id")
        public int id;
        @JsonProperty("id")
        public String official_id;
        public String permit_permit_id;
        public String contractnumber;
        public String boroughid;
        public String permitcategory;
        public String currentstatus;
        public String duration_start_date;
        public String duration_end_date;
        public String reason_category;
        public String occupancy_name;
        public String submittercategory;
        public String organizationname;
// deprecated parameters
/*         public String duration_days_mon_active;
        public String duration_days_mon_all_day_round;
        public String duration_days_tue_active;
        public String duration_days_tue_all_day_round;
        public String duration_days_wed_active;
        public String duration_days_wed_all_day_round;
        public String duration_days_thu_active;
        public String duration_days_thu_all_day_round;
        public String duration_days_fri_active;
        public String duration_days_fri_all_day_round;
        public String duration_days_sat_active;
        public String duration_days_sat_all_day_round;
        public String duration_days_sun_active;
        public String duration_days_sun_all_day_round;
        public String duration_days_sat_start_time;
        public String duration_days_sat_end_time;
        public String duration_days_mon_start_time;
        public String duration_days_mon_end_time;
        public String duration_days_tue_start_time;
        public String duration_days_tue_end_time;
        public String duration_days_wed_start_time;
        public String duration_days_wed_end_time;
        public String duration_days_thu_start_time;
        public String duration_days_thu_end_time;
        public String duration_days_fri_start_time;
        public String duration_days_fri_end_time;
        public String duration_days_sun_start_time;
        public String duration_days_sun_end_time; */
        public String load_date;
        public String longitude;
        public String latitude;

        @Override
        public String toString() {
            String res = "";
            res += ("  ID: " + id + "\n");
            res += ("  Permit ID: " + permit_permit_id+ "\n");
            res += ("  Borough: " + boroughid+ "\n");
            res += ("  Start Date: " + duration_start_date+ "\n");
            res += ("  Longitude: " + longitude+ "\n");
            res += ("  Latitude: " + latitude + "\n");
            return res;
        }
    }

    @JsonIgnoreProperties({"fields"})
    public static class RootImpediment {
        public String help;
        public boolean success;
        public Resultimpediment result;
    }

    @JsonIgnoreProperties({"fields"})
    public static class Resultimpediment {
        @JsonProperty("include_total")
        public boolean includeTotal;
        public int limit;
        @JsonProperty("records_format")
        public String recordsFormat;
        @JsonProperty("resource_id")
        public String resourceId;
        public List<Impediment> records;
        @JsonProperty("total_estimation_threshold")
        public String totalEstimationThreshold;
        public List<Object> fields;  // This will be ignored during deserialization
        public Object _links;
        public int total;
        public boolean total_was_estimated;
    }
    
    public static class Impediment {
        @JsonProperty("_id")
        public int id;
        @JsonProperty("id_request")
        public String idRequest;
        public String streetid;
        public String streetimpactwidth;
        public String streetimpacttype;
        public String nbfreeparkingplace;
        public String sidewalk_blockedtype;
        public String backsidewalk_blockedtype;
        public String bikepath_blockedtype;
        public String name;
        public String shortname;
        public String fromname;
        public String fromshortname;
        public String toname;
        public String toshortname;
        public String length;
        public String isarterial;
        public String stmimpact_blockedtype;
        public String otherproviderimpact_blockedtype;
        public String reservedlane_blockedtype;
    
        @Override
        public String toString() {
            return String.format("ID: %d, Street: %s, Length: %s, Is Arterial: %s", id, name, length, isarterial);
        }

        public boolean affects(String s){
            if (streetid.toLowerCase().contains(s.toLowerCase())){return true;}
            if (fromname.toLowerCase().contains(s.toLowerCase())){return true;}
            if (toname.toLowerCase().contains(s.toLowerCase())){return true;}
            return false;
        }
    }

    // Method to fetch JSON data from a URL
    //TODO: implement an offline method, so the program can work without url
    public static String fetchJsonFromUrl(String jsonUrl) throws IOException {
        // Open connection to the URL
        URL url = new URL(jsonUrl);
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        }

        // current method is slightly faster than this one

/*         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        // Check if the response code is 200 (OK)
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Failed to fetch data, HTTP response code: " + responseCode);
        }

        // Read response body
        StringBuilder jsonResponse = new StringBuilder();
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            while (scanner.hasNextLine()) {
                jsonResponse.append(scanner.nextLine());
            }
        }
        connection.disconnect();
        return jsonResponse.toString(); */
    }

        /** Gets all impediments (entraves) and put them into the database
     * 
     * @param filePath
     */
    public static List<Impediment> getImpediments(String jsonURL){
        System.out.println(Language.fetching_impediments(Dialog.choice_language));
        try {
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            String JsonResponse = fetchJsonFromUrl(jsonURL);

            RootImpediment root = om.readValue(JsonResponse, RootImpediment.class);
            return root.result.records;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Gets all records and put them into the database
     * 
     * @param filePath
     */
    public static List<Record> getRecords(String jsonURL){
        System.out.println(Language.fetching_records(Dialog.choice_language));
        try {
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            String JsonResponse = fetchJsonFromUrl(jsonURL);

            Root root = om.readValue(JsonResponse, Root.class);

            return root.result.records;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    ///  Pour tester le parsing sans le url à partir des fichiers test local////

    //  lire fichiers locaux et les parser en projets
    public static List<Record> getRecordsFromLocalFile(String filePath) {
        try {
            String jsonContent = readFileAsString(filePath);
            // Utiliser ObjectMapper pour convertir JSON en objets
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Root root = om.readValue(jsonContent, Root.class);
            return root.result.records;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lire des fichiers locaux et les parser en entraves
    public static List<Impediment> getImpedimentsFromLocalFile(String filePath) {
        try {
            String jsonContent = readFileAsString(filePath);
            // Utiliser ObjectMapper pour convertir JSON en objets
            ObjectMapper om = new ObjectMapper();
            om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            RootImpediment root = om.readValue(jsonContent, RootImpediment.class);
            return root.result.records;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode lit un fichier comme String
    private static String readFileAsString(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static GeoJSON getgeojson(String filename) {
    try {
        // Utiliser le ClassLoader pour obtenir la ressource
        InputStream inputStream = Parser.class.getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new FileNotFoundException("Fichier non trouvé: " + filename);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, GeoJSON.class);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}
