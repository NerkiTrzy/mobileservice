package com.roguskip.roguskiwarehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
//import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@SpringBootApplication
//@EnableOAuth2Client
//@EnableWebSecurity
public class RoguskiWarehouseApplication {//extends WebSecurityConfigurerAdapter {

//	@Autowired
//	@Qualifier("oauth2ClientContext")
//	OAuth2ClientContext oauth2ClientContext;

	public static void main(String[] args) {
		SpringApplication.run(RoguskiWarehouseApplication.class, args);
	}

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder().encode("user")).roles("USER")
//                .and()
//                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
//                .and()
//                .withUser("admin").password("admin").roles("ADMIN");
//    }
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.antMatcher("/**")
//				.authorizeRequests()
//				.antMatchers("/", "/login**", "/webjars/**", "/error**", "/login", "/regular/login.html", "/static**", "/regular/login", "/api/**/**/**")
//				.permitAll()
//                .anyRequest()
//				.authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/regular/login.html")
//                .defaultSuccessUrl("/api/manufacturers", true)
//                .permitAll()
//				.and().exceptionHandling()
//				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
//				.and().logout().logoutSuccessUrl("/").permitAll()
//				.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
//        //.httpBasic()
//        ;
//	}
//
//	private Filter ssoFilter() {
//
//		OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
//		OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
//		facebookFilter.setRestTemplate(facebookTemplate);
//		UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId());
//		tokenServices.setRestTemplate(facebookTemplate);
//		facebookFilter.setTokenServices(tokenServices);
//		return facebookFilter;
//	}
//
//	@Bean
//	@ConfigurationProperties("facebook.client")
//	public AuthorizationCodeResourceDetails facebook() {
//		return new AuthorizationCodeResourceDetails();
//	}
//
//	@Bean
//	@ConfigurationProperties("facebook.resource")
//	public ResourceServerProperties facebookResource() {
//		return new ResourceServerProperties();
//	}
//
//	@Bean
//	public FilterRegistrationBean oauth2ClientFilterRegistration(
//			OAuth2ClientContextFilter filter) {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(filter);
//		registration.setOrder(-100);
//		return registration;
//	}
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
