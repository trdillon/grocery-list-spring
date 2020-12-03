package com.itsdits.grocerylist.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "grocery")
public class Grocery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    private String group;
    @Column(name = "sub_group")
    private String subGroup;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;

    @OneToMany(mappedBy = "grocery")
    private Set<Item> items;
}
