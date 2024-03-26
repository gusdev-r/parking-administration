package com.parking.administration.demo.dto.response;

import java.time.LocalDateTime;

public record ParkingSpaceResponse(String condominiumBlock, String condominiumApartment, String vehicleBrand, String vehicleModel,
                                   String vehicleColor, Long id, String responsibleVehicleName, String vehicleSpaceNumber,
                                   String vehicleLicensePlateNumber, LocalDateTime createdAt) {
}
