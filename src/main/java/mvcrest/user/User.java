package mvcrest.user;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private String JWTToken;
    private static AtomicInteger idCounter = new AtomicInteger();

    public String getJWTToken() {
        return JWTToken;
    }

    public void setJWTToken(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId() {
        this.id = idCounter.getAndIncrement();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
