package org.example.projectd.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.projectd.dto.CustomerDTO;
import org.example.projectd.entity.Customer;
import org.example.projectd.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerDTO> getCustomers(Long companyId) {
        return customerRepository.findAllByCompanyId(companyId.intValue()).stream()
                .map(CustomerDTO::fromEntity)
                .toList();
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO,Long companyId) {
        Customer customer = customerDTO.toEntity();
        customer.setCompanyId(companyId.intValue());
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerDTO.fromEntity(savedCustomer);
    }
}
