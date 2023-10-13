
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
public class ContentServer {
    private static boolean flag = true;
    private static final int DEFAULT_PORT = 4567;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the hostname:port (e.g., localhost:4567): ");
        String hostPort = scanner.nextLine();

        System.out.print("Enter the file path (e.g., src/data.txt): ");
        String filePath = scanner.nextLine();
        while (flag){
            if(isValidFile(filePath)){
                System.out.println("valid filepath");
                break;
            }else {
                System.out.println("please check the file path or the file's attribute");
                flag = false;
            }
        }

        String[] addressParts = hostPort.split(":");
        String hostname = addressParts[0];
        int port = Integer.parseInt(addressParts[1]);
        try (Socket socket = new Socket(hostname, port);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Construct the HTTP PUT request
            String jsonData = readFile(filePath).toString();
            writer.write("PUT /weather.json HTTP/1.1\r\n"+
            "User-Agent: ContentServer/1.0\r\n"+
            "Content-Type: application/json\r\n"+
            "Content-Length: " + jsonData.length() + "\r\n"+
            "\r\n");
            writer.write(jsonData);
            writer.flush();

            // Read and print the response
            String responseLine = reader.readLine();
            System.out.println("Response: " + responseLine);

        } catch (IOException e) {
            System.out.println("invalid port number or hostname");
        }
    }

    //the content server need to read data from a file and store it
    public static JSONObject readFile(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        Map<String, Object> data = new HashMap<>();

        for (String line : lines) {
            String[] parts = line.split(":", 2); // Split at the first colon
            if (parts.length == 2) {
                data.put(parts[0].trim(), parts[1].trim());

            }
        }
        JSONObject weatherData = new JSONObject();
        for(String key : data.keySet()){//assign data in txt to a weatherdata entity
            weatherData.put(key, (String) data.get(key));
        }
        return weatherData;
    }
    public static boolean isValidFile(String filePath) {
        Set<String> missingAttributes = new HashSet<>();
        Set<String> extraAttributes = new HashSet<>();
        Set<String> fileAttributes = new HashSet<>();

        for (String attribute : EXPECTED_ATTRIBUTES) {
            missingAttributes.add(attribute);
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length > 0) {
                    fileAttributes.add(parts[0].trim());
                    missingAttributes.remove(parts[0].trim());
                }
            }

            // Determine extra attributes
            for (String attribute : fileAttributes) {
                if (!Arrays.asList(EXPECTED_ATTRIBUTES).contains(attribute)) {
                    extraAttributes.add(attribute);
                }
            }

            if (missingAttributes.isEmpty() && extraAttributes.isEmpty()) {
                return true;
            } else {
                if (!missingAttributes.isEmpty()) {
                    System.out.println("Missing attributes: " + missingAttributes);
                }
                if (!extraAttributes.isEmpty()) {
                    System.out.println("Extra attributes: " + extraAttributes);
                }
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static final String[] EXPECTED_ATTRIBUTES = {//expected attributes in weatherdata
            "id", "name", "state", "time_zone", "lat", "lon",
            "local_date_time", "local_date_time_full", "air_temp",
            "apparent_t", "cloud", "dewpt", "press", "rel_hum",
            "wind_dir", "wind_spd_kmh", "wind_spd_kt"
    };
    public static void sendWeatherDataToAggregationServer(String hostPort, String jsonData) throws Exception {
        URL url = new URL("http://" + hostPort + "/weatherdata");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("POST");
        httpCon.setRequestProperty("Content-Type", "application/json");

        try (OutputStream out = httpCon.getOutputStream()) {
            out.write(jsonData.getBytes());
        }

        int responseCode = httpCon.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            String responseLine;
            StringBuilder response = new StringBuilder();

            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine);
            }
            in.close();
            System.out.println(response.toString());  // Print server's response.
        } else {
            System.out.println("POST request failed. Response Code: " + responseCode);
        }
    }
}
