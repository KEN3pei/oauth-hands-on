package com.example.authorization_server.services.specification;

import com.example.authorization_server.domains.ClientCredencial;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface BasicAuthorizationCodeFlow {
    public boolean basicAttempt(ClientCredencial credencial);
    public boolean isDuplicateAuthenticationMethods();
    public boolean grantTypeCheck(String grantType);
    public boolean authCodeValidation(String code, String clientId) throws JsonMappingException, JsonProcessingException;
}
