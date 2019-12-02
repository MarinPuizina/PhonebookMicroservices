package com.marin.phonebook.ui.controller;

import com.marin.phonebook.ui.model.request.CreateUpdateRecordRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("records") // http://localhost:8080/records
public class RecordsController {

    @Autowired
    private Environment environment;

    @GetMapping("/status")
    public String getStatus() {
        return "Phonebook Microservice is working on the port." + environment.getProperty("local.server.port");
    }

    @PostMapping
    public String createUpdateRecord(@Valid @RequestBody CreateUpdateRecordRequestModel requestModel) {
        return "createUpdateRecord() is called.";
    }

}
