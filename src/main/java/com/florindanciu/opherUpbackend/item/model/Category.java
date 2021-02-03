package com.florindanciu.opherUpbackend.item.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EnumCategory enumCategory;

    public Category(EnumCategory enumCategory) {
        this.enumCategory = enumCategory;
    }
}
