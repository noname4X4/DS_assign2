import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

public class GETClient {
    private static boolean flag = false;
    private static JSONObject weatherData;
    public static JSONObject parseToJson(String body){
        JSONObject data = new JSONObject(body);
        return data;
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Please enter the port number:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int port = Integer.parseInt(input);
        while(true) {
            try {

                if(flag == false) {
                    try (Socket socket = new Socket("localhost", port);
                         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        flag = true;
                        // Construct the HTTP GET request
                        writer.write("GET / HTTP/1.1\r\n");
                        writer.write("Host: localhost\r\n");
                        writer.write("\r\n");  // HTTP headers end with a blank line
                        writer.flush();

                        // Read and print the response
                        String responseLine;
                        while ((responseLine = reader.readLine()) != null) {
                            System.out.println("Response: " + responseLine);
                            weatherData = parseToJson(responseLine);
                            for (String key : weatherData.keySet()) {
                                System.out.println(key + ": " + weatherData.getString(key));
                            }
                        }

                    } catch (IOException e) {
                        System.out.println("Error: Invalid port number " + port);
                    }
                }else if(flag){
                    System.out.println("Client is hanging on, if you want to get the weather data aging, please input get");
                    scanner = new Scanner(System.in);
                    String getData = scanner.nextLine();
                    System.out.println(getData);
                    if(getData.equals("get")){
                        flag = false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Could not connect to the server");

            }
        }
    }
}
