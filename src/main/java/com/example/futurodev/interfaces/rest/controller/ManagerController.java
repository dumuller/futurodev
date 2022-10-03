package com.example.futurodev.interfaces.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {

    @GetMapping("/sobrenome")
    public String getSobrenome() {
        return "sobrenome";
    }
}
