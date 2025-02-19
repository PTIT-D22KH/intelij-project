package com.duongvct.config;

import com.duongvct.entity.Account;
import com.duongvct.exception.InactiveAccountException;
import com.duongvct.utils.Role;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
                                Role.ROLE_ADMIN.getId(),
                                Role.ROLE_USER.getId(),
                                Role.ROLE_EMPLOYEE.getId()
                        )
//                        .requestMatchers("/dashboard").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
//                        .defaultSuccessUrl("/dashboard", true)
                        .successHandler(customAuthenticationSuccessHandler())
//                        .failureUrl("/login?error=true")
                                .failureHandler(customerAuthenticationFailureHandler())
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
                if (roles.contains(Role.ROLE_EMPLOYEE.getId())) {
                    if (accountService.findByUsername(authentication.getName()).isFirstLogin() == true) {
                        response.sendRedirect("/change-password");

                    } else {
                        response.sendRedirect("/admin/dashboard");
                    }
                }
                else if (roles.contains(Role.ROLE_ADMIN.getId())) {
                    response.sendRedirect("/admin/dashboard");
                } else {
                    response.sendRedirect("/dashboard");
                }
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler customerAuthenticationFailureHandler(){
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                Account account = accountService.findByUsername(request.getParameter("username"));
                System.out.println(account);
                if (account != null && !account.isActivated()) {
                    response.sendRedirect("/login?error=inactivated");
                } else {
                    response.sendRedirect("/login?error=true");
                }

            }
        };
    }
}