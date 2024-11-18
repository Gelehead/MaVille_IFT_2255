package Utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Parser {

    class Root {
        public String help;
        public boolean success;
        public Result result;
    }
    
    // Class to map the "result" object
    class Result {
        @JsonProperty("include_total")
        public boolean includeTotal;
        public int limit;
        @JsonProperty("records_format")
        public String recordsFormat;
        @JsonProperty("resource_id")
        public String resourceId;
        public List<Record> records;
    }

    class Record {
        @JsonProperty("_id")
        public int id;
        public String permit_permit_id;
        public String boroughid;
        public String permitcategory;
        public String currentstatus;
        @JsonProperty("duration_start_date")
        public String durationStartDate;
        @JsonProperty("duration_end_date")
        public String durationEndDate;
        public String occupancy_name;
        public String organizationname;
        public String longitude;
        public String latitude;

        @Override
        public String toString() {
            String res = "";
            res += ("  ID: " + id + "\n");
            res += ("  Permit ID: " + permit_permit_id+ "\n");
            res += ("  Borough: " + boroughid+ "\n");
            res += ("  Start Date: " + durationStartDate+ "\n");
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
    public static Parser.Record[] getRecords(String jsonURL){
        try {
            ObjectMapper om = new ObjectMapper();

            String JsonResponse = fetchJsonFromUrl(jsonURL);

            Root root = om.readValue(JsonResponse, Root.class);

            return (Record[]) root.result.records.toArray();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
