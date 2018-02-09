package com.szabodev.example.rest.repository;


import com.szabodev.example.rest.domain.shop.Vendor;
import org.springframework.data.repository.CrudRepository;

public interface VendorRepository extends CrudRepository<Vendor, Long> {
}
