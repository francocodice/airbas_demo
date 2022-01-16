package com.afm.apigateway.security.jwt;

import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtManager jwtManager;

    public String generateJwt(UserBas user)  {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtManager.CLAIM_KEY_EMAIL, user.getEmail());
        claims.put(JwtManager.CLAIM_KEY_CREATED, new Date());

        List<String> auth = user
                .getAuthorities().stream()
                .map(role-> role.getName().toString())
                .collect(Collectors.toList());

        claims.put(JwtManager.CLAIM_KEY_AUTHORITIES, auth);
        return jwtManager.buildToken(claims);
    }
}
