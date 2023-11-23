package com.example.vmrentalrest.dto.updatedto;

import com.example.vmrentalrest.model.users.Address;

public record UpdateUserDTO(String firstName,
                            String lastName,
                            String password,
                            String email,
                            Address address) {}
