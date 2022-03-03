package com.omonaelite.loginregistrationsystem.user;

import com.omonaelite.loginregistrationsystem.registration.token.ConfirmationToken;
import com.omonaelite.loginregistrationsystem.registration.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public record AppUserService(AppUserRepository repository,
                             BCryptPasswordEncoder bCryptPasswordEncoder,
                             ConfirmationTokenService tokenService) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found"));
    }

    public String singUpUser(AppUser appUser) {
        boolean userExists = repository.findByUsername(appUser.getUsername()).isPresent()
                || repository.findByEmail(appUser.getEmail()).isPresent();
        boolean userReconfirm = repository.findByUsername(appUser.getUsername()).isPresent()
                && repository.findByEmail(appUser.getEmail()).isPresent()
                && repository.findByFirstName(appUser.getFirstName()).isPresent()
                && repository.findByLastName(appUser.getLastName()).isPresent()
                && repository.findByEnabled(appUser.isEnabled()).equals(false);
        if (userExists) {
            throw new IllegalStateException("Email or Username Taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        repository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
        tokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void enableAppUser(String email) {
        repository.enableAppUser(email);
    }
}
