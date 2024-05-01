package com.auth.client.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth.client.controllers.requests.CallBackRequest;
import com.auth.client.infrastructures.rest_client.responses.TokenEndpointResponse;
import com.auth.client.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ClientController {

    private final TokenService tokenService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ClientController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("access_token", null);
        model.addAttribute("token_type", null);
        return "index";
    }

    @GetMapping("/authorize")
    public String redirectAuthEndpoint() {
        logger.info("START: GET /authorize");
        // TODO: 認証をつけてDBから取得できるようにする
        URI uri = UriComponentsBuilder.newInstance()
                .queryParam("response_type", "code")
                .queryParam("client_id", "1")
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
    public String callbackClientEndpoint(@RequestParam Map<String, String> params, Model model) {
        try {
            logger.info("START GET /callback");

            ObjectMapper mapper = new ObjectMapper();
            CallBackRequest request = mapper.convertValue(params, CallBackRequest.class);
            logger.info("<<< MAPPING REQUEST >>> ");

            TokenEndpointResponse res = tokenService.execute(request);
            logger.info("<<< tokenService.execute >>> ");

            Map<String, Object> token = new HashMap<>();
            token.put("access_token", res.getAccessToken());
            token.put("token_type", res.getTokenType());
            model.addAllAttributes(token);

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "400";
        }
    }
}
