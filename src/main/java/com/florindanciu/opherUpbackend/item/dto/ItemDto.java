package com.florindanciu.opherUpbackend.item.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ItemDto {

    private UUID id;
    private String name;
    private String description;
    private String category;
    private Date postingDate;
    private Long price;
    private String location;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String image;
}
