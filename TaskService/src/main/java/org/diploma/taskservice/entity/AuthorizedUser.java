package org.diploma.taskservice.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizedUser {
    private String login;
}
