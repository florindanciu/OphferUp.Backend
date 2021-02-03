package com.florindanciu.opherUpbackend.item.service;

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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    public Boolean addItem(ItemDto itemDto) {
        Item item = converter.dtoToModel(itemDto);
        Set<Category> categories = new HashSet<>();
        String strCategory = itemDto.getCategory();

        switch (strCategory) {
            case "auto" -> {
                Category auto = categoryRepository.findByEnumCategory(EnumCategory.AUTO_AND_MOTO);
                categories.add(auto);
            }
            case "realEstate" -> {
                Category realEstate = categoryRepository.findByEnumCategory(EnumCategory.REAL_ESTATE);
                categories.add(realEstate);
            }
            case "jobs" -> {
                Category jobs = categoryRepository.findByEnumCategory(EnumCategory.JOBS);
                categories.add(jobs);
            }
            case "electronics" -> {
                Category electronics = categoryRepository.findByEnumCategory(EnumCategory.ELECTRONICS_AND_APPLIANCES);
                categories.add(electronics);
            }
            case "fashion" -> {
                Category fashion = categoryRepository.findByEnumCategory(EnumCategory.FASHION_AND_CARE);
                categories.add(fashion);
            }
            case "home" -> {
                Category home = categoryRepository.findByEnumCategory(EnumCategory.HOME_AND_GARDEN);
                categories.add(home);
            }
            case "pets" -> {
                Category pets = categoryRepository.findByEnumCategory(EnumCategory.PETS);
                categories.add(pets);
            }
            case "services" -> {
                Category services = categoryRepository.findByEnumCategory(EnumCategory.SERVICES);
                categories.add(services);
            }
        }
        item.setCategories(categories);
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
