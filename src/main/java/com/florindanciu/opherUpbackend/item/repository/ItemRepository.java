package com.florindanciu.opherUpbackend.item.repository;

import com.florindanciu.opherUpbackend.item.model.Category;
import com.florindanciu.opherUpbackend.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> getItemsByCategories(Category category);
    List<Item> getItemsByName(String name);
    List<Item> getItemsByLocation(String location);
    List<Item> getItemsByNameAndLocation(String name, String location);
}
