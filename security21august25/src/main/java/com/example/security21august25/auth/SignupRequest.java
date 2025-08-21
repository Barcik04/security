package com.example.security21august25.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
    @NotBlank @Size(min = 3, max = 50)
    private String userName;

    @NotBlank @Size(min = 8, max = 100)
    private String password;

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
