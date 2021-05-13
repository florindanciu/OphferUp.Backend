package com.florindanciu.opherUpbackend.auth.service;

import com.florindanciu.opherUpbackend.auth.dto.UserConverter;
import com.florindanciu.opherUpbackend.auth.dto.UserDto;
import com.florindanciu.opherUpbackend.auth.exception.UserNotFoundException;
import com.florindanciu.opherUpbackend.auth.model.AppUser;
import com.florindanciu.opherUpbackend.auth.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final AppUserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserService(AppUserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public UserDto getUserById(UUID userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userConverter.modelToDto(user);
    }

    //TODO: add methods for content api's
}
