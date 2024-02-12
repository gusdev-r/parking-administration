package com.parking.administration.demo.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PSPostResponse {

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
    @Size(max = 7)
    private String vehicleLicensePlateNumber;

}
