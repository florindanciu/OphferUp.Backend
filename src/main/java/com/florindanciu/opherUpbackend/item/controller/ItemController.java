package com.florindanciu.opherUpbackend.item.controller;

import com.florindanciu.opherUpbackend.auth.dto.UserDto;
import com.florindanciu.opherUpbackend.auth.model.AppUser;
import com.florindanciu.opherUpbackend.item.dto.ItemDto;
import com.florindanciu.opherUpbackend.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDto> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable UUID id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ItemDto> getItemsByUserId(@PathVariable UUID userId) {
        return itemService.getItemsByUserId(userId);
    }

    @GetMapping("/user/item/{itemId}")
    public UserDto getUserByItemId(@PathVariable UUID itemId) {
        return itemService.getUserByItemId(itemId);
    }

    @GetMapping("/category/{id}")
    public List<ItemDto> getItemsByCategoryId(@PathVariable UUID id) {
        return itemService.getItemsByCategoryId(id);
    }

    @GetMapping("/name/{itemName}")
    public List<ItemDto> getItemsByName(@PathVariable String itemName) {
        return itemService.getItemsByName(itemName);
    }

    @GetMapping("/location/{itemLocation}")
    public List<ItemDto> getItemsByLocation(@PathVariable String itemLocation) {
        return itemService.getItemsByLocation(itemLocation);
    }

    @GetMapping("/name/{itemName}/location/{itemLocation}")
    public List<ItemDto> getItemsByNameAndLocation(@PathVariable String itemName, @PathVariable String itemLocation) {
        return itemService.getItemsByNameAndLocation(itemName, itemLocation);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addItem(@RequestBody ItemDto itemDto, @PathVariable UUID userId) {
        if (itemService.addItem(itemDto, userId)) {
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
