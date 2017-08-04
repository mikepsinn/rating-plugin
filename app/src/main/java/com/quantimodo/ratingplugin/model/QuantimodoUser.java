package com.quantimodo.ratingplugin.model;

public class QuantimodoUser {
    public String displayName;
    public String loginName;
    public String email;
    public String token;
    public boolean isAdministrator;

    // Do not use this constructor
    public QuantimodoUser() {
    }

    public QuantimodoUser(String displayName, String loginName, String email, String token, boolean isAdministrator) {
        this.displayName = displayName;
        this.loginName = loginName;
        this.email = email;
        this.token = token;
        this.isAdministrator = isAdministrator;
    }
}
