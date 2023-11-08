package com.example.vmrentalrest;

import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.managers.UserManager;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.model.enums.*;
import com.example.vmrentalrest.model.users.Address;
import com.example.vmrentalrest.model.users.Administrator;
import com.example.vmrentalrest.model.users.Client;
import com.example.vmrentalrest.model.users.ResourceManager;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

import java.time.LocalDateTime;

@Configuration
public class Config {
    @Bean
    MongoTransactionManager txManager(MongoDatabaseFactory mongoDbFactory) {
        return new MongoTransactionManager(mongoDbFactory);
    }


}
