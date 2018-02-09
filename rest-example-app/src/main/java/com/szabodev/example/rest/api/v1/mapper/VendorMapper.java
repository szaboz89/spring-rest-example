package com.szabodev.example.rest.api.v1.mapper;

import com.szabodev.example.rest.api.v1.model.VendorDTO;
import com.szabodev.example.rest.domain.shop.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO toDTO(Vendor vendor);

    Vendor toEntity(VendorDTO vendorDTO);
}
