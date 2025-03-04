package org.diploma.userservice.fw.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak.client")
public record KeycloakClientProperties(String url, String realm, String grantType, String clientId, String username, String password) {
}
