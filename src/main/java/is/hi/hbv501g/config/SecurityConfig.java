// package is.hi.hbv501g.config;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .cors().and() // Enable CORS at the security level
//             .csrf().disable() // Disable CSRF for simplicity, adjust based on your app's needs
//             .authorizeRequests()
//             .anyRequest().authenticated(); // Adjust based on your security needs

//         return http.build();
//     }
// }