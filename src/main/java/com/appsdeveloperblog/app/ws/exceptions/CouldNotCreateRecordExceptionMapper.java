/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.exceptions;

import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author amor
 */
@Provider
public class CouldNotCreateRecordExceptionMapper implements ExceptionMapper<CouldNotCreateRecordException> {

    public Response toResponse(CouldNotCreateRecordException exception) {
        ErrorMessage message = new ErrorMessage(exception.getMessage()
                , ErrorMessages.RECORD_ALREADY_EXIST.name(), "http://appsdevelopmentblog.com");
        
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }    
}
