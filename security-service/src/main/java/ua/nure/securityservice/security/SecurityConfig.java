package ua.nure.securityservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.nure.securityservice.security.exceptionHandling.AmourlinkAuthenticationEntryPoint;
import ua.nure.securityservice.security.exceptionHandling.AmourlinkBearerTokenAccessDeniedHandler;
import ua.nure.securityservice.security.exceptionHandling.AmourlinkBearerTokenAuthenticationEntryPoint;
import ua.nure.securityservice.security.jwt.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] SECURED_URLs = {};

    private static final String[] UN_SECURED_URLs = {
            "/api/security-service/login",
            "/api/security-service/login/**",
            "/api/security-service/users/add",
            "/api/security-service/activation-token/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/swagger-resources",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security"};

    private final JwtAuthenticationFilter authenticationFilter;
    private final AmourlinkUserDetailsService amourLinkUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final AmourlinkAuthenticationEntryPoint amourlinkAuthenticationEntryPoint;
//    private final AmourlinkBearerTokenAuthenticationEntryPoint amourlinkBearerTokenAuthenticationEntryPoint;
//    private final AmourlinkBearerTokenAccessDeniedHandler amourlinkBearerTokenAccessDeniedHandler;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(amourLinkUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(UN_SECURED_URLs).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(amourlinkAuthenticationEntryPoint));
//                .oauth2ResourceServer().jwt().and()
//                .authenticationEntryPoint(amourlinkBearerTokenAuthenticationEntryPoint)
//                .accessDeniedHandler(amourlinkBearerTokenAccessDeniedHandler);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
