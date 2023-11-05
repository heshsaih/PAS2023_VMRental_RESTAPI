package com.example.nbd.repositories;

import com.example.nbd.model.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByFirstNameAndLastNameAndAddress_CityAndAddress_StreetAndAddress_HouseNumber(String firstName,String lastName,String city, String street, String houseNumber);

}
