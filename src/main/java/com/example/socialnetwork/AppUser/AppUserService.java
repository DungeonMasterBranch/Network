package com.example.socialnetwork.AppUser;

import com.example.socialnetwork.Registration.Token.ConfirmationToken;
import com.example.socialnetwork.Registration.Token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    @Autowired
    private final AppUserRepository appUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user= appUserRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException
                                (String.format(USER_NOT_FOUND_MSG, email)));
        return new User(user.getUsername(),user.getPassword(),
                true,true,true,true,
                getAuthorities("ROLE_USER"));

    }

    public boolean userExists(String appUserEmail){
        return appUserRepository.findByEmail(appUserEmail)
                .isPresent();
    }

    public String signUpUser(AppUser appUser){

        if(userExists(appUser.getEmail()) && appUser.getEnabled()){
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);


        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        return Collections.singletonList(new SimpleGrantedAuthority(role_user));
    }

}
