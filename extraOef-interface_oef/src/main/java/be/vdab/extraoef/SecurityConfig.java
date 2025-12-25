package be.vdab.extraoef;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private static final String IK = "ik";
    @Bean
    InMemoryUserDetailsManager maakPrincipals() {
        var gebruiker = User
                .withUsername("raf")
                .password("{noop}raf")
                .authorities(IK)
                .build();
        return new InMemoryUserDetailsManager(gebruiker);
    }
    @Bean
    SecurityFilterChain geefRechten(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.formLogin(withDefaults());
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.POST, "/database").permitAll()
                .requestMatchers("/images/**", "/css/**", "/js/**","/database/**","/database","/database/"
                ).permitAll()
                .requestMatchers("/","/index.html","/mensen/**")
                .hasAuthority(IK)
        .anyRequest().permitAll());
        return http.build();
    }

    }
