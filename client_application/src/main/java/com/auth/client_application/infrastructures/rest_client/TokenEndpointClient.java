package com.auth.client_application.infrastructures.rest_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import com.auth.client_application.infrastructures.json_response.JsonUtil;
import com.auth.client_application.infrastructures.rest_client.requests.TokenEndpointRequest;
import com.auth.client_application.infrastructures.rest_client.responses.TokenEndpointResponse;

@Repository
public class TokenEndpointClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestClient client;
    private final String tokenEndpointUri = "http://host.docker.internal:8000/token";

    public TokenEndpointClient() {
        this.client = RestClient.create();
    }

    public TokenEndpointResponse post(
            TokenEndpointRequest body,
            String clientId,
            String password) throws Exception {
        logger.info("START POST " + this.tokenEndpointUri);
        logger.info("Header, " + "basic " + clientId + ":" + password);

        ResponseEntity<TokenEndpointResponse> response = this.client.post()
                .uri(this.tokenEndpointUri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", "basic " + clientId + ":" + password)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    logger.info("<<< ERROR POST >>>");
                    logger.info(res.toString());
                    try {
                        throw new Exception(res.toString());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                })
                .toEntity(TokenEndpointResponse.class);
        logger.info("FINISHED POST " + this.tokenEndpointUri);

        String json = JsonUtil.toJson(response);
        logger.info(json);

        return response.getBody();
    }

}
