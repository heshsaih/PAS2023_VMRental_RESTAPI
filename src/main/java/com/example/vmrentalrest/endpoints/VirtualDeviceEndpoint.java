package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.enums.VirtualDeviceType;
import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/virtual-devices")
@RequiredArgsConstructor
public class VirtualDeviceEndpoint {

    private final VirtualDeviceManager virtualDeviceManager;
    @GetMapping
    public List<VirtualDevice> getAllVirtualDevices() {
        return virtualDeviceManager.findAllVirtualDevices();
    }
    @GetMapping("/{id}")
    public VirtualDevice getVirtualDeviceById(@PathVariable String id) {
        return virtualDeviceManager.findVirtualDeviceById(id);
    }
    @PostMapping
    public VirtualDevice createVirtualDevice(@RequestBody VirtualDevice virtualDevice, @RequestParam VirtualDeviceType virtualDeviceType) {
        return virtualDeviceManager.createVirtualDevice(virtualDevice,virtualDeviceType);
    }
    @PutMapping("/{id}")
    public void updateVirtualDevice(@PathVariable String id, @RequestBody VirtualDevice virtualDevice) {
        virtualDeviceManager.updateVirtualDevice(id,virtualDevice);
    }
    @DeleteMapping("/{id}")
    public void deleteVirtualDevice(@PathVariable String id) {
        virtualDeviceManager.deleteVirtualDevice(id);
    }


}
