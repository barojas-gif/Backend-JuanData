package com.jdc.repojuandata.DTO;

public class RecuperarRequest {
    private String username;

    // Constructor vacío (necesario para JSON)
    public RecuperarRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
