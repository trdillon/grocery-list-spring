package com.itsdits.grocerylist.service;

import com.itsdits.grocerylist.controller.GroceryController;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GroceryService.java - This class provides a data service for the API calls from
 * {@link GroceryController GroceryController}.
 * Utilizes an instance of {@link GroceryRepository} as an interface for JpaRepository CRUD methods.
 *
 * @author Tim Dillon
 * @version 1.0
 */
@Service
@Transactional
public class GroceryService {

    @Autowired
    private GroceryRepository repo;

    /**
     * This gets a List of all Grocery items in the database.
     *
     * @return List of Grocery objects
     */
    public List<Grocery> getAll() {
        return repo.findAll();
    }

    /**
     * This gets a single Grocery item by its id value.
     *
     * @param id long param representing the id of the desired Grocery item
     * @return Grocery object with matching id or {@code null} if no match is found
     */
    public Grocery getById(long id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * This gets a List of Grocery items that have not been purchased.
     *
     * @return List of Grocery objects where {@code purchased = false}
     */
    public List<Grocery> getNotPurchased() {
        return repo.findByPurchased(false);
    }

    /**
     * This gets a List of Grocery items that have already been purchased.
     *
     * @return List of Grocery objects where {@code purchased = true}
     */
    public List<Grocery> getPurchased() {
        return repo.findByPurchased(true);
    }

    /**
     * This gets a List of Grocery items that the user has marked as favorites.
     *
     * @return List of Grocery objects where {@code favorite = true}
     */
    public List<Grocery> getFavorites() {
        return repo.findByFavorite(true);
    }

    /**
     * This gets a List of Grocery items whose name contains a match to the search criteria.
     *
     * @param name String param representing the search criteria
     * @return List of Grocery objects that match the search criteria
     */
    public List<Grocery> getByName(String name) {
        return repo.findByNameContaining(name);
    }

    /**
     * This saves a Grocery item to the database for create and update methods.
     *
     * @param grocery Grocery object holding the data to be saved
     * @return New or updated Grocery object
     */
    public Grocery save(Grocery grocery) {
        return repo.save(grocery);
    }

    /**
     * This deletes a Grocery item by its id value.
     *
     * @param id long param representing the id of the desired Grocery item
     */
    public void delete(long id) {
        repo.deleteById(id);
    }

    /**
     * This deletes all Grocery items from the database.
     */
    public void purge() {
        repo.deleteAll();
    }
}
