package com.parking.administration.demo.service;

import com.parking.administration.commons.UserUtils;
import com.parking.administration.demo.domain.User;
import com.parking.administration.demo.infra.exception.UserNotFoundException;
import com.parking.administration.demo.infra.exception.VehicleNotFoundException;
import com.parking.administration.demo.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;
import java.util.Optional;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUtils userUtils;
    private User user;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = userUtils.UserCompleteRegistered();

    }
    @Test
    @DisplayName("updateVehicleAttributes(), update vehicle attributes when is ok")
    void updateVehicleAttributes() {
    }

    @Test
    @DisplayName("deleteVehicleRegistered(), delete a vehicle when the user and the vehicle exists")
    @Order(1)
    void deleteVehicleRegistered() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Assertions.assertThatNoException().isThrownBy(
                () -> userService.deleteVehicleRegistered(1L, 1L));

        Assertions.assertThat(user.getVehicleList()).isEmpty();


    }
    @Test
    @DisplayName("deleteVehicleRegistered(), throw an exception when no user is found")
    @Order(2)
    void deleteVehicleRegisteredCase2() {

        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(
                () -> userService.deleteVehicleRegistered(10L, 1L))
                .isInstanceOf(UserNotFoundException.class);
    }
    @Test
    @DisplayName("deleteVehicleRegistered(), throw an exception when no vehicle is found")
    @Order(3)
    void deleteVehicleRegisteredCase3() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Assertions.assertThatException().isThrownBy(
                        () -> userService.deleteVehicleRegistered(1L, 10L))
                .isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void signUpUser() {
    }

    @Test
    void enableUser() {
    }
}