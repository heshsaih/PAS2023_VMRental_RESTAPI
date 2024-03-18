package com.example.vmrentalrest;

import com.example.vmrentalrest.DBManagementTools;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
@Profile({"test"})
@RequiredArgsConstructor
public class TestInitData {
    private final DBManagementTools dbManagementTools;

    @EventListener(ApplicationReadyEvent.class)
    void init () {
        dbManagementTools.clearRecords();
    }
}
