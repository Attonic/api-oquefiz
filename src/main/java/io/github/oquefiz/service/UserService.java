package io.github.oquefiz.service;


import io.github.oquefiz.dto.Request.UserRequestDto;
import io.github.oquefiz.dto.Response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {

    Page<UserResponseDto> findAll(Pageable pageable);

    UserResponseDto findById(UUID uuid);




}
