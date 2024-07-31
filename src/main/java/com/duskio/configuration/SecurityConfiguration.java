package com.duskio.configuration;

import com.duskio.common.constant.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;
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
    public SecurityFilterChain filterChainForResourceServer(HttpSecurity http) throws Exception {
        http.securityMatchers(matcher -> matcher.requestMatchers(Constant.API_PATH +"**"))
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorize) -> authorize.requestMatchers(Constant.PUBLIC_API_PATH +"**").permitAll()
                                                           .requestMatchers(Constant.API_PATH +"**").authenticated()
                                                           .requestMatchers(Constant.ADMIN_API_PATH +"**")
                                                           .permitAll()
//                                                           .hasAuthority(Constant.ROLE_ADMIN)
                                                           .anyRequest().hasAuthority(Constant.ROLE_ADMIN))
            .oauth2ResourceServer(configurer -> configurer.jwt(Customizer.withDefaults()));
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
}
