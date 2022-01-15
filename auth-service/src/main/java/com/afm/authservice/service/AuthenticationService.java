package com.afm.authservice.service;

import com.afm.authservice.repository.UserBasRepository;
import com.afm.authservice.security.JWTAuthenticationManager;
import lombok.RequiredArgsConstructor;

import model.auth.AuthProvider;
import model.auth.ERole;
import model.auth.UserBas;
import model.exception.BadRequestException;
import model.exception.ResourceNotFoundException;
import model.utils.LoginRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @RequiredArgsConstructor generates a constructor with 1 parameter for each field that requires special handling.
 * All non-initialized final fields get a parameter, as well as any fields that are marked as @NonNull that aren't
 * initialized where they are declared. For those fields marked with @NonNull, an explicit null check is also generated.
 * The constructor will throw a NullPointerException if any of the parameters intended for the fields marked with
 * @NonNull contain null. The order of the parameters match the order in which the fields appear in your class.
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserBasRepository userBasRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTAuthenticationManager jwtAuthenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean exisitUser(String email){
        return userBasRepository.findByEmail(email) != null;
    }

    public UserBas findUser(String email){
        return userBasRepository.findByEmail(email);
    }

    public List<UserBas> findAll(){
        return userBasRepository.findAll();
    }

    public void deleteUser(UserBas user){
        userBasRepository.delete(user);
    }


    public String authenticateUser(LoginRequest credentials) throws ResourceNotFoundException,
            BadCredentialsException {

        if (findUser(credentials.getEmail()) == null)
            throw new ResourceNotFoundException("Email not exists");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(),
                    credentials.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadRequestException("Password Wrong");
        }

        return jwtAuthenticationManager.generateJwtToken(credentials.getEmail());
    }

    public void createUser(LoginRequest request, AuthProvider provider) throws IllegalArgumentException {
        if (userBasRepository.findByEmail(request.getEmail()) != null)
            throw new BadRequestException("Email already exists");

        UserBas newUser = new UserBas();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setRole(ERole.ROLE_USER);
        newUser.setProvider(provider);
        userBasRepository.save(newUser);
    }









}
