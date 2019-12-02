package com.marin.phonebook.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("records") // http://localhost:8080/records
public class RecordsController {

    @Autowired
    private Environment environment;

    @GetMapping("/status")
    public String getStatus() {
        return "Phonebook Microservice is working on the port." + environment.getProperty("local.server.port");
    }

}
