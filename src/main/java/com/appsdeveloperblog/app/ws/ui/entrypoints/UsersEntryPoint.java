/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.ui.entrypoints;

import com.appsdeveloperblog.app.ws.annotations.Secured;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.service.impl.UsersServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.CreateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserProfileRest;
import com.mchange.v2.beans.BeansUtils;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author amor
 */
@Path("/users")
public class UsersEntryPoint {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest createUser(CreateUserRequestModel requestObject) {
        UserProfileRest returnModel = new UserProfileRest();
        
        //prepare user dto
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDto);
        
        //creste new user
        UsersService userService = new UsersServiceImpl();
        UserDTO createdUserProfile = userService.createUser(userDto);
        
        //prepare response
        BeanUtils.copyProperties(createdUserProfile, returnModel);
                
        return returnModel;
    }
    
    @Secured
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest getUserProfile(@PathParam("id") String id) {
        UserProfileRest returnValue = null;
        UsersService userService = new UsersServiceImpl();
        UserDTO userProfile = userService.getUser(id);
        
        returnValue = new UserProfileRest();
        BeanUtils.copyProperties(userProfile, returnValue);
        
        return returnValue;
    }
    
}
