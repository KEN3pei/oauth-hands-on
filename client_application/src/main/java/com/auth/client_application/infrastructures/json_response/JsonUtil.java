package com.auth.client_application.infrastructures.json_response;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static String toJson(ResponseEntity<?> res) {
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(res.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
