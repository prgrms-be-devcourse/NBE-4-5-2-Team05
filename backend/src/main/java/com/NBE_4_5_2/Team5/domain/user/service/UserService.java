package com.NBE_4_5_2.Team5.domain.user.service;

import com.NBE_4_5_2.Team5.domain.user.entity.Users;
import com.NBE_4_5_2.Team5.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Users createUser(String username) {
        Users user = new Users();
        user.setUsername(username);
        return userRepository.save(user);
    }

    public Optional<Users> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<Users> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
}