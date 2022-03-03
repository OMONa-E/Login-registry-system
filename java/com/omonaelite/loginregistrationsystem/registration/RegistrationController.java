package com.omonaelite.loginregistrationsystem.registration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
        @Autowired
        private final RegistrationService service;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest request) {
        return service.registerUser(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return service.confirmToken(token);
    }
}
