package com.omonaelite.loginregistrationsystem.registration;

import lombok.Data;

@Data
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private final String password;
}
