package com.nhom1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.config.JwtProvider;
import com.nhom1.exception.UserExCeption;
import com.nhom1.model.Cart;
import com.nhom1.model.User;
import com.nhom1.repository.UserRepository;
import com.nhom1.request.LoginRequest;
import com.nhom1.response.AuthResponse;
import com.nhom1.service.CartService;
import com.nhom1.service.CustomeUserServiceImplementation;

// Controller xử lý các yêu cầu liên quan đến xác thực và ủy quyền
@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomeUserServiceImplementation customeUserService;
    private CartService cartService;
    
    // Constructor để inject các dependency
    public AuthController(UserRepository userRepository,
                          CustomeUserServiceImplementation customeUserService,
                          PasswordEncoder passwordEncoder,
                          JwtProvider jwtProvider,
                          CartService cartService) {
        this.userRepository = userRepository;
        this.customeUserService = customeUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.cartService = cartService;
    }

    // API endpoint để xử lý yêu cầu đăng ký người dùng
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserExCeption {
        // Lấy thông tin từ yêu cầu đăng ký
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        // Kiểm tra xem email đã được sử dụng chưa
        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new UserExCeption("Email đã được sử dụng");
        }

        // Tạo mới người dùng
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        // Lưu người dùng vào cơ sở dữ liệu
        User savedUser = userRepository.save(createdUser);
        Cart cart = cartService.createCart(savedUser);
        
        // Xác thực người dùng mới đăng ký và đặt thông tin vào SecurityContextHolder
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
                savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Tạo JWT từ thông tin xác thực và đặt vào AuthResponse
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Đăng ký thành công");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    // API endpoint để xử lý yêu cầu đăng nhập
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
        // Lấy thông tin đăng nhập từ yêu cầu
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Xác thực thông tin đăng nhập và đặt vào SecurityContextHolder
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Tạo JWT từ thông tin xác thực và đặt vào AuthResponse
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Đăng nhập thành công");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    // Phương thức xác thực thông tin đăng nhập
    private Authentication authenticate(String username, String password) {
        // Lấy thông tin người dùng từ service
        UserDetails userDetails = customeUserService.loadUserByUsername(username);

        // Kiểm tra xem người dùng có tồn tại và mật khẩu có đúng không
        if (userDetails == null) {
            throw new BadCredentialsException("Tên người dùng không hợp lệ");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Mật khẩu không hợp lệ");
        }

        // Trả về đối tượng Authentication
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
