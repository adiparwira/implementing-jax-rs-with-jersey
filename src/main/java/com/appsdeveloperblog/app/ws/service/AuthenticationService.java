/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/**
 *
 * @author amor
 */
public interface AuthenticationService {
    UserDTO authenticate(String userName, String password) throws AuthenticationException;
    String issueAccessToken(UserDTO userProfile) throws AuthenticationException;
    void resetSecurityCridentials(String password, UserDTO userProfile);
}
