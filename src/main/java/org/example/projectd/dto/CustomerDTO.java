package org.example.projectd.dto;


import lombok.Builder;
import org.example.projectd.entity.Customer;


@Builder
public record CustomerDTO(
        Integer id,
        String name,
        String phone,
        String address
) {
    public static CustomerDTO fromEntity(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();
    }
}
