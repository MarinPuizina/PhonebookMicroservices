package com.marin.phonebook.service;

import com.marin.phonebook.shared.RecordDto;

public interface RecordsService {

    String createUpdateRecord(RecordDto recordDetails);
    RecordDto createRecord(RecordDto recordDetails);
    RecordDto updateRecord(RecordDto recordDetails);

}
