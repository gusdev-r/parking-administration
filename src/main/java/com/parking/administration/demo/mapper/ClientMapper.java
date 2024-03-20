package com.parking.administration.demo.mapper;

import com.parking.administration.demo.domain.Client;
import com.parking.administration.dto.response.ClientResponse;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
    ClientResponse toClient(Client client);
}
