package com.florindanciu.opherUpbackend.item.repository;

import com.florindanciu.opherUpbackend.auth.model.AppUser;
import com.florindanciu.opherUpbackend.item.model.Category;
import com.florindanciu.opherUpbackend.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> getItemsByCategories(Category category);
    List<Item> getItemsByItemName(String name);
    List<Item> getItemsByLocation(String location);
    List<Item> getAllByUser(AppUser user);
    List<Item> getItemsByItemNameAndLocation(String name, String location);
}
