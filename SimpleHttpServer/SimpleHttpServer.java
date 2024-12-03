import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleHttpServer {

    private static final Logger logger = Logger.getLogger(SimpleHttpServer.class.getName());
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", new RootHandler());
            server.setExecutor(null);
            server.start();
            logger.info("Server started on port " + PORT);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start server", e);
        }
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            String response = "Hello, World!";
            exchange.getResponseHeaders().add("Content-Type", "text/plain");
            sendResponse(exchange, 200, response);
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String response) {
            try {
                exchange.sendResponseHeaders(statusCode, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error handling request", e);
                sendErrorResponse(exchange);
            }
        }

        private void sendErrorResponse(HttpExchange exchange) {
            String errorResponse = "Internal Server Error";
            sendResponse(exchange, 500, errorResponse);
        }
    }
}
