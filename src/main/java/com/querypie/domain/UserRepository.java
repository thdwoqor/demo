package com.querypie.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);

    default User getById(final Long userId) {
        return findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }
}
