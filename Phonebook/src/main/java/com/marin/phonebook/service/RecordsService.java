package com.marin.phonebook.service;

import com.marin.phonebook.shared.RecordDto;

import java.util.List;

public interface RecordsService {

    String createUpdateRecord(RecordDto recordDetails);
    RecordDto createRecord(RecordDto recordDetails);
    RecordDto updateRecord(RecordDto recordDetails);
    List<RecordDto> findRecordsUsingPersonName(String personName);
    RecordDto findRecordUsingPhoneNumber(String phoneNumber);
    void deleteRecord(String phoneNumber);
}
