package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.VirtualDeviceDTO;
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
    public List<VirtualDeviceDTO> getAllVirtualDevices() {
        return virtualDeviceManager.findAllVirtualDevices()
                .stream()
                .map(VirtualDeviceDTO::new)
                .toList();
    }
    @GetMapping("/{id}")
    public VirtualDeviceDTO getVirtualDeviceById(@PathVariable String id) {
        return new VirtualDeviceDTO(virtualDeviceManager.findVirtualDeviceById(id));
    }
    @PostMapping
    public VirtualDeviceDTO createVirtualDevice(@RequestBody VirtualDeviceDTO virtualDeviceDTO) {
        return new VirtualDeviceDTO(virtualDeviceManager.createVirtualDevice(virtualDeviceDTO.convertToVirtualDevice(),virtualDeviceDTO.getVirtualDeviceType()));
    }
    @PutMapping("/{id}")
    public VirtualDevice updateVirtualDevice(@PathVariable String id,@RequestBody VirtualDeviceDTO virtualDeviceDTO) {
        return virtualDeviceManager.updateVirtualDevice(id,virtualDeviceDTO.convertToVirtualDevice());
    }
    @DeleteMapping("/{id}")
    public void deleteVirtualDevice(@PathVariable String id) {
        virtualDeviceManager.deleteVirtualDevice(id);
    }


}
