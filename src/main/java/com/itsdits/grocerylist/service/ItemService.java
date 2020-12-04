package com.itsdits.grocerylist.service;

import com.itsdits.grocerylist.model.Item;
import com.itsdits.grocerylist.repository.ItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getUserGroceryList(String user) {
        return itemRepository.findAllByUserId(user);
    }

    public List<Item> getByProductName(String name) {
        return itemRepository.findByProductContaining(name);
    }

    public List<Item> getByNeeded() {
        return itemRepository.findByPurchased(false);
    }

    public List<Item> getByPurchased() {
        return itemRepository.findByPurchased(true);
    }

    public List<Item> getByFavorite() {
        return itemRepository.findByFavorite(true);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
