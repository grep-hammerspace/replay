package org.example.sender;

import com.sun.net.httpserver.HttpExchange;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    // Parse query parameters into a Map
    public static Map<String, String> queryToMap(URI uri) {
        Map<String, String> result = new HashMap<>();
        String query = uri.getQuery(); // returns the part after '?', or null
        if (query == null) return result;

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                result.put(keyValue[0], keyValue[1]);
            }
        }
        return result;
    }

    // Parse a Long safely
    public static Long getLong(Map<String, String> params, String key) {
        String value = params.get(key);
        if (value == null) return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
