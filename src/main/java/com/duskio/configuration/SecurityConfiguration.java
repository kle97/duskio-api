package com.duskio.configuration;

import com.duskio.common.constant.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration {
    
    private final ApplicationProperties properties;

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainForResourceServer(HttpSecurity http) throws Exception {
        http.securityMatchers(matcher -> matcher.requestMatchers(properties.publicEndpoints())
                                                .requestMatchers(Constant.PUBLIC_API_PATH +"**"))
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorize) -> authorize.requestMatchers(properties.publicEndpoints()).permitAll()
                                                           .requestMatchers(Constant.PUBLIC_API_PATH +"**").authenticated()
                                                           .anyRequest().hasAuthority(Constant.ROLE_ADMIN))
            .oauth2ResourceServer(configurer -> configurer.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChainForOauth2Client(HttpSecurity http) throws Exception {
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
            .cors(Customizer.withDefaults())
            .csrf(Customizer.withDefaults())
            .oauth2Client(Customizer.withDefaults())
            .oauth2Login(Customizer.withDefaults())
            .logout(logout -> logout.addLogoutHandler(getLogoutHandler()).logoutSuccessUrl(properties.homeEndpoint()))
            .authorizeHttpRequests((authorize) -> authorize.anyRequest().hasAuthority(Constant.ROLE_ADMIN));
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(properties.allowedOrigins());
            }
        };
    }
    
    @Bean
    public GrantedAuthoritiesMapper getGrantedAuthoritiesMapper() {
        return authorities -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            GrantedAuthority authority = authorities.iterator().next();

            if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
                log.info(userInfo.getClaims().toString());
                if (userInfo.hasClaim("resource_access")) {
                    Map<String, Object> resourceAccess = userInfo.getClaimAsMap("resource_access");
                    if (resourceAccess.containsKey(properties.keycloakClient())) {
                        Map<String, Object> client = (Map<String, Object>) resourceAccess.get(properties.keycloakClient());
                        Collection<String> roles = (Collection<String>) client.get("roles");
                        mappedAuthorities.addAll(roles.stream().map(SimpleGrantedAuthority::new).toList());
                    }
                }
            }
            return mappedAuthorities;
        };
    }
    
    public LogoutHandler getLogoutHandler() {
        return (request, response, authentication) -> {
            OidcUser user = (OidcUser) authentication.getPrincipal();
            String logoutUrl = user.getIssuer() + "/protocol/openid-connect/logout";
            RestClient.create()
                      .get()
                      .uri(logoutUrl + "?id_token_hint={token}", user.getIdToken().getTokenValue())
                      .retrieve()
                      .toEntity(String.class);
        };
    }
}
