package com.parking.administration.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


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

    public String getCondominiumBlock() {
        return condominiumBlock;
    }

    public void setCondominiumBlock(String condominiumBlock) {
        this.condominiumBlock = condominiumBlock;
    }

    public String getCondominiumApartment() {
        return condominiumApartment;
    }

    public void setCondominiumApartment(String condominiumApartment) {
        this.condominiumApartment = condominiumApartment;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getResponsibleVehicleName() {
        return responsibleVehicleName;
    }

    public void setResponsibleVehicleName(String responsibleVehicleName) {
        this.responsibleVehicleName = responsibleVehicleName;
    }

    public String getVehicleSpaceNumber() {
        return vehicleSpaceNumber;
    }

    public void setVehicleSpaceNumber(String vehicleSpaceNumber) {
        this.vehicleSpaceNumber = vehicleSpaceNumber;
    }

    public String getVehicleLicensePlateNumber() {
        return vehicleLicensePlateNumber;
    }

    public void setVehicleLicensePlateNumber(String vehicleLicensePlateNumber) {
        this.vehicleLicensePlateNumber = vehicleLicensePlateNumber;
    }

    //private Constructor to use only in this class
    private ParkingSpacePutRequest(String condominiumBlock, String condominiumApartment, String vehicleBrand,
                                   String vehicleModel, String vehicleColor, String responsibleVehicleName,
                                   String vehicleSpaceNumber, String vehicleLicensePlateNumber) {
        this.condominiumBlock = condominiumBlock;
        this.condominiumApartment = condominiumApartment;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.responsibleVehicleName = responsibleVehicleName;
        this.vehicleSpaceNumber = vehicleSpaceNumber;
        this.vehicleLicensePlateNumber = vehicleLicensePlateNumber;
    }

    // Getters

    public static ParkingSpacePutRequestBuilder builder() {
        return new ParkingSpacePutRequestBuilder();
    }

    public static class ParkingSpacePutRequestBuilder {
        private String condominiumBlock;
        private String condominiumApartment;
        private String vehicleBrand;
        private String vehicleModel;
        private String vehicleColor;
        private String responsibleVehicleName;
        private String vehicleSpaceNumber;
        private String vehicleLicensePlateNumber;

        private ParkingSpacePutRequestBuilder() {

        }

        public ParkingSpacePutRequestBuilder condominiumBlock(String condominiumBlock) {
            this.condominiumBlock = condominiumBlock;
            return this;
        }

        public ParkingSpacePutRequestBuilder condominiumApartment(String condominiumApartment) {
            this.condominiumApartment = condominiumApartment;
            return this;
        }

        public ParkingSpacePutRequestBuilder vehicleBrand(String vehicleBrand) {
            this.vehicleBrand = vehicleBrand;
            return this;
        }

        public ParkingSpacePutRequestBuilder vehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
            return this;
        }

        public ParkingSpacePutRequestBuilder vehicleColor(String vehicleColor) {
            this.vehicleColor = vehicleColor;
            return this;
        }

        public ParkingSpacePutRequestBuilder responsibleVehicleName(String responsibleVehicleName) {
            this.responsibleVehicleName = responsibleVehicleName;
            return this;
        }

        public ParkingSpacePutRequestBuilder vehicleSpaceNumber(String vehicleSpaceNumber) {
            this.vehicleSpaceNumber = vehicleSpaceNumber;
            return this;
        }

        public ParkingSpacePutRequestBuilder vehicleLicensePlateNumber(String vehicleLicensePlateNumber) {
            this.vehicleLicensePlateNumber = vehicleLicensePlateNumber;
            return this;
        }

        public ParkingSpacePutRequest build() {
            return new ParkingSpacePutRequest(condominiumBlock, condominiumApartment, vehicleBrand,
                    vehicleModel, vehicleColor, responsibleVehicleName, vehicleSpaceNumber,
                    vehicleLicensePlateNumber);
        }
    }
}
