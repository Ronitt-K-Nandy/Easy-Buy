package com.substring.easybuy.products.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;


    private String title;
    @Column(columnDefinition = "TEXT")
    private String shortDesc;
    @Lob
    private String longDesc;
    private Double price;
    private Integer discount;
    private Boolean live = false;

/*@ElementCollection - in JPA/Hibernate maps a collection of basic types (e.g., String, Integer)
 or classes (e.g., Address or PhoneNumber object) without needing a
 full-fledged independent entity.

 No Independent Identity: The collection items do not have their own @Id primary key
 or standalone repository. They are strictly owned by the parent entity
 and share its lifecycle (if the parent is deleted, the collection items go with it)*/
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> productImages = new ArrayList<>();

    //"products" -> column in Category
    @ManyToMany(mappedBy = "products")
    private  List<Category> categories = new ArrayList<>();

    //"product" -> column in Review
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

/*
• The Owning Side: The entity that does not use mappedBy (@ManyToOne,@JoinColumn,@JoinTable).
    It is responsible for updating the database foreign key.
• The Inverse Side: The entity marked with mappedBy (@OneToMany,@OneToOne,@ManyToMany)
    tells Hibernate: “Do not create a separate foreign key or join table for this side; look at the product field in the other entity to see how this relationship is mapped.”
 */
}
