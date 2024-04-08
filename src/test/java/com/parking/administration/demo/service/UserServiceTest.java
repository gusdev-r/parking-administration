package com.parking.administration.demo.service;

import com.parking.administration.commons.UserUtils;
import com.parking.administration.demo.domain.User;
import com.parking.administration.demo.domain.Vehicle;
import com.parking.administration.demo.infra.exception.BadRequestException;
import com.parking.administration.demo.infra.exception.UserNotFoundException;
import com.parking.administration.demo.infra.exception.VehicleNotFoundException;
import com.parking.administration.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUtils userUtils;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailService emailService;
    private User user;

    private Vehicle vehicleUpdated;
    private User userToSignUp;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = userUtils.UserCompleteRegistered();
        vehicleUpdated = userUtils.vehicleUpdated();
        userToSignUp = userUtils.userToSignUp();

    }
    @Test
    @DisplayName("updateVehicleAttributes(), update vehicle attributes when is ok")
    @Order(1)
    void updateVehicleAttributes() {

        Long vehicleId = 1L;
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Assertions.assertThatNoException()
                .isThrownBy(() -> userService.updateVehicleAttributes(vehicleUpdated, vehicleId, userId));

        Vehicle vehicle = user.getVehicleList().get(0);

        assertEquals(vehicle.getBrand() , vehicleUpdated.getBrand());
        assertEquals(vehicle.getColor() , vehicleUpdated.getColor());
        assertEquals(vehicle.getModel() , vehicleUpdated.getModel());
        assertEquals(vehicle.getLicensePlateNumber(), vehicleUpdated.getLicensePlateNumber());
    }
    @Test
    @DisplayName("updateVehicleAttributes(), update vehicle attributes when is ok")
    @Order(2)
    void updateVehicleAttributesCase2() {

        Long vehicleId = 1L;
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Assertions.assertThatNoException()
                .isThrownBy(() -> userService.updateVehicleAttributes(vehicleUpdated, vehicleId, userId));

        Vehicle vehicle = user.getVehicleList().get(0);

        assertEquals(vehicle.getBrand() , vehicleUpdated.getBrand());
        assertEquals(vehicle.getColor() , vehicleUpdated.getColor());
        assertEquals(vehicle.getModel() , vehicleUpdated.getModel());
        assertEquals(vehicle.getLicensePlateNumber(), vehicleUpdated.getLicensePlateNumber());
    }

    @Test
    @DisplayName("deleteVehicleRegistered(), delete a vehicle when the user and the vehicle exists")
    @Order(3)
    void deleteVehicleRegistered() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


        Assertions.assertThatNoException().isThrownBy(
                () -> userService.deleteVehicleRegistered(1L, 1L));

        Assertions.assertThat(user.getVehicleList()).isEmpty();

    }
    @Test
    @DisplayName("deleteVehicleRegistered(), throw an exception when no user is found")
    @Order(4)
    void deleteVehicleRegisteredCase2() {

        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(
                () -> userService.deleteVehicleRegistered(10L, 1L))
                .isInstanceOf(UserNotFoundException.class);
    }
    @Test
    @DisplayName("deleteVehicleRegistered(), throw an exception when no vehicle is found")
    @Order(5)
    void deleteVehicleRegisteredCase3() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Assertions.assertThatException().isThrownBy(
                        () -> userService.deleteVehicleRegistered(1L, 10L))
                .isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    @DisplayName("loadUserByUsername(), find the user by email when it exists")
    @Order(6)
    void loadUserByUsername() {
        String email = "joaosilva@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertThatNoException().isThrownBy(() -> userService.loadUserByUsername(email));

        assertNotNull(user.getUsername());
        assertNotNull(user.getEmail());
        assertNotNull(user.getDocument());
    }
    @Test
    @DisplayName("UserNotFoundException(), throw UserNotFoundException when no user is found by email")
    @Order(7)
    void loadUserByUsernameCase2() {
        String email = "noexist@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> userService.loadUserByUsername(email))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("signUpUser(), sign up a new user when it doesn't exists")
    @Order(8)
    void signUpUser() {

        assertNotNull(userToSignUp.getPassword());

        when(userRepository.findByEmail(userToSignUp.getEmail())).thenReturn(Optional.empty());

        Assertions.assertThatNoException().isThrownBy(() -> userService.signUpUser(userToSignUp));

        verify(confirmationTokenService, times(1)).saveConfirmationToken(any());

        verify(emailService, times(1)).send(any(), any());
    }
    @Test
    @DisplayName("signUpUser(), throw BadRequestException when user already exists")
    @Order(9)
    void signUpUserCase2() {

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Assertions.assertThatException()
                .isThrownBy(() -> userService.signUpUser(user)).isInstanceOf(BadRequestException.class);
    }
    @Test
    @DisplayName("enableUser(), enable the user at the system with the query at the repository")
    @Order(10)
    void enableUser() {
        String email = user.getEmail();

        when(userRepository.enableUser(email)).thenReturn(anyInt());
        Assertions.assertThatNoException().isThrownBy(() -> userService.enableUser(email));
    }
}