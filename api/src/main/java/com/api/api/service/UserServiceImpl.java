package com.api.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.api.DTO.UserDto;
import com.api.api.entity.User;
import com.api.api.service.DAO.UserDAO;
import com.api.api.service.Interfaces.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;



    @Override
    public List<UserDto> findAll() {
        return userDAO.findAll();
    }

     @Override
    public Optional<UserDto> findById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); 
        return user.getId(); // Devuelve el ID del usuario
    }
}
