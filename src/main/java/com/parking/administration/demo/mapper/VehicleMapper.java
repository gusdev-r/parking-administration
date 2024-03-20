package com.parking.administration.demo.mapper;

import com.parking.administration.demo.domain.Vehicle;
import com.parking.administration.dto.response.VehicleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VehicleMapper {
    VehicleResponse toVehicle(Vehicle vehicle);
    List<VehicleResponse> toListOfVehicle(List<Vehicle> vehicleList);
}
