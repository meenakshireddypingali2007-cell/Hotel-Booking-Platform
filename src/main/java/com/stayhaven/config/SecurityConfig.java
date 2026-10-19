package com.stayhaven.config;
import com.stayhaven.repo.AccountRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService userDetailsService(AccountRepository accounts){return email->accounts.findByEmail(email.toLowerCase()).map(a->User.withUsername(a.getEmail()).password(a.getPassword()).roles(a.getRole()).disabled(!a.isEnabled()).build()).orElseThrow(()->new UsernameNotFoundException("Account not found"));}
 @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{return http.authorizeHttpRequests(a->a.requestMatchers("/","/hotels/**","/register","/login","/admin/login","/css/**","/js/**","/images/**","/api/rooms/**","/api/availability").permitAll().requestMatchers("/admin/**","/h2-console/**","/api/admin/**").hasRole("ADMIN").requestMatchers(org.springframework.http.HttpMethod.POST,"/api/bookings/**").hasRole("USER").requestMatchers("/dashboard","/bookings/**","/profile/**","/api/**").hasAnyRole("USER","ADMIN").anyRequest().permitAll()).formLogin(f->f.loginPage("/login").successHandler((req,res,auth)->res.sendRedirect(auth.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN"))?"/admin":"/dashboard")).permitAll()).logout(l->l.logoutSuccessUrl("/").permitAll()).csrf(c->c.ignoringRequestMatchers("/h2-console/**")).headers(h->h.frameOptions(f->f.sameOrigin())).build();}
}
