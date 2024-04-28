package com.auth.client.infrastructures.client;

public interface ClientRepositoryInterface {
    public String findByClientId(String clientId) throws Exception;
}
