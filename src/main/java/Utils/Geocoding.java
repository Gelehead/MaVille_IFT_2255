package Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Geocoding {

    private static final String API_KEY = "18996ebf570f4298b988c76b67bbed78";

    /**
     * Fetches the latitude and longitude for a given address using the OpenCage API.
     *
     * @param address The address to geocode.
     * @return A double array with latitude and longitude [lat, lng].
     * @throws Exception If the API call or JSON parsing fails.
     */
    public static double[] getCoordinates(String address) throws Exception {
        // Format the API URL
        String url = String.format("https://api.opencagedata.com/geocode/v1/json?q=%s&key=%s",
                address.replace(" ", "%20"), API_KEY);

        // Build the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response.body());

        // Extract coordinates
        JsonNode results = jsonResponse.path("results");
        if (results.isArray() && results.size() > 0) {
            JsonNode geometry = results.get(0).path("geometry");
            double lat = geometry.path("lat").asDouble();
            double lon = geometry.path("lng").asDouble();
            return new double[]{lat, lon};
        } else {
            throw new IllegalArgumentException("Address not found in geocoding response.");
        }
    }
}
