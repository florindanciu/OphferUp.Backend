package com.florindanciu.opherUpbackend.item.repository;

import com.florindanciu.opherUpbackend.item.model.Category;
import com.florindanciu.opherUpbackend.item.model.EnumCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByEnumCategory(EnumCategory enumCategory);
}
