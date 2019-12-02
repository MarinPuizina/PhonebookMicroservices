package com.marin.phonebook.shared;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecordDto implements Serializable {

    private static final long serialVersionUID = -195066237002034168L;

    private String personName;
    private String recordType;
    private String phoneNumber;

}
