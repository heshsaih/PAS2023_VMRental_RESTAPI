package com.example.vmrentalrest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;


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
