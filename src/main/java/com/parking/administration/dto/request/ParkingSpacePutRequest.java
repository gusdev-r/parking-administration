package com.parking.administration.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ParkingSpacePutRequest {
    @NotBlank
    private String condominiumBlock;

    @NotBlank
    private String condominiumApartment;

    @NotBlank
    private String vehicleBrand;

    @NotBlank
    private String vehicleModel;

    @NotBlank
    private String vehicleColor;
    @NotBlank
    private String responsibleVehicleName;

    @NotBlank
    private String vehicleSpaceNumber;

    @NotBlank
    private String vehicleLicensePlateNumber;
}
