package org.diploma.notificationservice.fw.mail.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
}
