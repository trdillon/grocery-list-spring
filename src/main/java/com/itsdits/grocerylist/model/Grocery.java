package com.itsdits.grocerylist.model;

import javax.persistence.*;

/**
 * Grocery.java - This class creates an Entity model for the Grocery objects used in this app.
 * The price field uses the int datatype for storing values as we are recording prices in Japanese yen.
 * Yen is represented by whole numbers only, so for this application it will work. If the currency to be
 * represented by price is changed to US dollars or Euros, for example, the datatype will need to be changed
 * to handle the decimal point and remainders such as cents. Using type double in the app and type decimal in the DB
 * can cause rounding issues when doing floating point math, so caution should be taken when changing datatype.
 *
 * @author Tim Dillon
 * @version 1.1
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

    @Column(name = "price")
    private int price;

    @Column(name = "notes")
    private String notes;

    @Column(name = "purchased")
    private boolean purchased;

    public Grocery() {
    }

    //TODO - constructors for different param sets

    public Grocery(String name, int quantity, int price, String notes, boolean purchased) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
