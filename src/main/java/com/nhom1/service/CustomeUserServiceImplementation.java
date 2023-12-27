package com.nhom1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhom1.model.User;
import com.nhom1.repository.UserRepository;

// Lớp cài đặt UserDetailsService để xử lý tải thông tin người dùng từ cơ sở dữ liệu
@Service
public class CustomeUserServiceImplementation implements UserDetailsService {

    private UserRepository userRepository;
    
    // Constructor chấp nhận một đối tượng UserRepository để thực hiện tìm kiếm người dùng trong cơ sở dữ liệu
    public CustomeUserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Phương thức được ghi đè từ UserDetailsService, thực hiện tìm kiếm người dùng theo tên đăng nhập
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // Tìm kiếm người dùng trong cơ sở dữ liệu dựa trên địa chỉ email (username)
        User user = userRepository.findByEmail(username);
        
        // Nếu không tìm thấy người dùng, ném ngoại lệ UsernameNotFoundException
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với địa chỉ email đã nhập - " + username);
        }
        
        // Tạo danh sách quyền (authorities) rỗng (do không có thông tin về quyền trong đoạn mã này)
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // Trả về một đối tượng UserDetails được tạo ra từ thông tin người dùng trong cơ sở dữ liệu
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
