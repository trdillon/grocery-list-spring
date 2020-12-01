package com.itsdits.grocerylist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String product;
    private int quantity;
    private int price;
    private String notes;
    private boolean purchased;
    private boolean favorite;

    @ManyToMany
    private Set<User> buyers;
}
