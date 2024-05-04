package com.auth.client_application.infrastructures.client;

import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository implements ClientRepositoryInterface {
    public String findByClientId(String clientId) throws Exception
    {
        if (!clientId.equals("1")) {
            throw new Exception("Not Found Client");
        }

        String clientState = "2aljs8";

        return clientState;
    }
}
