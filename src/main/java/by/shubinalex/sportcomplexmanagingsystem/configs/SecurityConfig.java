//package by.shubinalex.sportcomplexmanagingsystem.configs;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
//    private final UserAuthenticationProvider userAuthenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////                http.csrf(CsrfConfigurer:: disable)
////                .authorizeHttpRequests((authz) -> authz
////                        .anyRequest().permitAll());
//
////            http.csrf(CsrfConfigurer:: disable)
////                    .sessionManagement((session) -> session
////                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                    )
////                    .authorizeHttpRequests((authz) -> authz
////                            .requestMatchers(HttpMethod.POST, "/login").permitAll()
////                            .requestMatchers("/serviceEmployees").hasRole(ADMIN.getAuthority())
////                            .anyRequest().authenticated()
////                    )
//
////                   .formLogin(login -> login
////                      .loginPage("/login")
////                           .defaultSuccessUrl("/admin")
////                           .permitAll())
//
////                    .exceptionHandling((ex) -> ex
////                            .authenticationEntryPoint(exceptionHandler)
////                    ).addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        http.csrf(CsrfConfigurer:: disable)
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .exceptionHandling((ex) -> ex
//                            .authenticationEntryPoint(userAuthenticationEntryPoint)
//                    ).addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
//                .sessionManagement((session) -> session
//                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
//                        .anyRequest().authenticated());
//        return http.build();
//    }
//}