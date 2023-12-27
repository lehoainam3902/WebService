package com.nhom1.exception;

// Lớp UserExCeption là một loại ngoại lệ được định nghĩa tùy chỉnh
public class UserExCeption extends Exception {

    // Constructor với tham số để tạo ra một đối tượng UserExCeption với thông điệp lỗi cụ thể
    public UserExCeption(String message) {
        super(message);
    }
}
