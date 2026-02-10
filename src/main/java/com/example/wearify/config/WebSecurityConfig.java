package com.example.wearify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.wearify.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

        private final UserRepository userRepository;
        private final CustomAuthenticationSuccessHandler successHandler;
        private final CustomAuthenticationFailureHandler failureHandler;

        public WebSecurityConfig(UserRepository userRepository, CustomAuthenticationSuccessHandler successHandler,
                        CustomAuthenticationFailureHandler failureHandler) {
                this.userRepository = userRepository;
                this.successHandler = successHandler;
                this.failureHandler = failureHandler;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests((requests) -> requests
                                                .requestMatchers("/", "/about", "/mens", "/womens", "/accessories",
                                                                "/shoes", "/register",
                                                                "/product-details/{id}")
                                                .permitAll()
                                                .requestMatchers("/css/**", "/js/**", "/images/**", "/styles.css",
                                                                "/script.js",
                                                                "/review-code.js")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin((form) -> form
                                                .loginPage("/login")
                                                .successHandler(successHandler)
                                                .failureHandler(failureHandler)
                                                .permitAll())
                                .logout((logout) -> logout
                                                .logoutSuccessUrl("/")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll());

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                return username -> userRepository.findByUsername(username)
                                .map(user -> org.springframework.security.core.userdetails.User.builder()
                                                .username(user.getUsername())
                                                .password(user.getPassword())
                                                .roles(user.getRole())
                                                .disabled(!user.isEnabled())
                                                .build())
                                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        }

}
