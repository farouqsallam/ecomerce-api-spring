package org.roka.ecomerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "Text")
    @Lob
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
    private List<Product> products;
}
