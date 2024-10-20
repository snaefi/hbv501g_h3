// package is.hi.hbv501g.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// import static org.springframework.security.config.Customizer.withDefaults;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable()) // Disable CSRF if not needed
//             .authorizeHttpRequests(authz -> authz
//                 .requestMatchers(HttpMethod.GET, "/**/**").permitAll() // Allow GET requests to /users/** without authentication
//                 .requestMatchers(HttpMethod.POST, "/**").permitAll() // Allow POST to /users without authentication
// 				.requestMatchers(HttpMethod.PATCH, "/**").permitAll() // Allow POST to /users without authentication
// 				.requestMatchers(HttpMethod.DELETE, "/**/**").permitAll() // Allow POST to /users without authentication
// 				.requestMatchers(HttpMethod.GET, "/**").permitAll() // Allow POST to /users without authentication
//                 .anyRequest().authenticated() // All other equests require authentication
//             )
//             .httpBasic(withDefaults()) // Use basic authentication
// 			.formLogin(form -> form.disable()); // Disable the default login form

        
//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     // Add userDetailsService or other beans if necessary
// }