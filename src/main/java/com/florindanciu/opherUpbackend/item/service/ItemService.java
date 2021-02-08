package com.florindanciu.opherUpbackend.item.service;

import com.florindanciu.opherUpbackend.auth.model.AppUser;
import com.florindanciu.opherUpbackend.auth.repository.AppUserRepository;
import com.florindanciu.opherUpbackend.item.dto.ItemConverter;
import com.florindanciu.opherUpbackend.item.dto.ItemDto;
import com.florindanciu.opherUpbackend.item.exception.ItemNotFoundException;
import com.florindanciu.opherUpbackend.item.model.Category;
import com.florindanciu.opherUpbackend.item.model.EnumCategory;
import com.florindanciu.opherUpbackend.item.model.Item;
import com.florindanciu.opherUpbackend.item.repository.CategoryRepository;
import com.florindanciu.opherUpbackend.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ItemConverter converter;

    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return converter.modelToDto(items);
    }

    public List<ItemDto> getItemsByCategoryId(UUID id) {
        Category category = categoryRepository.getOne(id);
        List<Item> items = itemRepository.getItemsByCategories(category);
        List<ItemDto> itemDtoList = converter.modelToDto(items);
        itemDtoList.forEach(itemDto -> itemDto.setCategory(category.getEnumCategory().name()));
        return itemDtoList;
    }

    public ItemDto getItemById(UUID id) {
        Item item = itemRepository.findById(id).
                orElseThrow(() -> new ItemNotFoundException(id));
        return converter.modelToDto(item);
    }

    public List<ItemDto> getItemsByName(String name) {
        List<Item> items = itemRepository.getItemsByName(name);
        return converter.modelToDto(items);
    }

    public List<ItemDto> getItemsByLocation(String location) {
        List<Item> items = itemRepository.getItemsByLocation(location);
        return converter.modelToDto(items);
    }

    public List<ItemDto> getItemsByNameAndLocation(String name, String location) {
        List<Item> items = itemRepository.getItemsByNameAndLocation(name, location);
        return converter.modelToDto(items);
    }

    public Boolean addItem(ItemDto itemDto, UUID userId) {
        Item item = converter.dtoToModel(itemDto);
        Set<Category> categories = new HashSet<>();
        String strCategory = itemDto.getCategory();

        AppUser appUser = appUserRepository.getOne(userId);

        switch (strCategory) {
            case "auto" -> {
                Category auto = categoryRepository.findByEnumCategory(EnumCategory.Vehicles);
                categories.add(auto);
            }
            case "realEstate" -> {
                Category realEstate = categoryRepository.findByEnumCategory(EnumCategory.Estates);
                categories.add(realEstate);
            }
            case "jobs" -> {
                Category jobs = categoryRepository.findByEnumCategory(EnumCategory.Jobs);
                categories.add(jobs);
            }
            case "electronics" -> {
                Category electronics = categoryRepository.findByEnumCategory(EnumCategory.Electronics);
                categories.add(electronics);
            }
            case "fashion" -> {
                Category fashion = categoryRepository.findByEnumCategory(EnumCategory.Fashion);
                categories.add(fashion);
            }
            case "home" -> {
                Category home = categoryRepository.findByEnumCategory(EnumCategory.Home);
                categories.add(home);
            }
            case "pets" -> {
                Category pets = categoryRepository.findByEnumCategory(EnumCategory.Pets);
                categories.add(pets);
            }
            case "services" -> {
                Category services = categoryRepository.findByEnumCategory(EnumCategory.Services);
                categories.add(services);
            }
        }
        item.setCategories(categories);
        item.setUser(appUser);
        itemRepository.save(item);
        if (itemRepository.existsById(item.getId())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean deleteItem(UUID id) {
        itemRepository.deleteById(id);
        if (itemRepository.existsById(id)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
