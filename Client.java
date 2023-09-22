import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) throws Exception {
        // PUT request
        String data = "{\n" +
                "    \"id\" : \"IDS60901\",\n" +
                "    \"name\" : \"Adelaide (West Terrace /  ngayirdapira)\",\n" +
                "    \"state\" : \"SA\",\n" +
                "    \"time_zone\" : \"CST\",\n" +
                "    \"lat\": -34.9,\n" +
                "    \"lon\": 138.6,\n" +
                "    \"local_date_time\": \"15/04:00pm\",\n" +
                "    \"local_date_time_full\": \"20230715160000\",\n" +
                "    \"air_temp\": 13.3,\n" +
                "    \"apparent_t\": 9.5,\n" +
                "    \"cloud\": \"Partly cloudy\",\n" +
                "    \"dewpt\": 5.7,\n" +
                "    \"press\": 1023.9,\n" +
                "    \"rel_hum\": 60,\n" +
                "    \"wind_dir\": \"S\",\n" +
                "    \"wind_spd_kmh\": 15,\n" +
                "    \"wind_spd_kt\": 8\n" +
                "  }";
        URL url = new URL("http://localhost:8080");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();
        System.out.println("PUT Response Code: " + responseCode);

        // GET request
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        String responseData = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("GET Response Data: " + responseData);
    }
}
