package org.diploma.projectservice.fw.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "service")
public class ServiceUrlProperties {
    private String userUrl;
}
