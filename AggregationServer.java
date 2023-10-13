import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
public class AggregationServer {
public static JSONObject weatherData = new JSONObject();
public static String lastTimeBody;
public static void parseToWeatherdata(String body){
    body = body.replaceAll("[\\{\\}\"]", "");    String[] data = body.split(",");
    for(String line : data){
        String[] subline = line.split(":");
        weatherData.put(subline[0],subline[1]);
    }
    System.out.println(weatherData);
}
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4567)) {
            while (true) {
                System.out.println("Aggregation server is listening");
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    // Read the request line
                    String requestLine = reader.readLine();
                    System.out.println("Received: " + requestLine);
                    if(requestLine.contains("PUT")) {
                        // Read headers and find Content-Length
                        String headerLine;
                        int contentLength = 0;
                        while (!(headerLine = reader.readLine()).isEmpty()) {
                            if (headerLine.startsWith("Content-Length:")) {
                                contentLength = Integer.parseInt(headerLine.split(": ")[1]);
                            }
                        }

                        // Read body
                        char[] bodyChars = new char[contentLength];
                        reader.read(bodyChars, 0, contentLength);
                        String body = new String(bodyChars);
                        System.out.println("Body: " + body);
                        //first time
                        if(lastTimeBody == null){
                            lastTimeBody = body;
                            parseToWeatherdata(body);
                            //send response
                            writer.write("201 - HTTP_CREATED\r\n");
                        }
                        //update
                        if(lastTimeBody != body){
                            parseToWeatherdata(body);
                            //send response
                            writer.write("200 - weatherdata update\r\n");
                        }


                        // Send HTTP response
                        writer.write("HTTP/1.1 200 OK\r\n");
                        writer.write("Content-Type: text/plain\r\n");
                        writer.write("\r\n");
                        writer.write("Data Received");
                        writer.flush();
                    }else if(requestLine.contains("GET")){
                        if(weatherData.isEmpty() == true){
                            writer.write("205 - the aggregation server has not received weather data yet");
                        }else {
                            writer.write(weatherData.toString());
                        }
                    }else {// Any request other than GET or PUT
                        writer.write("400 - invalid request\r\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
