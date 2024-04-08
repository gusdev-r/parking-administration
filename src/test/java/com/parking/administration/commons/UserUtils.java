package com.parking.administration.commons;

import com.parking.administration.demo.domain.ParkingSpace;
import com.parking.administration.demo.domain.User;
import com.parking.administration.demo.domain.Vehicle;
import com.parking.administration.demo.domain.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public User UserCompleteRegistered() {

        Vehicle vehicle = Vehicle.builder().id(1L).brand("Honda").model("Honda Civic Hatch")
                .color("Blue").licensePlateNumber("XYZ9876").build();
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(vehicle);

        ParkingSpace parkingSpace = ParkingSpace.builder().id(1L).condominiumBlock("Block A")
                .condominiumApartment("Residential White water condominium")
                .vehicleBrand(vehicle.getBrand()).vehicleModel(vehicle.getModel()).vehicleColor(vehicle.getColor())
                .responsibleVehicleName("João Santos").vehicleSpaceNumber("220")
                .vehicleLicensePlateNumber("XYZ9876").build();

        List<ParkingSpace> parkingSpaceList = new ArrayList<>();
        parkingSpaceList.add(parkingSpace);

        return User.builder().id(1L).fullName("João Santos").email("joaosilva@gmail.com").password("Jo8271ão#")
                .document("21328373281").username("jo8ao").vehicleList(vehicleList).parkingSpaceList(parkingSpaceList).build();

    }
   public Vehicle vehicleUpdated() {
       return Vehicle.builder().brand("Toyota").model("Supra").color("Silver")
               .licensePlateNumber("XYZ9876").build();
   }

   public User userToSignUp() {
        return User.builder().fullName("Meliodas").email("melpecados@gmail.com")
                .password("eliZa31@#").document("12134819212").username("meliod012").build();
   }
}
