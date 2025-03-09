package org.diploma.userservice.adapter.security;

import org.diploma.userservice.adapter.security.dto.AuthorizedUser;
import org.diploma.userservice.app.api.security.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class SecurityAdapter implements SecurityService {
    @Override
    public AuthorizedUser getAuthorizedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new SecurityException("Authentication required");
        }
        var principal = (Jwt) auth.getPrincipal();
        return AuthorizedUser.builder()
            .login(principal.getClaimAsString("login"))
            .build();
    }
}
