package com.parking.administration.demo.mapper;

import com.parking.administration.demo.domain.ParkingSpace;
import com.parking.administration.dto.request.ParkingSpaceRequestPost;
import com.parking.administration.dto.response.ParkingSpaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PSMapper {
    ParkingSpace INSTANCE = Mappers.getMapper(ParkingSpace.class);

    ParkingSpaceResponse toOptionalParkingSpace(ParkingSpace parkingSpaceOptional);

    List<ParkingSpaceResponse> toListParkingSpace(List<ParkingSpace> parkingSpaceList);

    ParkingSpace toParkingSpaceRequest(ParkingSpaceRequestPost parkingSpaceRequestPost);

    ParkingSpaceResponse toParkingSpacePostRequest(ParkingSpaceRequestPost parkingSpaceRequestPost);

}
