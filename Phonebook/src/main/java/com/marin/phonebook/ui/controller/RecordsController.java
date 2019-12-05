package com.marin.phonebook.ui.controller;

import com.marin.phonebook.exception.RecordsServiceException;
import com.marin.phonebook.service.RecordsService;
import com.marin.phonebook.shared.Constants;
import com.marin.phonebook.shared.RecordDto;
import com.marin.phonebook.ui.model.request.CreateUpdateRecordRequestModel;
import com.marin.phonebook.ui.model.request.DeleteRecordRequestModel;
import com.marin.phonebook.ui.model.response.RecordResponseModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/records") // http://localhost:8011/phonebook-ms/records
public class RecordsController {

    private final Environment environment;
    private final RecordsService recordsService;

    /**
     * http://localhost:8011/phonebook-ms/records/status
     *
     * Microservices status check.
     *
     * @return microservice's status and on which port it is operating
     */
    @GetMapping(path = "/status")
    public String getStatus() {
        return "Phonebook Microservice is working on the port." + environment.getProperty("local.server.port");
    }

    /**
     * http://localhost:8011/phonebook-ms/records
     *
     * Create or update user in database.
     *
     * @param requestModel JSON Request model
     * @return Http status
     */
    @PostMapping
    public ResponseEntity createUpdateRecord(@Valid @RequestBody CreateUpdateRecordRequestModel requestModel) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RecordDto recordDto = modelMapper.map(requestModel, RecordDto.class);

        if (recordsService.createUpdateRecord(recordDto).equals(Constants.CREATED)) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else if (recordsService.createUpdateRecord(recordDto).equals(Constants.UPDATED)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * http://localhost:8011/phonebook-ms/records/names/{name}
     *
     * Get all records via using the person's name
     *
     * @param name Name of the person
     * @return JSON list
     */
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

    /**
     * http://localhost:8011/phonebook-ms/records/phone_numbers/{phoneNumber}
     *
     * Get record via using the phone number.
     *
     * @param phoneNumber Person's phone number
     * @return JSON list
     */
    @GetMapping(path = "/phone_numbers/{phoneNumber}")
    public ResponseEntity<List<RecordResponseModel>> getRecordByPhoneNumber(@PathVariable String phoneNumber) {

        List<RecordDto> records = recordsService.findRecordUsingPhoneNumber(phoneNumber);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<RecordResponseModel> returnValue = new ArrayList<>();
        for (RecordDto record : records) {
            returnValue.add(modelMapper.map(record, RecordResponseModel.class));
        }

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    /**
     * http://localhost:8011/phonebook-ms/records
     *
     * Delete record from database using the JSON Request model.
     *
     * @param requestModel JSON Request model
     * @return Http status
     */
    @DeleteMapping
    public ResponseEntity deleteRecord(@RequestBody DeleteRecordRequestModel requestModel) throws RecordsServiceException {

        recordsService.deleteRecord(requestModel.getPhoneNumber());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * http://localhost:8011/phonebook-ms/records/phone_numbers/{phoneNumber}
     *
     * Delete record from database using the phone number.
     *
     * @param phoneNumber Person's phone number
     * @return Http status
     */
    @DeleteMapping(path = "/phone_numbers/{phoneNumber}")
    public ResponseEntity deleteRecordUri(@PathVariable String phoneNumber) {

        recordsService.deleteRecord(phoneNumber);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
