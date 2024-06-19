package com.duskio.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "duskio")
public record ApplicationProperties(
        String[] publicEndpoints,
        String[] allowedOrigins,
        String keycloakEndpoint,
        String keycloakRealm,
        String keycloakClient,
        String homeEndpoint,
        String logoutSuccessUrl
) {
}
