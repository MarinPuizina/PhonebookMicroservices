package com.marin.phonebook.ui.model.response;

import lombok.Data;

@Data
public class RecordResponseModel {

    private String personName;
    private String recordType;
    private String phoneNumber;

}
