package com.mkyong.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").and().formLogin()
				.loginPage("/login").defaultSuccessUrl("/levelCheck").failureUrl("/login?error")
				.usernameParameter("username").passwordParameter("password")
				.successHandler(new MySimpleUrlAuthenticationSuccessHandler()).and().logout()
				.logoutSuccessUrl("/login?logout").and().csrf().and().exceptionHandling().accessDeniedPage("/403");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	// new add for redirect pages after success login
	public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

		private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException {
			handle(request, response, authentication);
			clearAuthenticationAttributes(request);
		}

		protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
				throws IOException {
		
			String aa = request.getParameter("level");
			String targetUrl = determineTargetUrl(aa);
		
		
			//String targetUrl = determineTargetUrl(authentication);

			if (response.isCommitted()) {
				return;
			}

			redirectStrategy.sendRedirect(request, response, targetUrl);
		}

		protected String determineTargetUrl(String aa) {
			if (aa.equals("County")) {
				return "/County";
			} else if (aa.equals("District")) {
				return "/District";
			} 
			else if (aa.equals("PO")) {
				return "/PO";
			} 
			else if (aa.equals("DOE")) {
				return "/DOE";
			} 
			else {
				throw new IllegalStateException();
			}
		}
		
		
		//Builds the target URL according to the logic defined in the main
		 
		/*
		protected String determineTargetUrl(Authentication authentication) {
			boolean isUser = false;
			boolean isAdmin = false;
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority grantedAuthority : authorities) {
				if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
					isUser = true;
					break;
				} else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
					isAdmin = true;
					break;
				}
			}

			if (isUser) {
				return "/fail";
			} else if (isAdmin) {
				return "/fail";
			} else {
				throw new IllegalStateException();
			}
		}
*/
		protected void clearAuthenticationAttributes(HttpServletRequest request) {
			HttpSession session = request.getSession(false);
			if (session == null) {
				return;
			}
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}

		public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
			this.redirectStrategy = redirectStrategy;
		}

		protected RedirectStrategy getRedirectStrategy() {
			return redirectStrategy;
		}

	}

}