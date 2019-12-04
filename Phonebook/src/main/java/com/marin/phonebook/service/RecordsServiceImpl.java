package com.marin.phonebook.service;

import com.marin.phonebook.data.RecordEntity;
import com.marin.phonebook.data.RecordsRepository;
import com.marin.phonebook.exception.RecordsServiceException;
import com.marin.phonebook.shared.RecordDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class RecordsServiceImpl implements RecordsService {

    private final RecordsRepository recordsRepository;
    private final List<String> validRecordTypes = Arrays.asList("mobilni", "poslovni", "fiksni");

    @Override
    public String createUpdateRecord(RecordDto recordDetails) {

        if( recordsRepository.findByPhoneNumber( recordDetails.getPhoneNumber() ) == null
                && validRecordTypes.contains( recordDetails.getRecordType() ) ) {
            createRecord(recordDetails);
            return "Created";
        } else if ( validRecordTypes.contains( recordDetails.getRecordType() ) ) {
            updateRecord(recordDetails);
            return "Updated";
        }

        return "";
    }

    @Override
    public RecordDto createRecord(RecordDto recordDetails) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RecordEntity recordEntity = modelMapper.map(recordDetails, RecordEntity.class);

        recordsRepository.save(recordEntity);

        // See if we want to make it void or maybe to return valid String
        return null;
    }

    @Override
    public RecordDto updateRecord(RecordDto recordDetails) {

        recordsRepository.updateRecord(recordDetails.getPersonName(), recordDetails.getRecordType(), recordDetails.getPhoneNumber());
        // See if we want to make it void or maybe to return valid String
        return null;
    }

    @Override
    public List<RecordDto> findRecordsUsingPersonName(String personName) {
        // TODO java.lang.IllegalArgumentException: source cannot be null
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Iterable<RecordEntity> records = recordsRepository.findByPersonName(personName);

        List<RecordDto> returnValue = new ArrayList<>();

        for (RecordEntity record : records) {
            returnValue.add(modelMapper.map(record, RecordDto.class));
        }

        return returnValue;
    }

    @Override
    public List<RecordDto> findRecordUsingPhoneNumber(String phoneNumber) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RecordEntity record = recordsRepository.findByPhoneNumber(phoneNumber);

        List<RecordDto> returnValue = new ArrayList<>();

        if (record == null) {
            return returnValue;
        }

        returnValue.add(modelMapper.map(record, RecordDto.class));

        return returnValue;
    }

    @Override
    public void deleteRecord(String phoneNumber) throws RecordsServiceException{
        // TODO java.lang.IllegalArgumentException: source cannot be null
        RecordEntity record = recordsRepository.findByPhoneNumber(phoneNumber);

        if (record == null || record.getPersonName().isEmpty()) {
            throw new RecordsServiceException("Record wasn't found in the database.");
        }

        recordsRepository.deleteByPhoneNumber(phoneNumber);

    }

}
