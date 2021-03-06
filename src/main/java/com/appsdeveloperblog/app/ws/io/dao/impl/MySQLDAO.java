/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.app.ws.io.dao.impl;

import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.utils.HibernateUtils;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author amor
 */
public class MySQLDAO implements DAO {

    Session session;

    public void openConnection() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.openSession();
    }

    public UserDTO getUserByUserName(String userName) {
        UserDTO userDto = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);

        criteria.select(profileRoot);

        criteria.where(cb.equal(profileRoot.get("email"), userName));

        Query<UserEntity> query = session.createQuery(criteria);

        List<UserEntity> resultList = query.getResultList();

        if (resultList != null && resultList.size() > 0) {
            UserEntity userEntity = resultList.get(0);
            userDto = new UserDTO();

            BeanUtils.copyProperties(userEntity, userDto);
        }

        return userDto;

    }

    public UserDTO getUser(String id) {
        
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);

        criteria.select(profileRoot);

        criteria.where(cb.equal(profileRoot.get("userId"), id));

        Query<UserEntity> query = session.createQuery(criteria);

        UserEntity userEntity = query.getSingleResult();

        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    public UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();

        returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }
    
    public void updateUser(UserDTO userProfile) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userProfile, userEntity);
        
        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();
    }

    public void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    

}
