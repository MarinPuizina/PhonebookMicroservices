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
    public RecordDto createUpdateRecord(RecordDto recordDetails) {
        return null;
    }

    @Override
    public RecordDto createRecord(RecordDto recordDetails) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RecordEntity recordEntity = modelMapper.map(recordDetails, RecordEntity.class);

        recordsRepository.save(recordEntity);

        return null;
    }

    @Override
    public RecordDto updateRecord(RecordDto recordDetails) {
        return null;
    }

}
