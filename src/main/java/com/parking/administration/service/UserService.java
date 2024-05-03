
package com.parking.administration.service;

import com.parking.administration.domain.User;
import com.parking.administration.domain.Vehicle;
import com.parking.administration.domain.email.EmailService;
import com.parking.administration.domain.token.ConfirmationToken;
import com.parking.administration.dto.request.VehiclePutRequest;
import com.parking.administration.dto.response.VehicleResponse;
import com.parking.administration.infra.exception.BadRequestException;
import com.parking.administration.infra.exception.PasswordNotValidException;
import com.parking.administration.infra.exception.UserNotFoundException;
import com.parking.administration.infra.exception.VehicleNotFoundException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import com.parking.administration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.parking.administration.util.Utility.LOGGER;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
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

       BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
       String encodedPassword = bCryptPasswordEncoder.encode(userToRegister.getPassword());

       if (!bCryptPasswordEncoder.matches(userToRegister.getPassword(), encodedPassword)) {
           LOGGER.error("The encoded password does not match - UserDetailsService");
           throw new PasswordNotValidException(ErrorCode.EM0003.getMessage(), ErrorCode.EM0003.getCode());
        }

       userToRegister.setPassword(encodedPassword);
       userRepository.save(userToRegister);

       String randomTokenToConfirm = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                randomTokenToConfirm,
                userToRegister,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20)
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

       return randomTokenToConfirm;
    }
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
