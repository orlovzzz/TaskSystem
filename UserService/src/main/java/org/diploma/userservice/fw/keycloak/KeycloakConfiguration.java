package org.diploma.userservice.fw.keycloak;

import lombok.RequiredArgsConstructor;
import org.diploma.userservice.fw.property.KeycloakClientProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfiguration {
    private final KeycloakClientProperties props;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(props.url())
                .realm(props.realm())
                .grantType(props.grantType())
                .clientId(props.clientId())
                .username(props.username())
                .password(props.password())
                .build();
    }
}