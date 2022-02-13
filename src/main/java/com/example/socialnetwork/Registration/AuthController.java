package com.example.socialnetwork.Registration;

import com.example.socialnetwork.Registration.Token.ConfirmationTokenRepository;
import com.example.socialnetwork.Security.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    //@CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/signup")
    public void register(@RequestBody RegistrationRequest request){
          authService.register(request);
    }

   // @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){

        //TODO: Check is email already confirmed
        //TODO: Duplicates of users with same email delete
            return authService.confirmToken(token);

    }

   // @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
