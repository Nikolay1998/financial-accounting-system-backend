package kraynov.n.financialaccountingsystembackend.security.config;

import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.security.impl.SimpleContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.UserService;
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

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        return http
                .authorizeRequests()
                .antMatchers("/user/add").permitAll()
                .antMatchers("/", "/**").authenticated()
                .and()
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public ContextHolderFacade contextHolderFacade() {
        return new SimpleContextHolderFacade();
    }
}
