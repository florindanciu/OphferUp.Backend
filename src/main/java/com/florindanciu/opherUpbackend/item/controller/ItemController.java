package com.florindanciu.opherUpbackend.item.controller;

import com.florindanciu.opherUpbackend.item.dto.ItemDto;
import com.florindanciu.opherUpbackend.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<ItemDto> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable UUID id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/category/{id}")
    public List<ItemDto> getItemsByCategoryId(@PathVariable UUID id) {
        return itemService.getItemsByCategoryId(id);
    }

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody ItemDto itemDto) {
        System.out.println(itemDto.getCategory());
        if (itemService.addItem(itemDto)) {
            return ResponseEntity
                    .accepted()
                    .body("Saved successfully");
        }
        return ResponseEntity
                .badRequest()
                .body("Something went wrong");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable UUID id) {
        if (itemService.deleteItem(id)) {
            return ResponseEntity
                    .accepted()
                    .body("Deleted successfully");
        }
        return ResponseEntity
                .badRequest()
                .body("Something went wrong");
    }
}
