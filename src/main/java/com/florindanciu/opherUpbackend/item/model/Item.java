package com.florindanciu.opherUpbackend.item.model;

import com.florindanciu.opherUpbackend.auth.model.AppUser;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @CreationTimestamp
    private Date postingDate;
    private String description;
    private String price;
    private String location;
    private String contactPerson;
    private String email;
    private String phoneNumber;
    private String image;
    @ManyToOne
    private AppUser user;
}
