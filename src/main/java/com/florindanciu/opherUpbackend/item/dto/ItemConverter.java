package com.florindanciu.opherUpbackend.item.dto;

import com.florindanciu.opherUpbackend.item.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ItemConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ItemDto modelToDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    public List<ItemDto> modelToDto(List<Item> items) {
        return items.stream().map(this::modelToDto).collect(Collectors.toList());
    }

    public Item dtoToModel(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }

    public List<Item> dtoToModel(List<ItemDto> itemDtoList) {
        return itemDtoList.stream().map(this::dtoToModel).collect(Collectors.toList());
    }
}
