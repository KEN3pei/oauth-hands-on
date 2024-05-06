package com.example.protected_resource.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.protected_resource.config.EnvProperties;
import com.example.protected_resource.services.UserGetByTokenService;
import com.example.protected_resource.controllers.responses.ClientFromTokenResponse;

@RestController
public class ResourceController {
    private final EnvProperties envProperties;
    private final UserGetByTokenService userGetByTokenService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ResourceController(
        EnvProperties envProperties,
        UserGetByTokenService userGetByTokenService
    ) {
        this.envProperties = envProperties;
        this.userGetByTokenService = userGetByTokenService;
    }

    // @GetMapping("/")
    // public ResponseEntity<List<IndexUserResponse>> index() {

    //     List<UsersRecord> users = this.userGetAllService.execute();
    //     List<IndexUserResponse> responseUsers = ListIndexUserResponseConverter.toResponse(users);
    //     return new ResponseEntity<List<IndexUserResponse>>(responseUsers, HttpStatus.OK);
    // }

    // アクセストークンでユーザー情報を取得するAPI
    @GetMapping("/user")
    public ResponseEntity<ClientFromTokenResponse> user(@RequestHeader("authorization") String auth) {
        String tokenStr = auth.replace("Bearer ", "");
        logger.info("authorization Header: " + tokenStr);

        ClientFromTokenResponse respose = this.userGetByTokenService.execute(tokenStr);
        logger.info("ClientFromTokenResponse: " + respose.toString());

        return new ResponseEntity<ClientFromTokenResponse>(respose, HttpStatus.OK);
    }
}
