package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.updatedto.UpdateRentDTO;
import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.model.Rent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
public class RentEndpoint {
    private final RentManager rentManager;

    @GetMapping
    public List<Rent> getAllRents() {
        return rentManager.findAllRents();
    }
    @GetMapping("/{id}")
    public Rent getRentById(@PathVariable String id) {
        return rentManager.findRentById(id);
    }
    @GetMapping("/getbyvirtualdeviceid/{virtualDeviceId}")
    public List<Rent> getRentsByVirtualDeviceId(@PathVariable String virtualDeviceId) {
        return rentManager.findByVirtualDeviceId(virtualDeviceId);
    }
    @GetMapping("/getbyuserid/{userId}")
    public List<Rent> getRentsByUserId(@PathVariable String userId) {
        return rentManager.findByUserId(userId);
    }
    @PostMapping
    public Rent createRent(@RequestBody Rent rent) {
        return rentManager.createRent(rent);
    }

    @PutMapping("/{id}")
    public Rent updateRent(@PathVariable String id, @RequestBody UpdateRentDTO updateRentDTO) {
        return rentManager.updateRent(id,updateRentDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteRent(@PathVariable String id) {
        rentManager.deleteRent(id);
    }

}
