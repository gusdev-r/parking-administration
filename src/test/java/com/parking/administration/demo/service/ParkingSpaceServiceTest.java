package com.parking.administration.demo.service;

import com.parking.administration.commons.ParkingSpaceUtils;
import com.parking.administration.controller.ParkingSpaceController;
import com.parking.administration.domain.ParkingSpace;
import com.parking.administration.dto.response.ParkingSpaceResponse;
import com.parking.administration.infra.exception.BadRequestException;
import com.parking.administration.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.repository.ParkingSpaceRepository;
import com.parking.administration.service.ParkingSpaceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParkingSpaceServiceTest {

    @InjectMocks
    private ParkingSpaceService parkingSpaceService;
    @Mock
    private ParkingSpaceController parkingSpaceController;
    @Mock
    private ParkingSpaceRepository parkingSpaceRepository;

    @InjectMocks
    private ParkingSpaceUtils parkingSpaceUtils;

    private List<ParkingSpace> parkingSpaceList;

    @BeforeEach
    void setUp() {
        parkingSpaceList = parkingSpaceUtils.newParkingSpaceList();
    }
    @Test
    @DisplayName("save(), creates a new parking space")
    @Order(1)
    void createParkingSpaceCase() throws BadRequestException {
        var parkingSpaceToSave = parkingSpaceUtils.parkingSpaceToSave();

//        when(parkingSpaceRepository.save(parkingSpaceToSave)).thenReturn(parkingSpaceToSave);
//        Assertions.assertThatNoException().isThrownBy(() -> parkingSpaceValidator
//                .validateRegisterToCreate(parkingSpaceToSave));

        //var parkingSpaceSaveService = parkingSpaceService.create(parkingSpaceToSave);
        //Assertions.assertThat(parkingSpaceSaveService).isEqualTo(parkingSpaceToSave).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("findById(), return an optional parkingSpace when id exists")
    @Order(2)
    void findByIdCase1() throws ParkingSpaceNotFoundException {

        var id = 156L;
        ParkingSpace parkingSpaceExpected = new ParkingSpace();

        when(parkingSpaceRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(parkingSpaceExpected));
        var parkingSpaceResponse = parkingSpaceService.findById(id);

        Assertions.assertThat(parkingSpaceResponse).isEqualTo(parkingSpaceExpected);

        assertNotNull(parkingSpaceResponse.condominiumApartment());
        assertNotNull(parkingSpaceResponse.condominiumBlock());
        assertNotNull(parkingSpaceResponse.vehicleBrand());
        assertNotNull(parkingSpaceResponse.vehicleColor());
        assertNotNull(parkingSpaceResponse.vehicleSpaceNumber());
        assertNotNull(parkingSpaceResponse.vehicleModel());
        assertNotNull(parkingSpaceResponse.responsibleVehicleName());
        assertNotNull(parkingSpaceResponse.vehicleLicensePlateNumber());
    }

    @Test
    @DisplayName("findById(), throw an exception when the parking space searched by id not exists")
    @Order(3)
    void findByIdCase2() {
        var id = 1L;
        when(parkingSpaceRepository.findById(id)).thenReturn(Optional.empty());
        var parkingSpaceOptional = parkingSpaceRepository.findById(id);
        Assertions.assertThat(parkingSpaceOptional).isEmpty();
        Assertions.assertThatException()
                .isThrownBy(() -> parkingSpaceService.findById(id))
                .isInstanceOf(ParkingSpaceNotFoundException.class);
    }

    @Test
    @DisplayName("delete(), removes a parking space")
    @Order(4)
    void deleteCase1() throws ParkingSpaceNotFoundException {
        var id = 156L;
        var parkingSpaceExpected = parkingSpaceList.get(0);
        //var parkingSpace = parkingSpaceExpected.getId();

        doNothing().when(parkingSpaceRepository).delete(parkingSpaceExpected);

        Assertions.assertThatNoException().isThrownBy(() -> parkingSpaceService.delete(id));

    }
    @Test
    @DisplayName("delete(), throw an exception when there's no parking space to delete")
    @Order(5)
    void deleteCase2() throws ParkingSpaceNotFoundException {
        var id = 1L;
        when(parkingSpaceRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> parkingSpaceService.findById(id))
                .isInstanceOf(ParkingSpaceNotFoundException.class);
    }
    @Test
    @DisplayName("update(), update the parking space when successful")
    @Order(6)
    void updateCase1() throws ParkingSpaceNotFoundException {

        var parkingSpaceUpdated =  parkingSpaceUtils.parkingSpaceUpdated();

        var parkingSpaceToUpdate = parkingSpaceUtils.parkingSpaceToUpdate();

        var id = 159L;

        when(parkingSpaceRepository.findById(id)).thenReturn(Optional.of(parkingSpaceToUpdate));
        Assertions.assertThat(parkingSpaceToUpdate.getId()).isEqualTo(id);

        parkingSpaceService.update(parkingSpaceUpdated, id);
        Assertions.assertThatNoException().isThrownBy(() -> parkingSpaceService.update(parkingSpaceUpdated, id));

    }
    @Test
    @DisplayName("update(), throw a exception when no parking space is passed to update")
    @Order(7)
    void updateCase2() throws ParkingSpaceNotFoundException {
        var id = 159L;

        when(parkingSpaceRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> parkingSpaceService
                .update(null ,id))
                .isInstanceOf(ParkingSpaceNotFoundException.class);

    }

}