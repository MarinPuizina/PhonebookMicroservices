package com.marin.phonebook.ui.model.request;

import lombok.Data;

@Data
public class CreateUpdateRecordRequestModel {

    private String personsName;
    private String recordType;
    private Integer phoneNumber;

}
