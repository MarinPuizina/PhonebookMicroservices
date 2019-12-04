package com.marin.phonebook.service;

import com.marin.phonebook.data.RecordEntity;
import com.marin.phonebook.data.RecordsRepository;
import com.marin.phonebook.shared.RecordDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RecordsServiceImpl implements RecordsService {

    private final RecordsRepository recordsRepository;

    @Override
    public String createUpdateRecord(RecordDto recordDetails) {

        if(recordsRepository.findByPhoneNumber(recordDetails.getPhoneNumber()) == null) {
            createRecord(recordDetails);
            return "Created";
        } else {
            updateRecord(recordDetails);
            return "Updated";
        }

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
    public RecordDto findRecordUsingPhoneNumber(String phoneNumber) {
// TODO java.lang.IllegalArgumentException: source cannot be null
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RecordEntity record = recordsRepository.findByPhoneNumber(phoneNumber);

        RecordDto returnValue = modelMapper.map(record, RecordDto.class);

        return returnValue;
    }

}
