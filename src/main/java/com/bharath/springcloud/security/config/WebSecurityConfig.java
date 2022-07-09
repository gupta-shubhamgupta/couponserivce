package com.bharath.springcloud.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bharath.springcloud.security.UserDetailsServiceImp;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImp userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("AuthenticationManagerBuilder called 1 at start");
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("HttpSecurity called 2 at start");
//		http.formLogin();
//		http.authorizeRequests()
//				.mvcMatchers(HttpMethod.GET, "/couponapi/coupons/**", "/", "index", "/showCreateCoupon",
//						"/createCoupon", "/createRespone", "/showGetCoupon", "/getCoupon", "/couponDetails")
//				.hasAnyRole("USER", "ADMIN")
//				.mvcMatchers(HttpMethod.POST, "/couponapi/coupons", "/saveCoupon", "/getCoupon").hasRole("ADMIN")
//				.anyRequest().denyAll().and().csrf().disable();
		//http.formLogin();
		http.authorizeRequests()
				.mvcMatchers(HttpMethod.GET, "/couponapi/coupons/**", "index", 
						 "/createRespone", "/showGetCoupon", "/getCoupon", "/couponDetails")
				.hasAnyRole("USER", "ADMIN")
				.mvcMatchers(HttpMethod.GET,"/showCreateCoupon", "/createCoupon").hasRole("ADMIN")
				.mvcMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN")
				.mvcMatchers(HttpMethod.POST, "/couponapi/coupons", "/saveCoupon", "/getCoupon").hasRole("ADMIN")
				.mvcMatchers("/","/login","/logout","/showReg","/registerUser").permitAll()
				.anyRequest().denyAll().and().csrf().disable().logout().logoutSuccessUrl("/");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean //this is used also inplace of above BCryptPasswordEncoder bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
