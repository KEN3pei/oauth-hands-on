package com.auth.client_application.infrastructures.client;

public interface ClientRepositoryInterface {
    public String findByClientId(String clientId) throws Exception;
}
