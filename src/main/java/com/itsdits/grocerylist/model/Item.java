package com.itsdits.grocerylist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "grocery_id")
    Grocery grocery;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    private int quantity;
    private int price;
    private String notes;
}
