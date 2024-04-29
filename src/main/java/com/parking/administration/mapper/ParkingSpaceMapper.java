package com.parking.administration.mapper;

import com.parking.administration.domain.ParkingSpace;
import com.parking.administration.dto.request.ParkingSpaceRequestPost;
import com.parking.administration.dto.response.ParkingSpaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParkingSpaceMapper {
    ParkingSpaceResponse toOptionalParkingSpace(ParkingSpace parkingSpaceOptional);

    List<ParkingSpaceResponse> toListParkingSpace(List<ParkingSpace> parkingSpaceList);

    ParkingSpace toParkingSpaceRequest(ParkingSpaceRequestPost parkingSpaceRequestPost);

    ParkingSpaceResponse toParkingSpacePostRequest(ParkingSpaceRequestPost parkingSpaceRequestPost);
}
