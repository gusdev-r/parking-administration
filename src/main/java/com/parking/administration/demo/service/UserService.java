
package com.parking.administration.demo.service;

import com.parking.administration.demo.domain.User;
import com.parking.administration.demo.domain.token.ConfirmationToken;
import com.parking.administration.demo.domain.token.ConfirmationTokenService;
import com.parking.administration.demo.email.EmailService;
import com.parking.administration.demo.infra.exception.BadRequestException;
import com.parking.administration.demo.infra.exception.UserNotFoundException;
import com.parking.administration.demo.infra.exception.VehicleNotFoundException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import com.parking.administration.demo.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.parking.administration.demo.utils.Utility.LOGGER;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       ConfirmationTokenService confirmationTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    private void validateClientById(Long id) {
        LOGGER.info("Starting the validation of the client space searched by Id - ClientService");
        Optional<User> clientOptional = userRepository.findById(id);
        if (clientOptional.isEmpty()) {
            LOGGER.error("This user was not found or not exists at the system - ClientService");
            throw new VehicleNotFoundException(ErrorCode.WA0003.getMessage(), ErrorCode.WA0003.getCode());
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(
                ErrorCode.WA0003.getMessage(), ErrorCode.WA0003.getCode()));
    }

    public String signUpUser(User userRegisterRequest) {
       boolean userExists = userRepository.findByEmail(userRegisterRequest.getEmail()).isPresent();

       if (userExists) {
           LOGGER.error("The email was already taken - ClientService");
           throw new BadRequestException(ErrorCode.WA0004.getMessage(), ErrorCode.WA0004.getCode());
       }
       String encodedPassword = bCryptPasswordEncoder.encode(userRegisterRequest.getPassword());
       userRegisterRequest.setPassword(encodedPassword);
       userRepository.save(userRegisterRequest);

       String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                userRegisterRequest,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(20)
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        emailService.send(userRegisterRequest.getFullName(), userRegisterRequest.getEmail());

       return token;
    }
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
}
