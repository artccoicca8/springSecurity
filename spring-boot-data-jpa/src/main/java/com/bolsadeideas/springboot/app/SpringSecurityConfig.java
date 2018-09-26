package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccesHandler;
import com.bolsadeideas.springboot.app.models.service.JpaUserDetailsService;

/** Se habilita la autenticaicon por anotaciones , estas seran agregadas a  cada  metodo 
 * de un controlador , o al controlador mismo si desea habiliatr **/
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true )
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private LoginSuccesHandler successHandler; 
	/**
	@Autowired
	private DataSource  dataSource ; 
	**/
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/** se obtiene los datos de la configuarcion por jpa */
	@Autowired
	private JpaUserDetailsService detailService; 
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/**
		 * Se asigna de manera manual los accesos por url del los permisos a cada rol
		 * 
		 */
		http.authorizeRequests()/** es aqui donde se asigna las rutas de accesos , asi como los permsios de accesos una vez que se incia la session 
		, por ende decimos que una vez logueado el usuairo se rediira al listar **/
				.antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar").permitAll()
//				.antMatchers("/ver/**").hasAnyRole("USER")
//				.antMatchers("/uploads/**").hasAnyRole("USER")
//				.antMatchers("/form/**").hasAnyRole("ADMIN")
//				.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
//				.antMatchers("/factura/**").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				/** Se agraga un succes handler , para que notifique los ingresos satisfactorios */
				.successHandler(successHandler)
				/** redirecciona al controlador del login  */
				.loginPage("/login")
				.permitAll()
				.and().logout().permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/error_403");

	}

	
	 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
		
		/** Se refencia a la clase del deatis service , para que se pudea manejar el acceso por bd  ,
		 *  este ya resuelve con el core de spring el manjo de usuario y acceos */
		
		build
		.userDetailsService(detailService)
		.passwordEncoder(passwordEncoder); 
		
//		build.jdbcAuthentication()
//		/**se le pasa el datasource */
//		.dataSource(dataSource)
//		/**Se le pasa el encriptador de datos */
//		.passwordEncoder(passwordEconder)
//		/**se busca al usuario **/
//		.usersByUsernameQuery("select username , password , enable from users  where username=?")
//		/**se busca los roles */
//		.authoritiesByUsernameQuery("select u.username, a.authority   from authorities a inner join users u on (a.user_id= u.id) where u.username=? ");
//		
//		
		
		System.out.println("llego al configureGlobal"); 
		
//		build.jdbcAuthentication()
//		.dataSource(dataSource)
//		.passwordEncoder(passwordEncoder)
//		.usersByUsernameQuery("select username, password, enabled from users where username=?")
//		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");
//		
		
		// PasswordEncoder encoder =
		// PasswordEncoderFactories.createDelegatingPasswordEncoder();
		// UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
//	 
//		UserBuilder users = User.withDefaultPasswordEncoder();
//
//		build.inMemoryAuthentication().withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
//		.withUser(users.username("andres").password("12345").roles("USER"));
		
	 

	}

}
