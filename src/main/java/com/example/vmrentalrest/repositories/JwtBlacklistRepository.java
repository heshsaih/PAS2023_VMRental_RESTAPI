package com.example.vmrentalrest.repositories;

import com.example.vmrentalrest.model.JwtBlacklist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JwtBlacklistRepository extends MongoRepository <JwtBlacklist, String> {
    boolean existsByToken(String token);
}
