package com.parking.administration.demo.dto.request;

public record VehiclePutRequest (
        String brand, String model, String color,
        String licensePlateNumber
){
}
