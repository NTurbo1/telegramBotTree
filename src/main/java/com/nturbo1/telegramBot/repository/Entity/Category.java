package com.nturbo1.telegramBot.repository.Entity;

import com.nturbo1.telegramBot.repository.annotations.ValidRootCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@ValidRootCategory
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "is_root")
    private Boolean isRoot = false; // Default value is false to avoid null pointer exceptions

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Category> children = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;
        return name.equals(category.name);
    }
}
