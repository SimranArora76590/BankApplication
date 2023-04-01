package com.loan.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = {"/", "/swagger-ui", "/swagger-ui+html"})
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }
}
