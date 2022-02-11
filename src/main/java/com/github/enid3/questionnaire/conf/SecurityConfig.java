package com.github.enid3.questionnaire.conf;

import com.github.enid3.questionnaire.security.jwt.JwtTokenAuthenticationFilter;
import com.github.enid3.questionnaire.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
public class SecurityConfig {
    @Bean
    SecurityFilterChain springWebFilterChain(HttpSecurity http,
                                             JwtTokenProvider tokenProvider) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(c-> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeRequests(c -> c
                        .antMatchers(HttpMethod.GET, "/").permitAll()
                        .antMatchers(HttpMethod.GET, "/js/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/fonts/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/css/**").permitAll()
                        .antMatchers("/api/v1/auth/*").permitAll()
                        .antMatchers("/api/v1/questionnaire/*").permitAll()
                        .antMatchers(HttpMethod.POST,"/api/v1/response/*").permitAll()
                        .antMatchers("/api/v1/**").hasRole("USER")
                        //.antMatchers(HttpMethod.DELETE, "/vehicles/**").hasRole("ADMIN")
                        //.antMatchers(HttpMethod.GET, "/v1/vehicles/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtTokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        return authentication -> {
            String email = authentication.getPrincipal() + "";
            String password = authentication.getCredentials() + "";

            UserDetails user = userDetailsService.loadUserByUsername(email);

            if (!encoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Bad credentials");
            }

            if (!user.isEnabled()) {
                throw new DisabledException("User account is not active");
            }

            return new UsernamePasswordAuthenticationToken(email, null, user.getAuthorities());
        };
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
