package com.florindanciu.opherUpbackend.item.controller;

import com.florindanciu.opherUpbackend.auth.dto.UserDto;
import com.florindanciu.opherUpbackend.item.dto.ItemDto;
import com.florindanciu.opherUpbackend.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@CrossOrigin(origins = "*")
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

    @PutMapping("/item/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable UUID itemId, @RequestBody ItemDto itemDto) {
        itemService.updateItem(itemId, itemDto);
        return ResponseEntity.accepted().body("Offer successfully updated.");
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Map<String, UUID> addItem(@RequestBody ItemDto itemDto, @PathVariable UUID userId) {
        return itemService.addItem(itemDto, userId);
    }

    @PostMapping(
            path = "/image/{itemId}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> uploadImage(
            @PathVariable("itemId") UUID itemId,
            @RequestPart(required = false) MultipartFile file1,
            @RequestPart(required = false) MultipartFile file2,
            @RequestPart(required = false) MultipartFile file3
    ) {
        List<MultipartFile> images = new ArrayList<>();
        if (file1 != null) {
            images.add(file1);
        }
        if (file2 != null) {
            images.add(file2);
        }
        if (file3 != null) {
            images.add(file3);
        }
        itemService.uploadImage(itemId, images);
        return ResponseEntity
                .accepted()
                .body("Offer successfully");
    }

    @GetMapping("/image/{itemId}/{fileName}/download")
    public byte[] downloadImage(@PathVariable("itemId") UUID itemId, @PathVariable("fileName") String fileName) {
        return itemService.downloadImages(itemId, fileName);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
