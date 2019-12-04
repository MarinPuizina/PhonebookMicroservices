package com.marin.phonebook.ui.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateUpdateRecordRequestModel {

    @NotEmpty
    @NotNull
    private String personName;

    @NotEmpty
    @NotNull
    private String recordType;

    @NotEmpty
    @NotNull
    private String phoneNumber;

}
