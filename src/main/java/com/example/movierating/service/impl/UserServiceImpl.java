package com.example.movierating.service.impl;

import com.example.movierating.dto.UserDTO;
import com.example.movierating.mapper.UserMapper;
import com.example.movierating.model.User;
import com.example.movierating.repository.UserRepository;
import com.example.movierating.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setId(null);
        User user = userRepository.save(UserMapper.toEntity(userDTO));
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(userDTO.getUsername());
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
