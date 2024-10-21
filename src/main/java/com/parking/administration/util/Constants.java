package com.parking.administration.util;

public class Constants {
    public static final String ADMIN_USER_ENDPOINT = "/v1/api/user/**";
    public static final String ADMIN_VEHICLE_ENDPOINT = "/v1/api/vehicle/**";
    public static final String ADMIN_PARKING_SPACE_ENDPOINT = "/v1/api/moder/ps/**";
    public static final String TOKEN_URL = "http://localhost:8082/v1/api/registration/confirm/token?token=";
    public static final String BASE_URL = "/api/v1";
    public static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
}
