package com.parking.administration.demo.domain.enums;

public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    USER_READ("user:read"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete")
    ;
    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    Permission() {
    }

    public String getPermission() {
        return permission;
    }
}
