package com.example.authorization_server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelthCheckController {

    // helthcheck
    @GetMapping("/helthcheck")
    public String helthcheckEndpoint() {
        return "ok";
    }
}
