package com.auth.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {
    
    @GetMapping("/")
    public String authorizeView()
    {
        return "index";
    }
}
