package com.roguskip.roguskiwarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableOAuth2Client
//@EnableWebSecurity
@SpringBootApplication
public class RoguskiWarehouseApplication {

//	@Autowired
//	@Qualifier("oauth2ClientContext")
//	OAuth2ClientContext oauth2ClientContext;


	public static void main(String[] args) {
		SpringApplication.run(RoguskiWarehouseApplication.class, args);
	}


//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.antMatcher("/**")
//				.authorizeRequests()
//				.antMatchers("/", "/login**", "/webjars/**", "/error**", "/login", "/regular/login.html", "/static**", "/regular/login", "/regular/login/**"
//						, "/regular/login*", "/login*"
//				)
//
//				.permitAll()
//                .anyRequest()
//				.authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/regular/login.html")
//				.loginProcessingUrl("/login")
//                .defaultSuccessUrl("/api/manufacturers", true)
//                .permitAll()
//				.and().exceptionHandling()
//				//.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
//				.and().logout().logoutSuccessUrl("/").permitAll()
//				.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//				//.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
//        //.httpBasic()
//        ;
//	}

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


}
