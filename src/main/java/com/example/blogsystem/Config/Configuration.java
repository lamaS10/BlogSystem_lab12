package com.example.blogsystem.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf -> csrf.disable())

                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/register", "/api/v1/blog/get", "/api/v1/blog/get-by-id/{blogId}",
                                "/api/v1/blog/get-by-title/{title}").permitAll()
                        .requestMatchers("/api/v1/blog/get-my-blogs", "/api/v1/blog/add", "/api/v1/blog/update/{blogId}",
                                "/api/v1/blog/delete/{blogId}", "/api/v1/auth/update", "/api/v1/auth/delete").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/api/v1/auth/get-users").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .invalidateHttpSession(true)

                )
                .httpBasic(basic -> {})
                .build();


    }


}
