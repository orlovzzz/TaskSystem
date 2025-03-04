package org.diploma.projectservice.adapter.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizedUser {
    private String login;
}
