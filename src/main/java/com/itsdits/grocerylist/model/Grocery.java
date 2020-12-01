package com.itsdits.grocerylist.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "grocery")
public class Grocery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private int price;
    private String notes;
    private boolean purchased;
    private boolean favorite;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;
}
