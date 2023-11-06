package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.managers.VirtualDeviceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/virtual-devices")
@RequiredArgsConstructor
public class VirtualDeviceEndpoint {

    private final VirtualDeviceManager virtualDeviceManager;

}
