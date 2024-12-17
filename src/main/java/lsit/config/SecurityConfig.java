package lsit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.disable())
            .authorizeHttpRequests(authorize -> authorize
                //.requestMatchers("", "").permitAll() // If you want ot allow access to certain endpoints w/o authentication :)
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(oauth2UserService())
                )
            );
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return userRequest -> {
            OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

            // Extract GitLab groups and convert to roles
            List<String> groups = new ArrayList<>();

            Object generalGroupsObj = oauth2User.getAttribute("groups");
            if (generalGroupsObj instanceof List<?>) {
                for (Object group : (List<?>) generalGroupsObj) {
                    if (group instanceof String) {
                        groups.add((String) group);
                    }
                }
            }


            List<GrantedAuthority> authorities = new ArrayList<>(oauth2User.getAuthorities());

            if (!groups.isEmpty()) {
                groups.forEach(group -> {
                    if (group.contains("pizzeria_server")) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_PIZZERIA_SERVER"));
                    }
                    if (group.contains("pizzeria_manager")) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_PIZZERIA_MANAGER"));
                    }
                    if (group.contains("pizzeria_customer")) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_PIZZERIA_CUSTOMER"));
                    }
                });
            }

            // Log the extracted roles for debugging
            authorities.forEach(authority -> System.out.println("Granted Authority: " + authority.getAuthority()));

            return new DefaultOAuth2User(authorities, oauth2User.getAttributes(), "sub");
        };
    }
}
