package com.itsdits.grocerylist.repository;

import com.itsdits.grocerylist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
