/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.io.dao;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/**
 *
 * @author amor
 */
public interface DAO {
    void openConnection();
    UserDTO getUserByUserName(String userName);
    UserDTO saveUser(UserDTO user);
    UserDTO getUser(String id);
    void updateUser(UserDTO userProfile);
    void closeConnection();
}
