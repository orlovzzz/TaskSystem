package org.diploma.userservice.app.api.security;

import org.diploma.userservice.adapter.security.dto.AuthorizedUser;

public interface SecurityService {
    AuthorizedUser getAuthorizedUser();
}
