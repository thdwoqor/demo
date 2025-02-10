package com.querypie.service;

import com.querypie.domain.User;
import com.querypie.domain.UserRepository;
import com.querypie.service.dto.TokenResponse;
import com.querypie.service.dto.UserLoginRequest;
import com.querypie.service.dto.UserRegisterRequest;
import com.querypie.service.dto.UserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public TokenResponse register(final UserRegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
        }
        User user = request.toUser();
        userRepository.save(user);
        return new TokenResponse(jwtUtil.createAccessToken(user));
    }

    //TODO password 암호화 필요
    public TokenResponse login(final UserLoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));
        if (!user.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new TokenResponse(jwtUtil.createAccessToken(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(final Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        return new UserResponse(user.getId(), user.getName());
    }
}
