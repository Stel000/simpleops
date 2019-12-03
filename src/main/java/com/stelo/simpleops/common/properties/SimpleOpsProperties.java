package com.stelo.simpleops.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "simple-ops")
public class SimpleOpsProperties {

    private String adminPassword = "123456";

}
