
package com.parking.administration.service;

import com.parking.administration.domain.core.User;
import com.parking.administration.domain.core.Vehicle;
import com.parking.administration.domain.token.Token;
import com.parking.administration.dto.request.VehiclePutRequest;
import com.parking.administration.dto.response.VehicleResponse;
import com.parking.administration.infra.exception.BadRequestException;
import com.parking.administration.infra.exception.PasswordNotValidException;
import com.parking.administration.infra.exception.UserNotFoundException;
import com.parking.administration.infra.exception.VehicleNotFoundException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import com.parking.administration.repository.UserRepository;
import com.parking.administration.service.authProcess.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.parking.administration.util.Utility.LOGGER;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final ModelMapper modelMapper;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.WA0003.getMessage(), ErrorCode.WA0003.getCode()));
    }

    public List<VehicleResponse> getAllVehiclesFromUser(Long id) {
        LOGGER.info("Searching the user by Id - ClientService");
        User user = findUserById(id);
        return user.getVehicleList().stream().map(vehicleList -> modelMapper
                .map(vehicleList, VehicleResponse.class)).toList();
    }

    public void updateVehicleAttributes(VehiclePutRequest vehicleUpdated, Long vehicleId, Long userId) {
        Optional<User> optionalUser = validateUserOptional(userId);

        Optional<Vehicle> optionalVehicle = optionalUser.get().getVehicleList()
                .stream().filter(vehicle -> vehicle.getId().equals(vehicleId)).findFirst();

        Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleUpdated, vehicle);
        vehicle.setId(optionalVehicle.get().getId());
        vehicle.setCreatedAt(optionalVehicle.get().getCreatedAt());

        optionalUser.get().getVehicleList().remove(optionalVehicle.get());
        optionalUser.get().getVehicleList().add(vehicle);
    }

    private Optional<User> validateUserOptional(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(ErrorCode.WA0003.getMessage(), ErrorCode.WA0003.getCode());
        }
        return optionalUser;
    }

    public void deleteVehicleRegistered(Long userId, Long vehicleId) {
        Optional<User>  optionalUser = validateUserOptional(userId);

        Optional<Vehicle> optionalVehicle = optionalUser.get().getVehicleList()
                .stream().filter(vehicle -> vehicle.getId().equals(vehicleId)).findFirst();

        if (optionalVehicle.isEmpty()) {
            throw new VehicleNotFoundException(ErrorCode.WA0002.getMessage(), ErrorCode.WA0002.getCode());
        }
        optionalUser.get().getVehicleList().remove(optionalVehicle.get());
    }

    public String signUpUser(User userToRegister) {
        boolean userExists = userRepository.findByEmail(userToRegister.getEmail()).isPresent();
        if (userExists) {
            LOGGER.error("The email was already taken - UserDetailsService");
            throw new BadRequestException(ErrorCode.EM0002.getMessage(), ErrorCode.EM0002.getCode());
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userToRegister.getPassword());
        LOGGER.info(encodedPassword);
        userToRegister.setPassword(encodedPassword);
        userRepository.save(userToRegister);

        SecureRandom random = new SecureRandom();
        String codeTkn = String.valueOf(100000 + random.nextInt(900000));
        Token token = new Token(
                codeTkn,
                userToRegister,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20)
        );
        confirmationTokenService.saveConfirmationToken(token);

        return codeTkn;
    }

    public void enableUser(String email) {
        userRepository.updateConfirmedAt(email, LocalDateTime.now());
    }
}
