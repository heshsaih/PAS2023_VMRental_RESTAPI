package com.example.vmrentalrest.repositories;

import com.example.vmrentalrest.model.Rent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;


public interface RentRepository extends MongoRepository<Rent,String> {
    ArrayList<Rent> findAllByVirtualDeviceId(String virtualDeviceId);
}
