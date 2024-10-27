package com.parking.administration.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
         @Server(
                 description = "LOCAL ENV",
                 url = "http://localhost:8082"
         ),
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        },
        info = @Info(
                title = "Parking administration",
                version = "1.0.0",
                description = "This is a project involving the administration of parking spaces in a parking lot.",
                contact = @Contact(
                        name = "Gustavo Moreira",
                        email = "g.hen.moreira@gmail.com",
                        url = "https://gusdev-r.github.io/gusdevr-portfolio/"
                ),
                license = @License(
                        name = "Licença MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
