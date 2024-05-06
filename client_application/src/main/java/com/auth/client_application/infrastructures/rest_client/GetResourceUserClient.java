package com.auth.client_application.infrastructures.rest_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import com.auth.client_application.infrastructures.json_response.JsonUtil;
import com.auth.client_application.infrastructures.rest_client.responses.ResourceUserResponse;

@Repository
public class GetResourceUserClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestClient client;
    private final String getResourceUserEndpoint = "http://host.docker.internal:8001/user";

    public GetResourceUserClient() {
        this.client = RestClient.create();
    }

    public ResourceUserResponse get(String header) throws Exception {
        logger.info("START GET " + this.getResourceUserEndpoint);
        ResponseEntity<ResourceUserResponse> response = this.client.get()
                .uri(this.getResourceUserEndpoint)
                .header("authorization", header)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    logger.info("<<< ERROR GET >>>");
                    logger.info(res.toString());
                    try {
                        throw new Exception(res.toString());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                })
                .toEntity(ResourceUserResponse.class);
        logger.info("FINISHED GET " + this.getResourceUserEndpoint);

        String json = JsonUtil.toJson(response);
        logger.info(json);

        return response.getBody();
    }

}
