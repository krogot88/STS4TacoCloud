package tacos.security;


import javax.sql.DataSource;
import javax.xml.stream.events.EndDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder encoder() {
		return new StandardPasswordEncoder("53cr3t");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/design", "/orders")
				.access("hasRole('ROLE_USER')")
			.antMatchers("/", "/**","/h2_console/**").access("permitAll")
		.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/design", true)
		.and()
			.logout()
				.logoutSuccessUrl("/")		
		;
		
		//  just remove both strings to  ON CSRF  security back
		http.csrf().disable();
        http.headers().frameOptions().disable();
	}
	
	
	
	
	/* LDAP Authentication example
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	throws Exception {
	auth
		.ldapAuthentication()
			.userSearchBase("ou=people")
			.userSearchFilter("(uid={0})")
			.groupSearchBase("ou=groups")
			.groupSearchFilter("member={0}")
			.passwordCompare()
			.passwordEncoder(new BCryptPasswordEncoder())
			.passwordAttribute("passcode");			
	}
	*/ // end LDAP Authentication example
	
	/*  starting JDBC-authentication
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("select username, password, enabled from Users where username=?")
				.authoritiesByUsernameQuery("select username, authority from UserAuthorities where username=?")
				.passwordEncoder(new StandardPasswordEncoder("53cr3t"));
	}
	*/ // end JDBC-authentication


	/*  The In-Memory Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
			.withUser("buzz")
			//.password(encoder.encode("infinity"))
			.password("{noop}infinity")
			.authorities("ROLE_USER")
		.and()
			.withUser("woody")
			.password("{noop}bullseye")
			.authorities("ROLE_USER");
		
	}
	 */ //end  The In-Memory Authentication  

}
