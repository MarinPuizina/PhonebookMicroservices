package com.marin.phonebook.service;

import com.marin.phonebook.data.RecordEntity;
import com.marin.phonebook.data.RecordsRepository;
import com.marin.phonebook.shared.RecordDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class RecordsServiceImplTest {

    @InjectMocks
    RecordsServiceImpl recordsService;

    @Mock
    RecordsRepository recordsRepository;

    RecordDto mockDto;
    RecordEntity mockEntity;

    @BeforeEach
    void setUp() {

        initMocks(this);

        mockDto = new RecordDto();
        mockDto.setPersonName("Marin Puizina");
        mockDto.setPhoneNumber("2343939123291");
        mockDto.setRecordType("mobilni");

        mockEntity = new RecordEntity();
        mockEntity.setPersonName("Marin Puizina");
        mockEntity.setPhoneNumber("2343939123291");
        mockEntity.setRecordType("mobilni");

    }

    @Test
    void createUpdateRecordShouldReturnCreate() {

        when(recordsRepository.findByPhoneNumber(mockDto.getPhoneNumber())).thenReturn(null);

        String testValue = recordsService.createUpdateRecord(mockDto);

        assertEquals("Created", testValue);

    }

    @Test
    void findRecordsUsingPersonNameShouldReturnEmptyList() {

        when(recordsRepository.findByPersonName(mockDto.getPersonName())).thenReturn(new ArrayList<>());

        assertEquals(new ArrayList<>(), recordsService.findRecordsUsingPersonName(mockDto.getPersonName()));

    }

    @Test
    void findRecordsUsingPersonNameShouldReturnValues() {

        List<RecordEntity> records = new ArrayList<>();
        records.add(mockEntity);
        List<RecordDto> testValue = new ArrayList<>();
        testValue.add(mockDto);

        when(recordsRepository.findByPersonName(mockDto.getPersonName())).thenReturn(records);

        assertEquals(testValue.get(0), recordsService.findRecordsUsingPersonName(mockDto.getPersonName()).get(0));

    }

    @Test
    void findRecordUsingPhoneNumberShouldReturnEmptyList() {

        when(recordsRepository.findByPhoneNumber(anyString())).thenReturn(null);

        List<RecordDto> testValue = recordsService.findRecordUsingPhoneNumber(anyString());
        assertEquals(new ArrayList<>(), testValue);

    }

    @Test
    void findRecordUsingPhoneNumberShouldReturnValue() {

        List<RecordDto> mockList = new ArrayList<>();
        mockList.add(mockDto);

        when(recordsRepository.findByPhoneNumber(anyString())).thenReturn(mockEntity);

        List<RecordDto> testValue = recordsService.findRecordUsingPhoneNumber(anyString());
        assertEquals(mockList, testValue);

    }

}