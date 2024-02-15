package com.xindus.ecommerce.dto;

import lombok.Data;
//Create a Authentication API Here
@Data
public class AuthenticationRequest {
    private String username;
    private String  password;

    public Object getPassword() {
        return null;
    }

    public String getUsername() {
        return null;
    }
}
