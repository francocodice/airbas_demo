package com.afm.apigateway.controller;

import com.afm.apigateway.saga.orchestrators.UserCreationOrchestrator;
import com.afm.apigateway.security.jwt.JwtTokenUtil;
import com.afm.apigateway.security.jwt.JwtUser;
import com.afm.apigateway.service.AuthService;
import com.afm.apigateway.service.ProfileService;
import model.utils.LoginRequest;
import model.utils.TokenDto;
import model.utils.UserPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private UserCreationOrchestrator userCreationSaga;


    @Value("${jwt.header}")
    private String tokenHeader;

    public AuthController(AuthService authService,
                          JwtTokenUtil jwtTokenUtil, ProfileService profileService) {

        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
        userCreationSaga = new UserCreationOrchestrator(authService, profileService);

    }

    @CrossOrigin
    @GetMapping("auth/users")
    public ResponseEntity<?> users(){
        return new ResponseEntity(authService.allUser(), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credential, HttpServletResponse response)  {
        JwtUser currentUser = authService.authenticateUser(credential);
        final String token = jwtTokenUtil.generateToken(currentUser);
        response.setHeader(tokenHeader,token);
        System.out.println(token);
        return new ResponseEntity(currentUser, HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("auth/signup")
    public ResponseEntity<?> signUp(@RequestBody UserPayload payload) throws Throwable {
        UserPayload user = userCreationSaga.createUser(payload);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @CrossOrigin
    @PostMapping("oauth/google")
    public ResponseEntity<?> loginGoogle(@RequestBody TokenDto token)  {
        TokenDto tokenDto = authService.loginGoogle(token);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("oauth/facebook")
    public ResponseEntity<?> loginFacebook(@RequestBody TokenDto token)  {
        TokenDto tokenDto = authService.loginFacebook(token);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("oauth/amazon")
    public ResponseEntity<?> loginAmazon(@RequestBody TokenDto token)  {
        TokenDto tokenDto = authService.loginAmazon(token);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }
}
