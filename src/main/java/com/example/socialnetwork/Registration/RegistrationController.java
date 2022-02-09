package com.example.socialnetwork.Registration;

import com.example.socialnetwork.Registration.Token.ConfirmationTokenRepository;
import com.example.socialnetwork.Registration.Token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return  registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){

        //TODO: Check is email already confirmed
            return registrationService.confirmToken(token);

    }
}
