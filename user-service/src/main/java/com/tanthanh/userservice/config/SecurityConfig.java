package com.tanthanh.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.logging.Logger;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    private DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;
    final static Logger logger = Logger.getLogger(String.valueOf(SecurityConfig.class));

    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/users/login").permitAll()
                        .requestMatchers("/api/v1/regis/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    UserDetailsManager users(DataSource dataSource) throws SQLException {

        jdbcTemplate.update("DELETE FROM authorities");
        jdbcTemplate.update("DELETE FROM users where username != 'ta'");
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("ta"))
                .authorities("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("ta"))
                .authorities("USER", "ADMIN")
                .build();
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);
        users.createUser(admin);
        return users;
    }
}

