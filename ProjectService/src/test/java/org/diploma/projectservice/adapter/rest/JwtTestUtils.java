package org.diploma.projectservice.adapter.rest;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

public class JwtTestUtils {
    public SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwt() {
        var jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("login", "test")
                .build();
        return SecurityMockMvcRequestPostProcessors.jwt().jwt(jwt);
    }
}
