
package bk.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(request -> {
                            try {
                                request
                                        .requestMatchers("/img/**").permitAll()
                                        .requestMatchers("/css/**").permitAll()
                                        .requestMatchers("/js/**").permitAll()
                                        .requestMatchers("/webjars/**").permitAll()
                                        .requestMatchers("/chat/info**").permitAll()
                                        .requestMatchers("/chat/**").permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                                        .logout(c ->
                                                c.logoutSuccessUrl("/login?logout")
                                                        .logoutUrl("/logout")
                                                        .permitAll()
                                        )
                                        .formLogin()
                                        .defaultSuccessUrl("/chatpage")
                                        .loginPage("/login")
                                        .failureUrl("/login?error")
                                        .permitAll();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 =
                User.withDefaultPasswordEncoder()
                        .username("user1")
                        .password("user1")
                        .roles("USER")
                        .build();
        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("user2")
                        .password("user2")
                        .roles("USER")
                        .build();

        UserDetails user3 =
                User.withDefaultPasswordEncoder()
                        .username("user3")
                        .password("user3")
                        .roles("USER", "ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

}
