package com.marin.phonebook.ui.controller;

import com.marin.phonebook.service.RecordsService;
import com.marin.phonebook.shared.RecordDto;
import com.marin.phonebook.ui.model.request.CreateUpdateRecordRequestModel;
import com.marin.phonebook.ui.model.response.RecordResponseModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("records") // http://localhost:8011/records
public class RecordsController {

    private final Environment environment;
    private final RecordsService recordsService;

    @GetMapping("/status") // http://localhost:8011/records/status
    public String getStatus() {
        return "Phonebook Microservice is working on the port." + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity createUpdateRecord(@Valid @RequestBody CreateUpdateRecordRequestModel requestModel) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RecordDto recordDto = modelMapper.map(requestModel, RecordDto.class);

        if (recordsService.createUpdateRecord(recordDto).equals("Created")) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else if (recordsService.createUpdateRecord(recordDto).equals("Updated")) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/names/{name}") // http://localhost:8011/phonebook-ms/records/names/{name}
    public ResponseEntity<List<RecordResponseModel>> getRecordByName(@PathVariable String name) {

        List<RecordDto> records = recordsService.findRecordsUsingPersonName(name);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<RecordResponseModel> returnValue = new ArrayList<>();
        for (RecordDto record : records) {
            returnValue.add(modelMapper.map(record, RecordResponseModel.class));
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
    /*
    @GetMapping
    public ResponseEntity<List<RecordResponseModel>> getRecordByPhone(@RequestParam(name = "phoneNumber", required = true, defaultValue = "") String phoneNumber) {

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RecordResponseModel>> getRecordByNameAndPhone(
            @RequestParam(name = "personName", required = true, defaultValue = "") String personName,
            @RequestParam(name = "phoneNumber", required = true, defaultValue = "") String phoneNumber) {

        return new ResponseEntity(HttpStatus.OK);
    }
    */
}
