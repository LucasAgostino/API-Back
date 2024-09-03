package com.api.api.service.Interfaces;

import java.util.List;
import java.util.Optional;

import com.api.api.DTO.UserDto;
public interface UserService {
    
    public List<UserDto> findAll();

    public Optional<UserDto> findById(Long id);

    public Optional<UserDto> findByEmail(String email);

    public Long getCurrentUserId();
}
