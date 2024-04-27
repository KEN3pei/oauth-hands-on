package com.auth.client.controllers;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class ClientController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/authorize")
    public String redirectAuthEndpoint()
    {
        logger.info("START: GET /authorize");
        URI uri = UriComponentsBuilder.newInstance()
                        .queryParam("response_type", "code")
                        .queryParam("client_id","1")
                        .queryParam("client_secret", "password")
                        .queryParam("redirect_uri", "http://localhost:8002/callback")
                        .queryParam("state", "2aljs8")
                        .build()
                        .encode()
                        .toUri();

        logger.info("redirect:http://localhost:8000/authorize" + uri);
        return "redirect:http://localhost:8000/authorize" + uri;
    }

    @GetMapping("/callback")
    public String callbackClientEndpoint()
    {
        return "index";
    }
}
