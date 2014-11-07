
package bk.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/chat/info**").permitAll()
				.antMatchers("/chat**").permitAll()
				.anyRequest().authenticated()
				.and()
			.logout()
				.logoutSuccessUrl("/login?logout")
				.logoutUrl("/logout")
				.permitAll()
				.and()
			.formLogin()
				.defaultSuccessUrl("/")
				.loginPage("/login")
				.failureUrl("/login?error")
				.permitAll();
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user1").password("user1").roles("USER").and()
				.withUser("user2").password("user2").roles("USER").and()
				.withUser("user3").password("user3").roles("ADMIN","USER");
	}
}