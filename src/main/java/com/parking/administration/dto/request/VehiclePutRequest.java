package com.parking.administration.dto.request;

public record VehiclePutRequest (
        String brand, String model, String color,
        String licensePlateNumber
){
}
