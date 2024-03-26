package com.parking.administration.commons;

import com.parking.administration.demo.domain.ParkingSpace;
import com.parking.administration.demo.dto.request.ParkingSpacePutRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingSpaceUtils {

    public List<ParkingSpace> newParkingSpaceList() {
        var parkingSpace1 = ParkingSpace.builder().condominiumBlock("BlocoE").condominiumApartment("E505")
                .vehicleBrand("Nissan").vehicleModel("Altima").vehicleColor("White").createdAt(LocalDateTime.now())
                .id(156L)
                .responsibleVehicleName("Chris Miller").vehicleSpaceNumber("202").vehicleLicensePlateNumber("JKL0124")
                .build();
        var parkingSpace2 = ParkingSpace.builder().condominiumBlock("BlocoD").condominiumApartment("D404")
                .vehicleBrand("Chevrolet").vehicleModel("Malibu").vehicleColor("Black").createdAt(LocalDateTime.now())
                .id(157L)
                .responsibleVehicleName("Emily Davis").vehicleSpaceNumber("101").vehicleLicensePlateNumber("GHI7893")
                .build();
        var parkingSpace3 = ParkingSpace.builder().condominiumBlock("BlocoC").condominiumApartment("C303")
                .vehicleBrand("Honda").vehicleModel("Civic").vehicleColor("Silver").createdAt(LocalDateTime.now())
                .id(158L)
                .responsibleVehicleName("Bob Johnson").vehicleSpaceNumber("789").vehicleLicensePlateNumber("DEF4562")
                .build();
        return new ArrayList<>(List.of(parkingSpace1, parkingSpace2, parkingSpace3));
    }
    public ParkingSpace parkingSpaceToSave() {
        return ParkingSpace.builder().id(159L)
                .condominiumApartment("BlocoF")
                .condominiumBlock("F606")
                .createdAt(LocalDateTime.now())
                .vehicleSpaceNumber("303")
                .vehicleColor("Green")
                .vehicleBrand("Mazda")
                .vehicleModel("CX-5")
                .vehicleLicensePlateNumber("MNO3455")
                .responsibleVehicleName("Sarah Wilson")
                .build();
    }
    public ParkingSpacePutRequest parkingSpaceUpdated() {
        return ParkingSpacePutRequest.builder()
                .condominiumBlock("F606")
                .condominiumApartment("BlocoF")
                .vehicleBrand("Mazda")
                .vehicleModel("CX-5")
                .vehicleColor("Purple") //element changed
                .responsibleVehicleName("Purple Guy") //element changed
                .vehicleSpaceNumber("303")
                .vehicleLicensePlateNumber("MNO3455")
                .build();
    }
    public ParkingSpace parkingSpaceToUpdate() {
        return ParkingSpace.builder().id(159L)
                .condominiumApartment("BlocoF")
                .condominiumBlock("F606")
                .createdAt(LocalDateTime.now())
                .vehicleSpaceNumber("303")
                .vehicleColor("Green")
                .vehicleBrand("Mazda")
                .vehicleModel("CX-5")
                .vehicleLicensePlateNumber("MNO3455")
                .responsibleVehicleName("Sarah Wilson")
                .build();
    }

}
