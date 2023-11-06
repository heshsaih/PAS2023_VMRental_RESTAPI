package com.example.vmrentalrest.repositories;



import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VirtualDeviceRepository extends MongoRepository<VirtualDevice,String> {
}
