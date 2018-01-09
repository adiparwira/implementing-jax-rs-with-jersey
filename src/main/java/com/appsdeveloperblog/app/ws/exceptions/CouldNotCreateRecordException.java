/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.exceptions;

/**
 *
 * @author amor
 */
public class CouldNotCreateRecordException extends RuntimeException {

    private static final long serialVersionUID = -5865565853821170976L;

    public CouldNotCreateRecordException(String message) {
        super(message);
    }
}
