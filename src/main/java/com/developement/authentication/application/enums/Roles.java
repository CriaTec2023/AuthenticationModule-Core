package com.developement.authentication.application.enums;

public enum Roles {
    ADMIN("admin"),
    USER("user"),
    COMPANY("company");
    private String role;
    Roles(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
