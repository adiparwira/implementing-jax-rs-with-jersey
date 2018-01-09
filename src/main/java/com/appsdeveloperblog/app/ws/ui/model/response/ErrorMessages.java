/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.ui.model.response;

/**
 *
 * @author amor
 */
public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field. Please check the documentation for required fields"),
    RECORD_ALREADY_EXIST("Record alredy exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record with provided is is not found"),
    AUTHENTICATION_FAILED("Authentications failed");
    

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
