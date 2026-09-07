// package com.ftn.config;


// import com.ftn.config.jwt.JwtRequestFilter;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.Arrays;


// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class WebSecurityConfiguration {

// 	@Autowired
// 	private JwtRequestFilter jwtRequestFilter;

// 	@Bean
// 	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// 		http.csrf().disable().authorizeRequests()
// 				.antMatchers("/api/login").permitAll()
// 				.antMatchers("/api/logout").permitAll()
// 				.antMatchers("/api/register").permitAll()
// //				.requestMatchers("/api/accommodations/searchAccommodations").authenticated()
// 				.and()
// 				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //
// 		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); //

// 		return http.build();
// 	}


// 	@Bean
// 	public PasswordEncoder passwordEncoder() {
// 		PasswordEncoder encoder = new BCryptPasswordEncoder();// PasswordEncoderFactories.createDelegatingPasswordEncoder();
// 		//System.out.println(encoder.encode("admin"));
// //		return NoOpPasswordEncoder.getInstance();
//         return encoder;

// 	}

// 	@Bean
// 	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
// 			throws Exception {
// 		return authenticationConfiguration.getAuthenticationManager();
// 	}

// 	@Bean
// 	public CorsConfigurationSource corsConfigurationSource() {
// 		CorsConfiguration config = new CorsConfiguration();
// 		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://127.0.0.1:4200"));
// 		config.setAllowCredentials(true);
// 		config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
// 		config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
// 		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
// 		source.registerCorsConfiguration("/**", config);
// 		return source;
// 	}
// }