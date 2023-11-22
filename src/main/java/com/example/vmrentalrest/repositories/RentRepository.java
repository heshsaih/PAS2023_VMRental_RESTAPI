package com.example.vmrentalrest.repositories;

import com.example.vmrentalrest.model.Rent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;


public interface RentRepository extends MongoRepository<Rent,String> {
    ArrayList<Rent> findAllByVirtualDeviceId(String virtualDeviceId);
    ArrayList<Rent> findRentsByUserIdAndEndLocalDateTimeIsAfter(String userId, LocalDateTime localDateTime);
    ArrayList<Rent> findAllByUserId(String userId);

   // boolean existsRentByVirtualDeviceIdAndRentIdNotAndStartLocalDateTimeIsBeforeAndEndLocalDateTimeIsAfterOrRentIdNotAndStartLocalDateTimeIsBeforeAndEndLocalDateTimeIsAfterOrRentIdNotAndStartLocalDateTimeIsAfterAndEndLocalDateTimeIsBefore(String virtualDeviceId, String rentId, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime,String rentId2, LocalDateTime start, LocalDateTime end,String rentId3, LocalDateTime start1, LocalDateTime end1);
    boolean existsRentByVirtualDeviceIdAndRentIdNotAndStartLocalDateTimeIsBeforeAndEndLocalDateTimeIsAfter(String virtualDeviceId, String rentId, LocalDateTime startLocalDateTime, LocalDateTime start);
    boolean existsRentByVirtualDeviceIdAndRentIdNotAndStartLocalDateTimeIsAfterAndEndLocalDateTimeIsBefore(String virtualDeviceId, String rentId, LocalDateTime startLocalDateTime, LocalDateTime endLocalDateTime);

}
