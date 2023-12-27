package com.nhom1.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Lớp cung cấp phương thức để tạo và xác thực JSON Web Tokens (JWT)
@Service
public class JwtProvider {
	
	// Khóa bí mật được sử dụng cho việc ký và giải mã JWT
	private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	// Phương thức tạo JWT từ thông tin xác thực
	public String generateToken(Authentication auth) {
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 846000000)) // Thiết lập thời gian hết hạn của token (10 ngày)
				.claim("email", auth.getName()) // Thêm thông tin (claims) vào JWT, trong trường hợp này là email
				.signWith(key).compact(); // Ký JWT bằng khóa bí mật
				
		return jwt;
	}
	
	// Phương thức xác định email từ JWT
	public String getEmailFromToken(String jwt) {
		// Bỏ đi tiền tố "Bearer " trong chuỗi JWT
		jwt = jwt.substring(7);
		
		// Giải mã và lấy thông tin từ JWT
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		
		// Lấy giá trị của claim "email"
		String email = String.valueOf(claims.get("email"));
		
		return email;
	}
}
