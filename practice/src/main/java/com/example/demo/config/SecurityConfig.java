package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				//認証なしでアクセス可能に設定
				.authorizeHttpRequests(requests -> requests
						.requestMatchers("/admin/signup", "/admin/signup-regist-confirm", "/signup/confirm",
								"/admin/signup-register", "/admin/signup-complete", "/contact", "/contact/confirm",
								"/contact/register", "/contact/complete", "admin/contacts", "/admin/sign-in",
								"/admin/login", "/admin/contacts/{id}","/admin/contacts/{id}/edit","/admin/contacts/{id}/delete")
						.permitAll() // 認証なしでアクセス可能
						// 他のURLは認証が必要
						.anyRequest().authenticated())
				.formLogin(login -> login
						.loginPage("/admin/signin") // ログインページの指定
						.permitAll())
				.logout(logout -> logout
						.permitAll()); // ログアウトページも認証なしでアクセス可能

		return http.build();
	}
}
