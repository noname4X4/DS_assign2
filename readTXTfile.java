import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//try to read a file and transfer to json format
public class readTXTfile {
    public static void main(String[] args) throws IOException {
        WeatherData weatherData = readFile("src/data.txt");
        System.out.println(weatherData.getId());
    }
    public static WeatherData readFile(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        Map<String, Object> data = new HashMap<>();

        for (String line : lines) {
            String[] parts = line.split(":", 2); // Split at the first colon
            if (parts.length == 2) {
                data.put(parts[0].trim(), parts[1].trim());

            }
        }
        WeatherData weatherData = new WeatherData();
        for(String key : data.keySet()){//assign data in txt to a weatherdata entity
            weatherData.set(key, (String) data.get(key));
        }
        return weatherData;
    }
}

