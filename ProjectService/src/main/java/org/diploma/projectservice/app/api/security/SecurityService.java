package org.diploma.projectservice.app.api.security;

import org.diploma.projectservice.adapter.security.dto.AuthorizedUser;

public interface SecurityService {
    AuthorizedUser getAuthorizedUser();
}
