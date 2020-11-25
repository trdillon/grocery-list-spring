package com.itsdits.grocerylist;

import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.repository.GroceryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GroceryRepositoryIntegrationTest.java - This class provides test methods for {@link GroceryRepository} to ensure
 * the CRUD operations perform successfully. It implements {@link TestEntityManager} for data persistence.
 * This class uses {@link DataJpaTest} to disable full auto-configuration and make the tests transactional.
 *
 * @author Tim Dillon
 * @version 1.1
 */
@DataJpaTest
public class GroceryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    GroceryRepository repository;

    @Test
    void should_find_nothing_if_there_is_no_data() {
        Iterable<Grocery> groceries = repository.findAll();

        assertThat(groceries).isEmpty();
    }

    @Test
    void should_add_a_grocery() {
        Grocery grocery = repository.save(new Grocery("test", 1, 100, "testing", false));

        assertThat(grocery).hasFieldOrPropertyWithValue("name", "test");
        assertThat(grocery).hasFieldOrPropertyWithValue("quantity", 1);
        assertThat(grocery).hasFieldOrPropertyWithValue("price", 100);
        assertThat(grocery).hasFieldOrPropertyWithValue("notes", "testing");
        assertThat(grocery).hasFieldOrPropertyWithValue("purchased", false);
    }

    @Test
    void should_find_all_groceries() {
        Grocery data1 = new Grocery("test1", 1, 100, "testing1", false);
        entityManager.persist(data1);
        Grocery data2 = new Grocery("test2", 2, 100, "testing2", false);
        entityManager.persist(data2);
        Grocery data3 = new Grocery("test3", 3, 100, "testing3", false);
        entityManager.persist(data3);

        Iterable<Grocery> groceries = repository.findAll();

        assertThat(groceries).hasSize(3).contains(data1, data2, data3);
    }

    @Test
    void should_find_grocery_by_id() {
        Grocery data1 = new Grocery("test1", 1, 100, "testing1", false);
        entityManager.persist(data1);
        Grocery data2 = new Grocery("test2", 2, 100, "testing2", false);
        entityManager.persist(data2);

        Grocery grocery = repository.findById(data2.getId()).get();

        assertThat(grocery).isEqualTo(data2);
    }

    @Test
    void should_find_purchased_groceries() {
        Grocery data1 = new Grocery("test1", 1, 100, "testing1", true);
        entityManager.persist(data1);
        Grocery data2 = new Grocery("test2", 2, 100, "testing2", false);
        entityManager.persist(data2);
        Grocery data3 = new Grocery("test3", 3, 100, "testing3", true);
        entityManager.persist(data3);

        Iterable<Grocery> groceries = repository.findByPurchased(true);

        assertThat(groceries).hasSize(2).contains(data1, data3);
    }

    @Test
    void should_find_grocery_by_name() {
        Grocery data1 = new Grocery("test1", 1, 100, "testing1", false);
        entityManager.persist(data1);
        Grocery data2 = new Grocery("test2", 2, 100, "testing2", false);
        entityManager.persist(data2);
        Grocery data3 = new Grocery("failmeplz", 3, 100, "testing3", false);
        entityManager.persist(data3);

        Iterable<Grocery> groceries = repository.findByNameContaining("est");

        assertThat(groceries).hasSize(2).contains(data1, data2);
    }

    @Test
    void should_update_grocery_by_id() {
        Grocery data1 = new Grocery("test1", 1, 100, "testing1", false);
        entityManager.persist(data1);
        Grocery dataToCheck = new Grocery("this data", 2, 200, "will be checked", true);

        Grocery tempData = repository.findById(data1.getId()).get();
        tempData.setName(dataToCheck.getName());
        tempData.setQuantity(dataToCheck.getQuantity());
        tempData.setPrice(dataToCheck.getPrice());
        tempData.setNotes(dataToCheck.getNotes());
        tempData.setPurchased(dataToCheck.isPurchased());
        repository.save(tempData);

        Grocery testData = repository.findById(data1.getId()).get();

        assertThat(testData.getId()).isEqualTo(data1.getId());
        assertThat(testData.getName()).isEqualTo(dataToCheck.getName());
        assertThat(testData.getQuantity()).isEqualTo(dataToCheck.getQuantity());
        assertThat(testData.getPrice()).isEqualTo(dataToCheck.getPrice());
        assertThat(testData.getNotes()).isEqualTo(dataToCheck.getNotes());
        assertThat(testData.isPurchased()).isEqualTo(dataToCheck.isPurchased());
    }

    @Test
    void should_delete_grocery_by_id() {
        Grocery data1 = new Grocery("test1", 1, 100, "testing1", false);
        entityManager.persist(data1);
        Grocery data2 = new Grocery("test2", 2, 100, "testing2", false);
        entityManager.persist(data2);
        Grocery data3 = new Grocery("test3", 3, 100, "testing3", false);
        entityManager.persist(data3);

        repository.deleteById(data2.getId());
        Iterable<Grocery> groceries = repository.findAll();

        assertThat(groceries).hasSize(2).contains(data1, data3);
    }

    @Test
    void should_delete_all_groceries() {
        entityManager.persist(new Grocery("test1", 1, 100, "testing1", false));
        entityManager.persist(new Grocery("test2", 2, 100, "testing2", false));

        repository.deleteAll();

        assertThat(repository.findAll().isEmpty());
    }
}
