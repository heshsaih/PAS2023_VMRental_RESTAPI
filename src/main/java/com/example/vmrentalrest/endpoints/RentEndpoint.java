package com.example.vmrentalrest.endpoints;

import com.example.vmrentalrest.dto.updatedto.UpdateRentDTO;
import com.example.vmrentalrest.managers.RentManager;
import com.example.vmrentalrest.model.Rent;
import com.example.vmrentalrest.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.List;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
public class RentEndpoint {
    private final RentManager rentManager;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<Rent>> getAllRents() {
        return ResponseEntity.ok()
                .body(rentManager.findAllRents());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Rent> getRentById(@PathVariable String id) {
        return ResponseEntity.ok()
                .body(rentManager.findRentById(id));
    }
    @GetMapping("/getbyvirtualdeviceid/{virtualDeviceId}")
    public ResponseEntity<List<Rent>> getRentsByVirtualDeviceId(@PathVariable String virtualDeviceId) {
        return ResponseEntity.ok()
                .body(rentManager.findByVirtualDeviceId(virtualDeviceId));
    }
    @GetMapping("/getbyuserid/{userId}")
    public ResponseEntity<List<Rent>> getRentsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok()
                .body(rentManager.findByUserId(userId));
    }
    @PostMapping
    public ResponseEntity<Rent> createRent(@RequestBody Rent rent) {
        return ResponseEntity.ok()
                .body(rentManager.createRent(rent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rent> updateRent(@PathVariable String id, @RequestBody UpdateRentDTO updateRentDTO) {
        return ResponseEntity.ok()
                .body(rentManager.updateRent(id,updateRentDTO));
    }
    @DeleteMapping("/{id}")
    public void deleteRent(@PathVariable String id) {
        rentManager.deleteRent(id);
    }

    @GetMapping("/self/rents")
    public ResponseEntity<List<Rent>> getMyRents(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok()
                .body(rentManager.findByUserId(jwtService.extractUserId(token)));
    }
    @PostMapping("/self/rents")
    public ResponseEntity<Rent> createMyRent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Rent rent) {
        rent.setUserId(jwtService.extractUserId(token));
        return ResponseEntity.ok()
                .body(rentManager.createRent(rent));
    }
    @GetMapping("/self/rents/{id}")
    public ResponseEntity<Rent> getMyRentById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id) {
        return ResponseEntity.ok()
                .body(rentManager.findRentById(id,jwtService.extractUserId(token)));
    }
    @PutMapping("/self/rents/{id}")
    public ResponseEntity<Rent> updateMyRent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id, @RequestBody UpdateRentDTO updateRentDTO) {
        return ResponseEntity.ok()
                .body(rentManager.updateRent(id,updateRentDTO, jwtService.extractUserId(token)));
    }
    @DeleteMapping("/self/rents/{id}")
    public void deleteMyRent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id) {
        rentManager.deleteRent(id,jwtService.extractUserId(token));
    }
}
