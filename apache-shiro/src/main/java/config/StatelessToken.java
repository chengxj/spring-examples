package config;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

public class StatelessToken implements AuthenticationToken {
    private String username;
    private String key;
    private Map<String, ?> params;

    public StatelessToken(String username, String key, Map<String, ?> params) {
        this.username = username;
        this.key = key;
        this.params = params;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, ?> getParams() {
        return params;
    }

    public void setParams(Map<String, ?> params) {
        this.params = params;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return key;
    }
}
