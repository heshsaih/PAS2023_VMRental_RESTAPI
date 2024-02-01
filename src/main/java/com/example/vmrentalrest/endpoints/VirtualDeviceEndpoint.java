package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.updatedto.UpdateVirtualDeviceDTO;
import com.example.vmrentalrest.managers.VirtualDeviceManager;
import com.example.vmrentalrest.model.enums.DatabaseType;
import com.example.vmrentalrest.model.enums.OperatingSystemType;
import com.example.vmrentalrest.model.virtualdevices.VirtualDatabaseServer;
import com.example.vmrentalrest.model.virtualdevices.VirtualDevice;
import com.example.vmrentalrest.model.virtualdevices.VirtualMachine;
import com.example.vmrentalrest.model.virtualdevices.VirtualPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/virtual-devices")
@RequiredArgsConstructor
public class VirtualDeviceEndpoint {

    private final VirtualDeviceManager virtualDeviceManager;
    @GetMapping
    public ResponseEntity<List<VirtualDevice>> getAllVirtualDevices() {
        return ResponseEntity.ok()
                .body(virtualDeviceManager.findAllVirtualDevices());
    }
    @GetMapping("/{id}")
    public ResponseEntity<VirtualDevice> getVirtualDeviceById(@PathVariable String id) {
        return ResponseEntity.ok()
                .body(virtualDeviceManager.findVirtualDeviceById(id));
    }
    @PostMapping("/createvirtual-machine")
    public ResponseEntity<VirtualMachine> createVirtualMachine(@RequestBody VirtualMachine virtualMachine) {
        return ResponseEntity.ok()
                .body(virtualDeviceManager.createVirtualMachine(virtualMachine));
    }

    @PostMapping("/createvirtual-database-server")
    public ResponseEntity<VirtualDatabaseServer> createVirtualDatabaseServer(@RequestBody VirtualDatabaseServer virtualDatabaseServer) {
        return ResponseEntity.ok()
                .body(virtualDeviceManager.createVirtualDatabaseServer(virtualDatabaseServer));
    }
    @PostMapping("/createvirtual-phone")
    public ResponseEntity<VirtualPhone> createVirtualPhone(@RequestBody VirtualPhone virtualPhone) {
        return ResponseEntity.ok()
                .body(virtualDeviceManager.createVirtualPhone(virtualPhone));
    }
    @PutMapping("/{id}")
    public ResponseEntity<VirtualDevice> updateVirtualDevice(@PathVariable String id, @RequestBody UpdateVirtualDeviceDTO updateVirtualDeviceDTO) {
        return ResponseEntity.ok()
                .body(virtualDeviceManager.updateVirtualDevice(id,updateVirtualDeviceDTO));
    }
    @PatchMapping("/{id}/updatedatabasetype")
    public void updateDatabaseType(@PathVariable String id, @RequestParam DatabaseType databaseType) {
        virtualDeviceManager.updateDatabaseType(id,databaseType);
    }
    @PatchMapping("/{id}/updateoperatingsystemtype")
    public void updateOperatingSystemType(@PathVariable String id, @RequestParam OperatingSystemType operatingSystemType) {
        virtualDeviceManager.updateOperatingSystemType(id,operatingSystemType);
    }
    @PatchMapping("/{id}/updatephonenumber")
    public void updatePhoneNumber(@PathVariable String id, @RequestParam int phoneNumber) {
        virtualDeviceManager.updatePhoneNumber(id,phoneNumber);
    }
    @DeleteMapping("/{id}")
    public void deleteVirtualDevice(@PathVariable String id) {
        virtualDeviceManager.deleteVirtualDevice(id);
    }
}