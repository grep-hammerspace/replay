package org.example.sender;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SingleClientHandler implements HttpHandler {

    private InetSocketAddress allowedClient = null;

    @Override
    public synchronized void handle(HttpExchange exchange) throws IOException {
        InetSocketAddress clientAddress = exchange.getRemoteAddress();

        // If no client yet, register this one
        if (allowedClient == null) {
            allowedClient = clientAddress;
        }

        // Only serve requests from the registered client
        if (!allowedClient.equals(clientAddress)) {
            exchange.sendResponseHeaders(403, -1); // Forbidden
            exchange.close();
            return;
        }

        // Normal response
        String response = "Hello, single client!";
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

}
