package org.diploma.taskservice.app.api;

import org.diploma.taskservice.entity.AuthorizedUser;

public interface SecurityService {
    AuthorizedUser getAuthorizedUser();
}
