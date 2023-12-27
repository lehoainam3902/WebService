//Lớp cấu hình Spring trong Java, định nghĩa cài đặt bảo mật, CORS, và tạo bean để mã hóa mật khẩu.
package com.nhom1.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {
	
	// Bean để cấu hình bảo mật
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		// Cấu hình quản lý session và chính sách tạo session (STATELESS)
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		
		// Phân quyền truy cập cho các đường dẫn API, yêu cầu xác thực cho /api/**
		.authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
		
		// Thêm Filter để kiểm tra và xác thực JWT (Json Web Token)
		.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
		
		// Vô hiệu hóa CSRF (Cross-Site Request Forgery) bảo vệ
		.csrf().disable()
		
		// Cấu hình CORS (Cross-Origin Resource Sharing)
		.cors().configurationSource(new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

				CorsConfiguration cfg = new CorsConfiguration();
				
				// Thiết lập các nguồn gốc được cho phép
				cfg.setAllowedOriginPatterns(Arrays.asList(
					"http://localhost:3000",
					"http://localhost:4200"
					));
				
				// Thiết lập các phương thức được phép
				cfg.setAllowedMethods(Collections.singletonList("*"));
				
				// Cho phép gửi thông tin xác thực (credentials)
				cfg.setAllowCredentials(true);
				
				// Thiết lập các tiêu đề được phép
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				
				// Thiết lập các tiêu đề mà trình duyệt có thể truy cập
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				
				// Thiết lập thời gian sống của CORS Preflight request
				cfg.setMaxAge(3600L);
				return cfg;
			}
		})
		
		// Cấu hình xác thực cơ bản và đăng nhập thông qua form
		.and().httpBasic().and().formLogin();
		
		// Trả về SecurityFilterChain được cấu hình
		return http.build();	
	}
	
	// Bean để cung cấp PasswordEncoder sử dụng thuật toán BCrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
