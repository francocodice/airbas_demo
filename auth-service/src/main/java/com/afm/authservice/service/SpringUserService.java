package com.afm.authservice.service;



import com.afm.authservice.repository.UserBasRepository;
import com.afm.authservice.security.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import model.auth.UserBas;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringUserService implements UserDetailsService {
    private final UserBasRepository userBasRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserBas credentials = userBasRepository.findByEmail(email);

        if (credentials == null) {
            throw new UsernameNotFoundException(email);
        }
        return JwtUserFactory.create(credentials);

        /*UserDetails user = User.withUsername(
                credentials.getEmail()).
                password(credentials.getPassword()).
                authorities("USER").build();*/

    }
}
