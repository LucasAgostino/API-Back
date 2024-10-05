package com.api.api.service.DAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.api.DTO.UserDto;
import com.api.api.entity.User;
import com.api.api.repository.UserRepository;

@Service
public class UserDAO {
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional (readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                       .map(UserDAO::convertToDto)
                       .collect(Collectors.toList());
    }

    @Transactional (readOnly = true)
    public Optional<UserDto> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserDAO::convertToDto);
    }

    @Transactional (readOnly = true)
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserDAO::convertToDto);
    }

    private static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setSecondName(user.getSecondName());
        userDto.setId(user.getId());
        return userDto;
    }
}
