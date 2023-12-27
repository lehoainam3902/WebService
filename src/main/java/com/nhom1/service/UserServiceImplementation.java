package com.nhom1.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nhom1.config.JwtProvider;
import com.nhom1.exception.UserExCeption;
import com.nhom1.model.User;
import com.nhom1.repository.UserRepository;

// Implement interface UserService
@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    // Constructor để inject dependencies
    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    // Tìm kiếm người dùng dựa trên ID
    @Override
    public User findUserById(Long userId) throws UserExCeption {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UserExCeption("Không tìm thấy người dùng với mã -" + userId);
    }

    // Tìm kiếm thông tin người dùng dựa trên JWT (JSON Web Token)
    @Override
    public User findUserProfileByJwt(String jwt) throws UserExCeption {
        // Lấy email từ JWT sử dụng JwtProvider
        String email = jwtProvider.getEmailFromToken(jwt);

        // Tìm kiếm người dùng dựa trên email
        User user = userRepository.findByEmail(email);

        // Nếu không tìm thấy, ném ra ngoại lệ UserExCeption
        if (user == null) {
            throw new UserExCeption("Không tồn tại người dùng với email -" + email);
        }
        return user;
    }
}
