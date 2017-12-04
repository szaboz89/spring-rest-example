package com.szabodev.example.rest.api.v1.mapper;

import com.szabodev.example.rest.api.v1.model.CustomerDTO;
import com.szabodev.example.rest.domain.shop.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);
}
