package kraynov.n.financialaccountingsystembackend.security.config;

import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.security.impl.FASBasicAuthenticationEntryPoint;
import kraynov.n.financialaccountingsystembackend.security.impl.SimpleContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BasicAuthenticationEntryPoint fasBasicAuthenticationEntryPoint() {
        return new FASBasicAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> {
            UserDetails userDetails = userService.getByName(username);
            if (userDetails == null) {
                throw new UsernameNotFoundException("User " + username + " not found");
            }
            return userDetails;
        };
    }

    @Bean
    public SecurityContextHolder securityContextHolder() {
        return new SecurityContextHolder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           BasicAuthenticationEntryPoint fasBasicAuthenticationEntryPoint,
                                           CorsConfigurationSource corsConfigurationSource) throws Exception {
        http.csrf().disable();
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeRequests()
                .antMatchers("/user/add").permitAll()
                .antMatchers("/rate/calculateEquivalents").permitAll() //todo
                .antMatchers("/", "/**").authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(fasBasicAuthenticationEntryPoint);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(@Value("${allowed-origins}") String allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public ContextHolderFacade contextHolderFacade() {
        return new SimpleContextHolderFacade();
    }
}
