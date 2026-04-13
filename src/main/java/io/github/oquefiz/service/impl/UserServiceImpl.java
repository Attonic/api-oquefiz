package io.github.oquefiz.service.impl;

import io.github.oquefiz.dto.Response.UserResponseDto;
import io.github.oquefiz.repository.UserRepository;
import io.github.oquefiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findById(UUID uuid) {
        return userRepository.findById(uuid)
                .map(UserResponseDto::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("Não encontrado"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
