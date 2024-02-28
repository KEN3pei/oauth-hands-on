package com.example.authorization_server.services.util;

import java.util.UUID;

public class GenerateUUID {
    
    public static String generate()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
