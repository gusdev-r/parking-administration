package com.parking.administration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TokenDto(@JsonProperty("token") String token){

}
