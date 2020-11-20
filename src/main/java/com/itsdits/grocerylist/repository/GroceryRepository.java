package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.service.GroceryService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * GroceryRepository.java - This class extends {@link JpaRepository} to provide CRUD operation methods
 * for {@link GroceryService GroceryService}.
 *
 * @author Tim Dillon
 * @version 1.0
 */
public interface GroceryRepository extends JpaRepository<Grocery, Long> {

    /**
     * This gets a List of Grocery items based on the {@code purchased} value.
     *
     * @param purchased Boolean param determines the purchase status of the Grocery item
     * @return List of Grocery objects that match the {@code purchased} param
     */
    List<Grocery> findByPurchased(boolean purchased);

    /**
     * This gets a List of Grocery items whose name contains a match to the search criteria.
     *
     * @param name String param representing the search criteria
     * @return List of Grocery objects that match the search criteria
     */
    List<Grocery> findByNameContaining(String name);

}
