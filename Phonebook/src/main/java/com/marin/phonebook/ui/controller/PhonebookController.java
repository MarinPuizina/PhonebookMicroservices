package com.marin.phonebook.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("phonebook") // http://localhost:8080/phonebook
public class PhonebookController {

    @GetMapping("/status")
    public String getStatus() {
        return "Phonebook Microservice is working.";
    }

}
