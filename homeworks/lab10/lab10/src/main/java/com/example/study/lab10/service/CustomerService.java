package com.example.study.lab10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.study.lab10.dto.RegisterRequest;
import com.example.study.lab10.model.entity.Customer;
import com.example.study.lab10.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(RegisterRequest request, String externalId) {
        Customer customer = new Customer(null,
                externalId,
                request.getUsername(),
                request.getAddress(),
                List.of());
        return customerRepository.save(customer);
    }

    public Customer findCustomerByExternalId(String externalId) {
        return customerRepository.findByExternalId(externalId);
    }
}
