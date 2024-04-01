package com.parking.administration.demo.mapper;

import com.parking.administration.demo.domain.Vehicle;
import com.parking.administration.demo.dto.request.VehiclePutRequest;
import com.parking.administration.demo.dto.response.VehicleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VehicleMapper {
    VehicleResponse toVehicleResponse(Vehicle vehicle);
    List<VehicleResponse> toListOfVehicle(List<Vehicle> vehicleList);
    Vehicle toVehiclePutRequest (VehiclePutRequest vehicle);
}
