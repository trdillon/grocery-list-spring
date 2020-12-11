package com.itsdits.grocerylist.service;

import com.itsdits.grocerylist.model.dto.GrocerySearchDto;
import com.itsdits.grocerylist.model.Grocery;
import com.itsdits.grocerylist.repository.GroceryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GroceryService {

    private final GroceryRepository groceryRepository;

    public List<Grocery> getAll(GrocerySearchDto grocerySearchDto) {
        Grocery groceryProbe = new Grocery();
        groceryProbe.setName(grocerySearchDto.getName());
        groceryProbe.setGroup(grocerySearchDto.getGroup());
        groceryProbe.setSubGroup(grocerySearchDto.getSubGroup());

        ExampleMatcher groceryMatcher = ExampleMatcher.matchingAll()
                .withIgnoreCase("name", "group", "subGroup")
                .withIgnorePaths("id")
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        return groceryRepository.findAll(Example.of(groceryProbe, groceryMatcher));
    }

    public Page<Grocery> getByName(String name, Pageable pageable) {
        return groceryRepository.findByNameContaining(name, pageable);
    }

    public Grocery getById(long id) {
        return groceryRepository.findById(id).orElse(null);
    }
}
