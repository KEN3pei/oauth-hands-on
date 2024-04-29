package com.example.authorization_server.domains;

public class ClientCredencial {
    public String clientId;
    public String clientSecret;

    public ClientCredencial(
            String clientId,
            String clientSecret
            ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
