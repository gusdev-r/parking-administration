package com.parking.administration.demo.mapper;

import com.parking.administration.demo.response.PSGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import com.parking.administration.demo.domain.ParkingSpace;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PSMapper {
    ParkingSpace INSTANCE = Mappers.getMapper(ParkingSpace.class);

    Page<PSGetResponse> toListPSGetResponse(Page<ParkingSpace> parkingSpaceList);

    PSGetResponse ObjectToPSGetResponse(Object psOptional);
}
