/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amor
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    DAO database;

    public UserDTO authenticate(String userName, String password) throws AuthenticationException {
        UsersService usersService = new UsersServiceImpl();
        UserDTO storedUser = usersService.getUserByUserName(userName);

        if (storedUser == null) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }

        String encryptedPassword = null;
        encryptedPassword = new UserProfileUtils()
                .generateSecurePassword(password, storedUser.getSalt());

        boolean authenticated = false;
        if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
            if (userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {
                authenticated = true;
            }
        }

        if (!authenticated) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }

        return storedUser;
    }

    public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
        String returnValue = null;

        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;

        byte[] encryptedAccessToken = null;
        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();

        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        userProfile.setToken(tokenToSaveToDatabase);
        updateUserProfile(userProfile);

        return returnValue;
    }
    
    public void resetSecurityCridentials(String password, UserDTO userProfile) {
        // Gerenerate a new salt
        UserProfileUtils userUtils = new UserProfileUtils();
        String salt = userUtils.getSalt(30);
        
        // Generate a new password 
        String securePassword = userUtils.generateSecurePassword(password, salt);
        userProfile.setSalt(salt);
        userProfile.setEncryptedPassword(securePassword);
        
        // Update user profile 
        updateUserProfile(userProfile);
 
    }

    private void updateUserProfile(UserDTO userProfile) {
        this.database = new MySQLDAO();

        try {
            this.database.openConnection();
            this.database.updateUser(userProfile);
        } finally {
            this.database.closeConnection();
        }
    }

}
