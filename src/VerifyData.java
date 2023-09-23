import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class VerifyData {

    private static final String[] EXPECTED_ATTRIBUTES = {//expected attributes in weatherdata
            "id", "name", "state", "time_zone", "lat", "lon",
            "local_date_time", "local_date_time_full", "air_temp",
            "apparent_t", "cloud", "dewpt", "press", "rel_hum",
            "wind_dir", "wind_spd_kmh", "wind_spd_kt"
    };

    public static void main(String[] args) {
//        String filePath = "src/invalidData.txt";
        String filePath = "src/ExtraData.txt";
        if (isValidFile(filePath)) {
            System.out.println("The file is valid.");
        } else {
            System.out.println("The file is invalid.");
        }
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
}
