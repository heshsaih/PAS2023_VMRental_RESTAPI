package com.example.vmrentalrest;

import com.example.vmrentalrest.managers.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final UserManager userManager;
    @Scheduled(fixedRate = 300000)
    public void updateClientActiveRents() {
        userManager.findAllUsers()
                .forEach(user -> userManager.removeFromActiveRents(user.getId()));
    }
}
