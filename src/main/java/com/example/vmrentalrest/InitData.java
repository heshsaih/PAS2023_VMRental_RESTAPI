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
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;

@Configuration
@Profile("default")
@RequiredArgsConstructor
public class InitData {
    private final DBManagementTools dbManagementTools;
    @EventListener(ApplicationReadyEvent.class)
    void init () {
        dbManagementTools.clearRecords();
        dbManagementTools.createData();
    }
}
