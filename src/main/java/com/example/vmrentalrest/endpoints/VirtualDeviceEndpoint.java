package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.updatedto.UpdateVirtualDeviceDTO;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
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
    @PostMapping("/virtual-machine")
    public VirtualMachine createVirtualMachine(@RequestBody VirtualMachine virtualMachine) {
        return virtualDeviceManager.createVirtualMachine(virtualMachine);
    }

    @PostMapping("/virtual-database-server")
    public VirtualDatabaseServer createVirtualDatabaseServer(@RequestBody VirtualDatabaseServer virtualDatabaseServer) {
        return virtualDeviceManager.createVirtualDatabaseServer(virtualDatabaseServer);
    }
    @PostMapping("/virtual-phone")
    public VirtualPhone createVirtualPhone(@RequestBody VirtualPhone virtualPhone) {
        return virtualDeviceManager.createVirtualPhone(virtualPhone);
    }
    @PutMapping("/{id}")
    public VirtualDevice updateVirtualDevice(@PathVariable String id, @RequestBody UpdateVirtualDeviceDTO updateVirtualDeviceDTO) {
        return virtualDeviceManager.updateVirtualDevice(id,updateVirtualDeviceDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteVirtualDevice(@PathVariable String id) {
        virtualDeviceManager.deleteVirtualDevice(id);
    }


}
