package org.diploma.userservice.adapter.keycloak;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import org.diploma.userservice.app.api.security.IdentityProvider;
import org.diploma.userservice.entity.User;
import org.diploma.userservice.fw.property.KeycloakClientProperties;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdapter implements IdentityProvider {
    private final Keycloak keycloak;
    private final KeycloakClientProperties props;

    public void createUser(User user) {
        var userRepresentation = createNewUser(user);
        try (var response = keycloak.realm(props.realm()).users().create(userRepresentation)) {
            handleResponse(response);
        }
    }

    public void updateUser(String login, User user) {
        var userRepresentation = createNewUser(user);
        keycloak.realm(props.realm()).users().search(login).stream()
                .filter(u -> u.getUsername().equals(login))
                .findFirst()
                .ifPresent(u ->
                        keycloak.realm(props.realm()).users().get(u.getId()).update(userRepresentation));
    }

    public void deleteUser(User user) {
        keycloak.realm(props.realm()).users().search(user.getLogin()).stream()
                .filter(ur -> ur.getUsername().equals(user.getLogin())).findFirst()
                .ifPresent(ur -> {
                    try (var response = keycloak.realm(props.realm()).users().delete(ur.getId())) {
                        handleResponse(response);
                    }
                });
    }

    private UserRepresentation createNewUser(User user) {
        var represent = new UserRepresentation();
        represent.setEnabled(true);
        represent.setEmailVerified(true);
        represent.setUsername(user.getLogin());
        represent.setEmail(user.getEmail());
        represent.setFirstName(user.getFirstName());
        represent.setLastName(user.getLastName());

        var credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(user.getPassword());
        credentials.setTemporary(false);
        represent.setCredentials(List.of(credentials));
        return represent;
    }

    private void handleResponse(Response response) {
        log.info("Response returned from Keycloak. Status: {}, description: {}", response.getStatus(), response.getStatusInfo());
        var status = response.getStatus();
        if (status < 200 || response.getStatus() > 299) {
            throw new RuntimeException("Error response returned from Keycloak. Status: " + response.getStatus());
        }
    }
}
