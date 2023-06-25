package hs.aalen.urlaub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JpaUserDetailsService myMemberDetailsService;



    public SecurityConfig(JpaUserDetailsService myMemberDetailsService) {
        this.myMemberDetailsService = myMemberDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/page/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/saveMember").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(myMemberDetailsService)
                .formLogin(form -> form
                        .loginPage("/page/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

}
