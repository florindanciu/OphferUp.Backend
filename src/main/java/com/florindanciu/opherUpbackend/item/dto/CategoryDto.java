package com.florindanciu.opherUpbackend.item.dto;

import com.florindanciu.opherUpbackend.item.model.EnumCategory;
import com.florindanciu.opherUpbackend.item.model.Item;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CategoryDto {

    private UUID id;
    private EnumCategory enumCategory;
}
