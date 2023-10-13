import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

import org.json.JSONObject;

public class ClientWithTimeOut {
    private static boolean flag = false;
    private static JSONObject weatherData;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static JSONObject parseToJson(String body) {
        JSONObject data = new JSONObject(body);
        return data;
    }

    public static String getTimedInput(Scanner scanner, ScheduledFuture<?> scheduledFuture) {
        while (!scanner.hasNextLine()) {
            try {
                Thread.sleep(200);  // Polling interval
            } catch (InterruptedException e) {
                System.out.println("User input interrupted: " + e.getMessage());
            }
        }

        // If input is received, cancel the scheduled shutdown
        scheduledFuture.cancel(false);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Runnable shutdownTask = () -> {
            System.out.println("Shutdown due to inactivity.");
            System.exit(0);
        };

        try {
            System.out.println("Please enter the port number:");
            ScheduledFuture<?> scheduledFuture = scheduler.schedule(shutdownTask, 30, TimeUnit.SECONDS);
            String input = getTimedInput(scanner, scheduledFuture);
            int port = Integer.parseInt(input);

            while (true) {
                if (!flag) {
                    try (Socket socket = new Socket("localhost", port);
                         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                        flag = true;

                        writer.write("GET / HTTP/1.1\r\n");
                        writer.write("Host: localhost\r\n");
                        writer.write("\r\n");
                        writer.flush();

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
                        System.out.println("Please enter the port number:");
                        scheduledFuture = scheduler.schedule(shutdownTask, 30, TimeUnit.SECONDS);
                        input = getTimedInput(scanner, scheduledFuture);
                        port = Integer.parseInt(input);
                    }
                } else {
                    System.out.println("Client is hanging on, if you want to get the weather data aging, please input get");
                    scheduledFuture = scheduler.schedule(shutdownTask, 30, TimeUnit.SECONDS);
                    String getData = getTimedInput(scanner, scheduledFuture);
                    System.out.println(getData);
                    if ("get".equals(getData)) {
                        flag = false;
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number. Please enter a numeric value.");
        } finally {
            scheduler.shutdown();
        }
    }
}
