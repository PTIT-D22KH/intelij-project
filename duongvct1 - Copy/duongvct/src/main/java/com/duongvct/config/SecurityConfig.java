package com.duongvct.config;

import com.duongvct.constants.RoleConstant;
import com.duongvct.service.impl.AccountServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AccountServiceImpl accountService;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/register/**", "/login/**", "/css/**").permitAll()
                        .requestMatchers("/dashboard").hasAnyAuthority(
                                RoleConstant.ROLE_ADMIN.getId(),
                                RoleConstant.ROLE_USER.getId(),
                                RoleConstant.ROLE_EMPLOYEE.getId()
                        )
//                        .requestMatchers("/dashboard").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
//                        .defaultSuccessUrl("/dashboard", true)
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                if (roles.contains(RoleConstant.ROLE_EMPLOYEE.getId())) {
                    if (accountService.findByUsername(authentication.getName()).isFirstLogin() == true) {
                        response.sendRedirect("/change-password");

                    } else {
                        response.sendRedirect("/admin/dashboard");
                    }
                }
                else if (roles.contains(RoleConstant.ROLE_ADMIN.getId())) {
                    response.sendRedirect("/admin/dashboard");
                } else {
                    response.sendRedirect("/dashboard");
                }
            }
        };
    }
}