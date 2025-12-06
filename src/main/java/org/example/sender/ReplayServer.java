package org.example.sender;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

public class ReplayServer {
    private HttpServer server;
    private int port;
    private ReplaySender sender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ReplayServer(int port, ReplaySender sender) {
        this.port = port;
        this.sender = sender;
    }

    // Start the server
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // Add a single endpoint in order to request missed packets.
        // We expect calls in the format .../replay?start=<starting-seqno>&end=<ending-seqno>
        server.createContext("/replay", new ReplayHandler());

        server.setExecutor(null); // default executor
        server.start();
        logger.info(" Started server at port {}", port);

    }

    // Stop the server
    public void stop() {
        if (server != null) {
            //Finish whatever is currently being processed, then stop.
            server.stop(1);
            logger.info("Server stopped.");
        }
    }

    // Handler for /replay
    class ReplayHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            URI requestUri = exchange.getRequestURI();
            Map<String, String> params = Utils.queryToMap(requestUri);

            Long start = Utils.getLong(params, "start");
            Long end = Utils.getLong(params, "end");

            if (start != null || end != null){
                if(start == end){
                    // we just missed one
                    try {
                        // Not really sure how i should be sending this information.
                        String response = new String(sender.replay(start).getData(), StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                // We missed more than one
                try {
                    String response = new String(Arrays.stream(sender.replay(start, end)).toString(), StandardCharsets.UTF_8);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }



            logger.info("Received replay request {}-{}", start, end);
            exchange.sendResponseHeaders(200, response.length());

            try (var os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
