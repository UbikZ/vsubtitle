package org.ubikz.vsubtitle.api.controller.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFilterBody extends AbstractFilterBody {
    private String username;
    private String email;
    private String password;

    @JsonProperty("username")
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserFilterBody{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}