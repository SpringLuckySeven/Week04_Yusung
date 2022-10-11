package com.sparta.prac01.repository;

import com.sparta.prac01.model.ApiUseTime;
import com.sparta.prac01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}
