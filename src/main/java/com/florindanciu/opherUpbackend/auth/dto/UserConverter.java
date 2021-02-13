package com.florindanciu.opherUpbackend.auth.dto;

import com.florindanciu.opherUpbackend.auth.model.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto modelToDto(AppUser user) {
        return modelMapper.map(user, UserDto.class);
    }
}
