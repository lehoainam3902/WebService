package com.nhom1.exception;

// Lớp ProductException là một loại ngoại lệ được định nghĩa tùy chỉnh
public class ProductException extends Exception {

    // Constructor với tham số để tạo ra một đối tượng ProductException với thông điệp lỗi cụ thể
    public ProductException(String message) {
        super(message);
    }
}
