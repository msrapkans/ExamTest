package com.example.demo.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails user1 = userBuilder.username("ankit").password("abc").roles("ADMIN").build();
        UserDetails user2 = userBuilder.username("shiva").password("xyz").roles("ADMIN").build();
        UserDetails user3 = userBuilder.username("durgesh").password("").roles("USER").build();
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
    
}
