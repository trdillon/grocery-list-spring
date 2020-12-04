package com.itsdits.grocerylist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<Item> items;
}
