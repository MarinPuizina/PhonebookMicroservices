package com.marin.phonebook.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
/**
 * Enum class used to store error messages used in project
 */
public enum ErrorMessage {

    RECORD_NOT_FOUND("Record wasn't found in the database.");

    private String errorMessage;

    /**
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage used to set it as an error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
