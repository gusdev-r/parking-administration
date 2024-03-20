
package com.parking.administration.demo.service;

import com.parking.administration.demo.domain.Client;
import com.parking.administration.demo.infra.exception.VehicleNotFoundException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import com.parking.administration.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.parking.administration.demo.utils.Utility.LOGGER;

@Service
public class ClientService {
    private ClientRepository clientRepository;
    public Client save(Client client) {
        return clientRepository.save(client);
    }
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
    private void validateClientById(Long id) {
        LOGGER.info("Starting the validation of the client space searched by Id - ClientService");
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            LOGGER.error("This client was not found or not exists at the system - ClientService");
            throw new VehicleNotFoundException(ErrorCode.WA0003.getMessage(), ErrorCode.WA0003.getCode());
        }
    }
}
