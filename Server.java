import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Server {

    private static String storedData = "{}";

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(200, storedData.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(storedData.getBytes());
                }
            } else if ("PUT".equals(exchange.getRequestMethod())) {
                storedData = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, -1);
            }
        }
    }
}
