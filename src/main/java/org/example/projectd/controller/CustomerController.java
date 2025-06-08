package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.CustomerDTO;
import org.example.projectd.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/project/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomer(
            @RequestHeader("X-Company-Id") String companyId) {
        return new ResponseEntity<>(
                customerService.getCustomers(Long.parseLong(companyId)),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO,@RequestHeader("X-Company-Id") String companyId) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO,Long.parseLong(companyId)), HttpStatus.CREATED);
    }
}
