package com.marin.phonebook.service;

import com.marin.phonebook.shared.RecordDto;

import java.util.List;

public interface RecordsService {

    String createUpdateRecord(RecordDto recordDetails);
    void createRecord(RecordDto recordDetails);
    void updateRecord(RecordDto recordDetails);
    List<RecordDto> findRecordsUsingPersonName(String personName);
    List<RecordDto> findRecordUsingPhoneNumber(String phoneNumber);
    void deleteRecord(String phoneNumber);
}
