package org.example.projectd.controller;


import lombok.RequiredArgsConstructor;
import org.example.projectd.dto.CustomerDTO;
import org.example.projectd.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {
    private final CustomerService customerService;


    @GetMapping("")
    public ResponseEntity<List<CustomerDTO>> getCustomer() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }
}
