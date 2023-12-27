package com.nhom1.service;

import com.nhom1.exception.UserExCeption;
import com.nhom1.model.User;

public interface UserService {
    
    // Tìm kiếm người dùng dựa trên ID
    public User findUserById(Long userId) throws UserExCeption;
    
    // Tìm kiếm thông tin người dùng dựa trên JWT (JSON Web Token)
    public User findUserProfileByJwt(String jwt) throws UserExCeption;
}
