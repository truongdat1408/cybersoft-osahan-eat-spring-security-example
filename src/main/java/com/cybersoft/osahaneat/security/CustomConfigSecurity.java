package com.cybersoft.osahaneat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomConfigSecurity {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers("/login/signin")
                    .permitAll()
                    .antMatchers("/menu/files/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
//                .and()
//                .httpBasic(); --> Khong su dung nua
        //Kiem tra filter token truoc
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        UserDetails admin = User.withUsername("admin")
//                .password(encoder.encode("123456"))
//                .authorities("ADMIN")
////                .roles("ADMIN") // t??? ?????ng n?? g???n prefix ROLE_ v??o tr?????c
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password(encoder.encode("123456"))
////                .roles("USER")
//                .authorities("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        //Author: C?? quy???n truy c???p m???t link n??o ???? hay kh??ng
//        //Role: L??c t???o user th?? ph???i s??? d???ng .roles v?? gi?? tr??? c???a role ph???i lu??n c??
//        // prefix: ROLE_t??ng??c??ng???????c
//        // - M?? t??? quy???n cho m??nh l??m m???t ch???c n??ng n??o ????: CRUD...
//        http.csrf().disable()//Chong viec copy token
//                .authorizeRequests()
//                .antMatchers("/login/signin")
//                .permitAll()
////                .antMatchers("/login/signup")
////                    .hasAnyRole("ROLE_ADMIN", "ROLE_USER")
////                .hasAuthority("ADMIN")
//                .anyRequest()
//                .authenticated()
//                .and().httpBasic();
//        return http.build();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
