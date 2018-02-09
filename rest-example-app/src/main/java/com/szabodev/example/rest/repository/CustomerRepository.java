package com.szabodev.example.rest.repository;

import com.szabodev.example.rest.domain.shop.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
