package com.florindanciu.opherUpbackend.auth.dto;

import com.florindanciu.opherUpbackend.auth.model.Role;
import com.florindanciu.opherUpbackend.item.model.Item;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
public class UserDto {

    private UUID id;
    private String username;
    private String email;
    private final Set<Item> items = new HashSet<>();
}
