package com.florindanciu.opherUpbackend.item.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.florindanciu.opherUpbackend.auth.model.AppUser;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue
    private UUID id;

    private String itemName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @CreationTimestamp
    private Date postingDate;
    @Column(columnDefinition = "text")
    private String description;
    private String price;
    private String location;
    private String contactPerson;
    private String contactEmail;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private ImagesUrls imagesUrls;
    @ManyToOne
    private AppUser user;
}
