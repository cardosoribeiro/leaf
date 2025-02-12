package br.com.leaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password hashing
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll() // Permit static resources
                .antMatchers("/signup").permitAll() // Permit home and about pages
                .antMatchers("/users/**", "/categories/**", "/diseases/**", "/assignments/**").hasRole("ADMINISTRATOR") // Admin access
                .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin((form) -> form
                //.loginPage("/login") // Custom login page
                .permitAll()
            )
            .logout((logout) -> logout
                .permitAll());

        return http.build();
    }
}