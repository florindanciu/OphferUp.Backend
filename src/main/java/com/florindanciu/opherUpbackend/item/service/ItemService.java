package com.florindanciu.opherUpbackend.item.service;

import com.florindanciu.opherUpbackend.auth.dto.UserConverter;
import com.florindanciu.opherUpbackend.auth.dto.UserDto;
import com.florindanciu.opherUpbackend.auth.model.AppUser;
import com.florindanciu.opherUpbackend.auth.repository.AppUserRepository;
import com.florindanciu.opherUpbackend.aws.BucketName;
import com.florindanciu.opherUpbackend.aws.ImageStore;
import com.florindanciu.opherUpbackend.item.dto.ItemConverter;
import com.florindanciu.opherUpbackend.item.dto.ItemDto;
import com.florindanciu.opherUpbackend.item.exception.ItemNotFoundException;
import com.florindanciu.opherUpbackend.item.model.Category;
import com.florindanciu.opherUpbackend.item.model.EnumCategory;
import com.florindanciu.opherUpbackend.item.model.Item;
import com.florindanciu.opherUpbackend.item.model.ImagesUrls;
import com.florindanciu.opherUpbackend.item.repository.CategoryRepository;
import com.florindanciu.opherUpbackend.item.repository.ImagesUrlsRepository;
import com.florindanciu.opherUpbackend.item.repository.ItemRepository;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final AppUserRepository appUserRepository;
    private final ItemConverter converter;
    private final UserConverter userConverter;
    private final ImageStore imageStore;
    private final ImagesUrlsRepository imagesUrlsRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository,
                       CategoryRepository categoryRepository,
                       AppUserRepository appUserRepository,
                       ItemConverter converter,
                       UserConverter userConverter,
                       ImageStore imageStore,
                       ImagesUrlsRepository imagesUrlsRepository)
    {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.appUserRepository = appUserRepository;
        this.converter = converter;
        this.userConverter = userConverter;
        this.imageStore = imageStore;
        this.imagesUrlsRepository = imagesUrlsRepository;
    }

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

    public UserDto getUserByItemId(UUID itemId) {
        Item item = itemRepository.getOne(itemId);
        AppUser user = appUserRepository.getAppUserByItems(item);
        return userConverter.modelToDto(user);
    }

    public List<ItemDto> getItemsByUserId(UUID userId) {
        AppUser user = appUserRepository.getOne(userId);
        return converter.modelToDto(itemRepository.getAllByUser(user));
    }

    public ItemDto getItemById(UUID id) {
        Item item = itemRepository.findById(id).
                orElseThrow(() -> new ItemNotFoundException(id));
        String category = item.getCategories().iterator().next().getEnumCategory().name();
        ItemDto itemDto = converter.modelToDto(item);
        itemDto.setCategory(category);
        return itemDto;
    }

    public List<ItemDto> getItemsByName(String name) {
        List<Item> items = itemRepository.getItemsByItemName(name);
        return converter.modelToDto(items);
    }

    public List<ItemDto> getItemsByLocation(String location) {
        List<Item> items = itemRepository.getItemsByLocation(location);
        return converter.modelToDto(items);
    }

    public List<ItemDto> getItemsByNameAndLocation(String name, String location) {
        List<Item> items = itemRepository.getItemsByItemNameAndLocation(name, location);
        return converter.modelToDto(items);
    }

    public Map<String, UUID> addItem(ItemDto itemDto, UUID userId) {
        Item item = converter.dtoToModel(itemDto);
        Set<Category> categories = new HashSet<>();
        String strCategory = itemDto.getCategory();

        AppUser appUser = appUserRepository.getOne(userId);

        switch (strCategory) {
            case "Vehicles" -> {
                Category auto = categoryRepository.findByEnumCategory(EnumCategory.Vehicles);
                categories.add(auto);
            }
            case "Estates" -> {
                Category realEstate = categoryRepository.findByEnumCategory(EnumCategory.Estates);
                categories.add(realEstate);
            }
            case "Jobs" -> {
                Category jobs = categoryRepository.findByEnumCategory(EnumCategory.Jobs);
                categories.add(jobs);
            }
            case "Electronics" -> {
                Category electronics = categoryRepository.findByEnumCategory(EnumCategory.Electronics);
                categories.add(electronics);
            }
            case "Fashion" -> {
                Category fashion = categoryRepository.findByEnumCategory(EnumCategory.Fashion);
                categories.add(fashion);
            }
            case "Home" -> {
                Category home = categoryRepository.findByEnumCategory(EnumCategory.Home);
                categories.add(home);
            }
            case "Pets" -> {
                Category pets = categoryRepository.findByEnumCategory(EnumCategory.Pets);
                categories.add(pets);
            }
            case "Services" -> {
                Category services = categoryRepository.findByEnumCategory(EnumCategory.Services);
                categories.add(services);
            }
        }
        item.setCategories(categories);
        item.setUser(appUser);
        itemRepository.save(item);
        Map<String, UUID> response = new HashMap<>();
        response.put("new_itemId", item.getId());
        return response;
    }

    public void updateItem(UUID itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId).
                orElseThrow(() -> new ItemNotFoundException(itemId));

        Category category = categoryRepository.findByEnumCategory(EnumCategory.valueOf(itemDto.getCategory()));
        Set<Category> categories = new HashSet<>();
        categories.add(category);

        item.setItemName(itemDto.getItemName());
        item.setCategories(categories);
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setLocation(itemDto.getLocation());
        item.setContactPerson(itemDto.getContactPerson());
        item.setContactEmail(itemDto.getContactEmail());
        item.setPhoneNumber(itemDto.getPhoneNumber());
        itemRepository.save(item);
    }

    public Boolean deleteItem(UUID id) {
        deleteImages(id);
        itemRepository.deleteById(id);
        if (itemRepository.existsById(id)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void uploadImage(UUID itemId, List<MultipartFile> files) {
        Item item = itemRepository.getOne(itemId);

        UUID oldImgUrlsId = null;
        // delete images from s3 before updating with new images
        if (item.getImagesUrls() != null) {
            oldImgUrlsId = item.getImagesUrls().getId();
            deleteImages(itemId);
        }

        ImagesUrls imagesUrls = new ImagesUrls();
        for (MultipartFile file: files) {
            // check if image is not empty
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot upload empty file");
            }
            // if file is an image
            if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType()).contains(file.getContentType())) {
                throw new IllegalStateException("File must be an image");
            }
            // the item exists in DB
            if (!itemRepository.existsById(itemId)) {
                throw new IllegalStateException("Item with id " + itemId + " does not exists");
            }
            // grab metadata from file if any
            Long fileLength = file.getSize();

            // store the image in s3 and update db with s3 image url
            String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), itemId);
            String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
            try {
                imageStore.save(path, fileName, fileLength, file.getInputStream());
                switch (file.getName()) {
                    case "file1" -> imagesUrls.setFile1(fileName);
                    case "file2" -> imagesUrls.setFile2(fileName);
                    case "file3" -> imagesUrls.setFile3(fileName);
                }
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        item.setImagesUrls(imagesUrls);
        itemRepository.save(item);

        // delete old images urls from db
        if (oldImgUrlsId != null) {
            imagesUrlsRepository.deleteById(oldImgUrlsId);
        }
    }

    public void deleteImages(UUID itemId) {
        Item item = itemRepository.getOne(itemId);
        List<String> allImageKeys = item.getImagesUrls().getAllFiles();
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), itemId);
        allImageKeys.forEach((key) -> imageStore.deleteImage(path, key));
    }

    public byte[] downloadImages(UUID itemId, String fileName) {
        // path = bucketName + bucketFolder + key
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), itemId);
        ImagesUrls imagesUrls = itemRepository.getOne(itemId).getImagesUrls();
        switch (fileName) {
            case "file1":
                return imageStore.download(path, imagesUrls.getFile1());
            case "file2":
                return imageStore.download(path, imagesUrls.getFile2());
            case "file3":
                return imageStore.download(path, imagesUrls.getFile3());
        };
        return null;
    }
}
