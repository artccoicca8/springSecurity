package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccesHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private LoginSuccesHandler successHandler; 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/**
		 * Se asigna de manera manual los accesos por url del los permisos a cada rol
		 * 
		 */
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
				.antMatchers("/ver/**").hasAnyRole("USER")
				.antMatchers("/uploads/**").hasAnyRole("USER")
				.antMatchers("/form/**").hasAnyRole("ADMIN")
				.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				.antMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				/** Se agraga un succes handler , para que notifique los ingresos satisfacotiios */
				.successHandler(successHandler)
				.loginPage("/login")
				.permitAll()
				.and().logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403");

	}

	
	/**
	 * -se inyecta los datos en memroria 
	 * */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {

		// PasswordEncoder encoder =
		// PasswordEncoderFactories.createDelegatingPasswordEncoder();
		// UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		UserBuilder users = User.withDefaultPasswordEncoder();

		build.inMemoryAuthentication().withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		.withUser(users.username("andres").password("12345").roles("USER"));

	}

}
