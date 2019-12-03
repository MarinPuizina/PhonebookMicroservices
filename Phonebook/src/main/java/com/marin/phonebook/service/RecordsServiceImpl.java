package com.marin.phonebook.service;

import com.marin.phonebook.data.RecordEntity;
import com.marin.phonebook.data.RecordsRepository;
import com.marin.phonebook.shared.RecordDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

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

}
