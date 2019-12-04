package com.marin.phonebook.ui.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteRecordRequestModel {

    @NotNull
    private String phoneNumber;

}
