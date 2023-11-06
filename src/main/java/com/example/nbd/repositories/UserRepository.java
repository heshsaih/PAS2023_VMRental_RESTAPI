package com.example.nbd.repositories;

import com.example.nbd.model.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    ArrayList<User> findAllByUsernameContainsIgnoreCase(String username);

}
