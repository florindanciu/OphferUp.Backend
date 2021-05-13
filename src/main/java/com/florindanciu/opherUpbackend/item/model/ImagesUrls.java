package com.florindanciu.opherUpbackend.item.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagesUrls {

    @Id
    @GeneratedValue
    private UUID id;
    private String file1;
    private String file2;
    private String file3;

    public List<String> getAllFiles() {
        return List.of(file1, file2, file3);
    }
}
