package com.parking.administration.demo.controller;

import com.parking.administration.demo.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = {"/v1/user/api", "/v1/user/api/"})
public class UserController {
    private UserService userService;

    //TODO: Update personal infos and Delete and add cars
}
