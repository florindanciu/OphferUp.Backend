package com.florindanciu.opherUpbackend.item.repository;

import com.florindanciu.opherUpbackend.item.model.ImagesUrls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImagesUrlsRepository extends JpaRepository<ImagesUrls, UUID> {
}
