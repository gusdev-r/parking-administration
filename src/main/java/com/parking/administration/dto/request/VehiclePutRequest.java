package com.parking.administration.dto.request;

import lombok.Builder;

@Builder
public record VehiclePutRequest (
        String brand, String model, String color,
        String licensePlateNumber
){
}
