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
    private static final int DEFAULT_PORT = 8000;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid port number, using default port: " + DEFAULT_PORT);
            }
        }

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new RootHandler());
            server.createContext("/health", new HealthHandler());
            server.createContext("/info", new InfoHandler());
            server.setExecutor(null);
            server.start();
            logger.info("Server started on port " + port);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start server", e);
        }
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "Hello, World!";
                exchange.getResponseHeaders().add("Content-Type", "text/plain");
                sendResponse(exchange, 200, response);
            } else {
                sendErrorResponse(exchange, 405, "Method Not Allowed");
            }
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String response) {
            try {
                exchange.sendResponseHeaders(statusCode, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error handling request", e);
                sendErrorResponse(exchange, 500, "Internal Server Error");
            }
        }

        private void sendErrorResponse(HttpExchange exchange, int statusCode, String errorMessage) {
            try {
                String response = errorMessage;
                exchange.sendResponseHeaders(statusCode, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error sending error response", e);
            }
        }
    }

    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "Healthy";
                exchange.getResponseHeaders().add("Content-Type", "text/plain");
                new RootHandler().sendResponse(exchange, 200, response);
            } else {
                new RootHandler().sendErrorResponse(exchange, 405, "Method Not Allowed");
            }
        }
    }

    static class InfoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "Simple HTTP Server v1.0";
                exchange.getResponseHeaders().add("Content-Type", "text/plain");
                new RootHandler().sendResponse(exchange, 200, response);
            } else {
                new RootHandler().sendErrorResponse(exchange, 405, "Method Not Allowed");
            }
        }
    }
}