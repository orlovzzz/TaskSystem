package org.diploma.taskservice.app.api;

import java.util.List;

public interface UserService {
    List<String> getUserEmails(List<String> logins);
}
