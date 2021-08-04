package ru.otus.spring.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.JwtConfiguration;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
    private final JwtConfiguration configuration;
    private final UserDetailsService userDetailsService;

    @Override
    public String getToken(String username, String password) {
        Instant now = Instant.now();
        long expiry = 60L;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!password.equals(userDetails.getPassword())) {
            throw new UsernameNotFoundException(username);
        }
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("self")
                .issueTime(new Date(now.toEpochMilli()))
                .expirationTime(new Date(now.plusSeconds(expiry).toEpochMilli()))
                .subject(username)
                .claim("scope", scope)
                .build();
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();
        SignedJWT jwt = new SignedJWT(header, claims);
        return sign(jwt).serialize();
    }

    private SignedJWT sign(SignedJWT jwt) {
        try {
            jwt.sign(new RSASSASigner(configuration.getPrivateKey()));
            return jwt;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
