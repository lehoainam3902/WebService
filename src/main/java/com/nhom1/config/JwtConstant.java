package com.nhom1.config;

// Lớp chứa các hằng số liên quan đến JWT
public class JwtConstant {
	
	// Khóa bí mật được sử dụng để ký và giải mã JWT
	public static final String SECRET_KEY = "fdgfasklfjasklfjasklfsaklfakfsakfas";
	
	// Tên tiêu đề trong request header chứa chuỗi JWT
	public static final String JWT_HEADER = "Authorization";
}
