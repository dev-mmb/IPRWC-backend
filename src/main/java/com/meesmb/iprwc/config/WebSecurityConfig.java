package com.meesmb.iprwc.config;

import com.meesmb.iprwc.jwt.CustomAuthenticationManager;
import com.meesmb.iprwc.jwt.JwtAuthenticationEntryPoint;
import com.meesmb.iprwc.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.SecureRandom;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // define urls that don't need a jwt token
    public static final String[] UNSECURED_URLS = {
            "/account/authenticate",
            "/product_image/get/{fileName}",
            "/account/create",
            "/jwt/validate"
    };

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
        //auth.parentAuthenticationManager(this.authenticationManager());
    }

    // needs to be static to avoid cyclic dependency injection for some reason
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new CustomAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().configurationSource(this.corsConfigurationSource()).and()
                .authorizeRequests().antMatchers(UNSECURED_URLS).permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority(RoleName.ADMIN.getValue()).and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/filter_tag").hasAnyAuthority(RoleName.ADMIN.getValue()).and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/filter_group").hasAnyAuthority(RoleName.ADMIN.getValue()).and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/order").hasAnyAuthority(RoleName.ADMIN.getValue()).and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/product").hasAnyAuthority(RoleName.ADMIN.getValue()).and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/product").hasAnyAuthority(RoleName.ADMIN.getValue()).and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/product_image").hasAnyAuthority(RoleName.ADMIN.getValue()).and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
