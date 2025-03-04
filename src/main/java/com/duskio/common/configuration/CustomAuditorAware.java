package com.duskio.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Map;
import java.util.Optional;

/**
 * AuditorAware indicates current principal for properties annotated with @CreatedBy or @LastModifiedBy
 */
@Slf4j
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of("Anonymous");
//            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof DefaultOidcUser user) {
            OidcIdToken token = user.getIdToken();
            Map<String, Object> claims = token.getClaims();
            String userId = (String) claims.getOrDefault("user_id", "");
            String username = user.getName();
            return Optional.of(username + "|" + userId);
        }

        return Optional.empty();
    }
}
