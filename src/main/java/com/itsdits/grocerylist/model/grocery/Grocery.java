package com.itsdits.grocerylist.model.grocery;

import javax.persistence.*;

/**
 * Grocery.java - This class creates an Entity model for the Grocery objects used in this app.
 *
 * @author Tim Dillon
 * @version 1.0
 */
@Entity
@Table(name = "grocery")
public class Grocery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "notes")
    private String notes;

    @Column(name = "purchased")
    private boolean purchased;

    public Grocery() {
    }

    //TODO - constructors for different param sets

    public Grocery(String name, int quantity, String notes, boolean purchased) {
        this.name = name;
        this.quantity = quantity;
        this.notes = notes;
        this.purchased = purchased;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    @Override
    public String toString() {
        return "Grocery [id=" + id + ", name=" + name + "note=" + notes + "purchased=" + purchased + "]";
    }
}
