package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.updatedto.UpdateRentDTO;
import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
public class RentEndpoint {
    private final RentManager rentManager;
    private final JwtService jwtService;

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

    @GetMapping("/self/rents")
    public List<Rent> getMyRents(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return rentManager.findByUserId(jwtService.extractUserId(token));
    }
    @PostMapping("/self/rents")
    public Rent createMyRent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Rent rent) {
        rent.setUserId(jwtService.extractUserId(token));
        return rentManager.createRent(rent);
    }
    @GetMapping("/self/rents/{id}")
    public Rent getMyRentById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id) {
        return rentManager.findRentById(id,jwtService.extractUserId(token));
    }
    @PutMapping("/self/rents/{id}")
    public Rent updateMyRent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id, @RequestBody UpdateRentDTO updateRentDTO) {
        return rentManager.updateRent(id,updateRentDTO, jwtService.extractUserId(token));
    }
    @DeleteMapping("/self/rents/{id}")
    public void deleteMyRent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id) {
        rentManager.deleteRent(id,jwtService.extractUserId(token));
    }
}
