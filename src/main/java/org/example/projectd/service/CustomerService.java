package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.CustomerDTO;
import org.example.projectd.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerDTO> getCustomers(){
        return customerRepository.findAll().stream()
                .map(CustomerDTO::fromEntity)
                .toList();
    }
}
