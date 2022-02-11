package com.example.socialnetwork.Registration;

import com.example.socialnetwork.Registration.Token.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @PostMapping("/signup")
    public String register(@RequestBody RegistrationRequest request){
        return  authService.register(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){

        //TODO: Check is email already confirmed
        //TODO: Duplicates of users with same email delete
            return authService.confirmToken(token);

    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
