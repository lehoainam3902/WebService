package com.nhom1.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Lớp kiểm tra và xác thực JSON Web Tokens (JWT)
public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Lấy chuỗi JWT từ header của request
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		
		// Nếu JWT tồn tại
		if (jwt != null) {
			// Bỏ đi tiền tố "Bearer " trong chuỗi JWT
			jwt = jwt.substring(7);
			
			try {
				// Sử dụng khóa bí mật để giải mã JWT và lấy thông tin từ claims
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				// Lấy thông tin email từ claims
				String email = String.valueOf(claims.get("email"));
				
				// Lấy thông tin authorities từ claims
				String authorities = String.valueOf(claims.get("authorities"));
				
				// Chuyển đổi chuỗi authorities thành danh sách GrantedAuthority
				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				// Tạo đối tượng Authentication từ thông tin xác thực
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				
				// Đặt thông tin xác thực vào SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				// Nếu có lỗi trong quá trình xác thực, ném ra ngoại lệ BadCredentialsException
				throw new BadCredentialsException("Invalid token... from JwtValidator");
			}
		}
		
		// Chuyển tiếp yêu cầu đến các bộ lọc tiếp theo trong chuỗi bộ lọc
		filterChain.doFilter(request, response);
	}
}
