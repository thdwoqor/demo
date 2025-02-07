package com.querypie.service;

import com.querypie.domain.User;
import com.querypie.domain.UserRepository;
import com.querypie.service.dto.UserResponse;
import com.querypie.service.dto.UserSaveRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(final UserSaveRequest request) {
        userRepository.save(new User(request.name()));
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
