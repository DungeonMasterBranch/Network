package com.example.socialnetwork.Registration;

import com.example.socialnetwork.AppUser.AppUser;
import com.example.socialnetwork.AppUser.AppUserRole;
import com.example.socialnetwork.AppUser.AppUserService;
import com.example.socialnetwork.Email.EmailSender;
import com.example.socialnetwork.Email.EmailText;
import com.example.socialnetwork.Registration.Token.ConfirmationToken;
import com.example.socialnetwork.Registration.Token.ConfirmationTokenService;
import com.example.socialnetwork.Security.AuthenticationResponse;
import com.example.socialnetwork.Security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private final AppUserService appUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public void register(RegistrationRequest request) {

            boolean isValidEmail = emailValidator.
                    test(request.getEmail());

            if(!isValidEmail){
                throw new IllegalStateException("email not valid");
            }
            String token = appUserService.signUpUser(
                    new AppUser(
                            request.getFirstName(),
                            request.getLastName(),
                            request.getEmail(),
                            request.getPassword(),
                            AppUserRole.USER
                    )
            );
            String link = "http://localhost:8080/api/v1/registration/confirm?token="+token;
            emailSender.send(
                    request.getEmail(),
                    EmailText.buildEmail(request.getFirstName(), link));


    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()->
                        new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail()
        );

        return "confirmation";
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    public Optional<User> getCurrentUser() {
        User principal = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return Optional.of(principal);
    }





}
