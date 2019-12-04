package com.marin.phonebook.ui.controller;

import com.marin.phonebook.exception.RecordsServiceException;
import com.marin.phonebook.service.RecordsService;
import com.marin.phonebook.shared.RecordDto;
import com.marin.phonebook.ui.model.request.CreateUpdateRecordRequestModel;
import com.marin.phonebook.ui.model.request.DeleteRecordRequestModel;
import com.marin.phonebook.ui.model.response.RecordResponseModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("records") // http://localhost:8011/phonebook-ms/records
public class RecordsController {

    private final Environment environment;
    private final RecordsService recordsService;

    // http://localhost:8011/phonebook-ms/records/status
    @GetMapping(path = "/status")
    public String getStatus() {
        return "Phonebook Microservice is working on the port." + environment.getProperty("local.server.port");
    }

    // http://localhost:8011/phonebook-ms/records
    @PostMapping(
            consumes = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
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

    // http://localhost:8011/phonebook-ms/records/names/{name}
    @GetMapping(path = "/names/{name}")
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

    // http://localhost:8011/phonebook-ms/records/phone_numbers/{phoneNumber}
    @GetMapping(path = "/phone_numbers/{phoneNumber}")
    public ResponseEntity<RecordResponseModel> getRecordByPhoneNumber(@PathVariable String phoneNumber) {

        RecordDto record = recordsService.findRecordUsingPhoneNumber(phoneNumber);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RecordResponseModel returnValue = modelMapper.map(record, RecordResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    // http://localhost:8011/phonebook-ms/records
    @DeleteMapping
    public ResponseEntity deleteRecord(@RequestBody DeleteRecordRequestModel requestModel) throws RecordsServiceException {

        recordsService.deleteRecord(requestModel.getPhoneNumber());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
