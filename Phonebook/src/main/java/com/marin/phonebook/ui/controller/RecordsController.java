package com.marin.phonebook.ui.controller;

import com.marin.phonebook.service.RecordsService;
import com.marin.phonebook.shared.RecordDto;
import com.marin.phonebook.ui.model.request.CreateUpdateRecordRequestModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("records") // http://localhost:8080/records
public class RecordsController {

    private final Environment environment;
    private final RecordsService recordsService;

    @GetMapping("/status")
    public String getStatus() {
        return "Phonebook Microservice is working on the port." + environment.getProperty("local.server.port");
    }

    @PostMapping
    public String createUpdateRecord(@Valid @RequestBody CreateUpdateRecordRequestModel requestModel) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RecordDto recordDto = modelMapper.map(requestModel, RecordDto.class);

        recordsService.createRecord(recordDto);

        return "createUpdateRecord() is called.";
    }

}
