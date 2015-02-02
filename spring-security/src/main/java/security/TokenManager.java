package security;

import java.util.HashMap;
import java.util.Map;

public class TokenManager {
    private static final Map<String, String> tokenMap;

    static {
        tokenMap = new HashMap<String, String>();
    }

    public static void addToken(String token, String owner) {
        tokenMap.put(token, owner);
    }

    public static String getTokenOwner(String token) {
        return tokenMap.get(token);
    }

    public static void removeToken(String token) {
        tokenMap.put(token, null);
    }
}
