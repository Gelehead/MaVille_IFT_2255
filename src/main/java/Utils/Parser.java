package Utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

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
        public String duration_days_mon_active;
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
        public String duration_days_sun_end_time;
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

    // Method to fetch JSON data from a URL
    public static String fetchJsonFromUrl(String jsonUrl) throws IOException {
        // Open connection to the URL
        URL url = new URL(jsonUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
        return jsonResponse.toString();
    }

    /** Gets all records and put them into the database
     * 
     * @param filePath
     */
    public static List<Record> getRecords(String jsonURL){
        try {
            ObjectMapper om = new ObjectMapper();

            String JsonResponse = fetchJsonFromUrl(jsonURL);

            Root root = om.readValue(JsonResponse, Root.class);

            return root.result.records;
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
