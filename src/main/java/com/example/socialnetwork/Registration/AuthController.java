package com.example.socialnetwork.Registration;

import com.example.socialnetwork.Registration.Token.ConfirmationTokenRepository;
import com.example.socialnetwork.Security.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @PostMapping("/signup")
    public void register(@RequestBody RegistrationRequest request){
          authService.register(request);
    }


    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){

        //TODO: Check is email already confirmed
        //TODO: Duplicates of users with same email delete
            return authService.confirmToken(token);

    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
