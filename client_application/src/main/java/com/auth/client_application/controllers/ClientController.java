package com.auth.client_application.controllers;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.auth.client_application.controllers.requests.CallBackRequest;
import com.auth.client_application.infrastructures.rest_client.responses.TokenEndpointResponse;
import com.auth.client_application.services.CustomUserDetails;
import com.auth.client_application.services.GetResourceUserWithTokenService;
import com.auth.client_application.services.TokenService;
import com.auth.client_application.services.StoreUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.auth.client_application.infrastructures.rest_client.responses.ResourceUserResponse;

@Controller
public class ClientController {
    private final TokenService tokenService;
    private final StoreUserService storeUserService;
    private final GetResourceUserWithTokenService getResourceUserWithTokenService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ClientController(
            TokenService tokenService,
            StoreUserService storeUserService,
            GetResourceUserWithTokenService getResourceUserWithTokenService) {
        this.tokenService = tokenService;
        this.storeUserService = storeUserService;
        this.getResourceUserWithTokenService = getResourceUserWithTokenService;
    }

    @GetMapping("/")
    public String index(Model model) {
        String secretkey = System.getenv("PUB_KEY_PATH");
        logger.info("secretkey: " + secretkey);
        // ClassPathResource

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
    public String callbackClientEndpoint(
            @RequestParam Map<String, String> params,
            Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
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

            logger.info("Login userId: " + userDetails.getUserId());
            // access_tokenをログイン中ユーザのidで保存
            // FIXME: このuserIdを保存先としていいのか不明（調査の必要あり）
            if (null == userDetails.getUserId()) {
                throw new Exception("unauthorization you should login.");
            }
            this.storeUserService.execute(userDetails.getUserId(), res.getAccessToken());
            logger.info("<<< stored access_token >>> ");

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "400";
        }
    }

    @GetMapping("/user")
    public String getResourceUserProxyEndpoint(
            Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        logger.info("Login userId: " + userDetails.getUserId());
        if (null == userDetails.getUserId()) {
            logger.error("unauthorization you should login.");
            return "400";
        }
        try {
            logger.info("access_token: " + userDetails.getAccessToken());
            ResourceUserResponse res = this.getResourceUserWithTokenService.execute(userDetails.getAccessToken());

            Map<String, Object> token = new HashMap<>();
            token.put("client_id", res.getClientId());
            token.put("scope", res.getScope().toString());
            model.addAllAttributes(token);

            return "user";
        } catch (Exception e) {
            e.printStackTrace();
            return "400";
        }
    }
}
