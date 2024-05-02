package com.parking.administration.domain.enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.parking.administration.domain.enums.Permission.*;
@Getter
public enum UserRole {
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_CREATE,
            ADMIN_UPDATE,
            ADMIN_DELETE
    )),
    USER(Collections.emptySet())
    ;
    private final Set<Permission> permissions;

    UserRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream().
                map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
