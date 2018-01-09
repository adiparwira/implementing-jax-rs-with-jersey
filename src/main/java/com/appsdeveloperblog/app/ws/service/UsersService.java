/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

/**
 *
 * @author amor
 */
public interface UsersService {
    public UserDTO createUser(UserDTO userDto);
    public UserDTO getUser(String id);
    public UserDTO getUserByUserName(String userName);
}
