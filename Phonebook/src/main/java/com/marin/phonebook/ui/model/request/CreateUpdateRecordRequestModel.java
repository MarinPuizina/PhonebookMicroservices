package com.marin.phonebook.ui.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateUpdateRecordRequestModel {

    @NotNull
    private String personsName;

    @NotNull
    private String recordType;

    @NotNull
    private Integer phoneNumber;

}
