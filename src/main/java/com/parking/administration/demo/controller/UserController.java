package com.parking.administration.demo.controller;

import com.parking.administration.demo.service.ClientService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = {"/v1/client/", "/v1/client"})
public class ClientController {
    private ClientService clientService;
}
