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
////                .roles("ADMIN") // tự động nó gắn prefix ROLE_ vào trước
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
//        //Author: Có quyền truy cập một link nào đó hay không
//        //Role: Lúc tạo user thì phải sử dụng .roles và giá trị của role phải luôn có
//        // prefix: ROLE_têngìcũngđược
//        // - Mô tả quyền cho mình làm một chức năng nào đó: CRUD...
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
