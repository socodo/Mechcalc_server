package com.socodo.mechcalc.service;

import com.socodo.mechcalc.dto.request.UserCreateRequest;
import com.socodo.mechcalc.dto.request.UserUpdateRequest;
import com.socodo.mechcalc.dto.response.UserResponse;
import com.socodo.mechcalc.entity.User;
import com.socodo.mechcalc.exception.AppException;
import com.socodo.mechcalc.exception.ErrorCode;
import com.socodo.mechcalc.mapper.UserMapper;
import com.socodo.mechcalc.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS, "Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponse register(UserCreateRequest request) {
        return createUser(request);
    }

    public List<UserResponse> getUsers() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    public UserResponse getUser(UUID id) {
        return userMapper.toResponse(getUserEntity(id));
    }

    public User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "User not found"));
    }

    @Transactional
    public UserResponse updateUser(UUID id, UserUpdateRequest request) {
        User user = getUserEntity(id);

        if (request.getEmail() != null
                && !request.getEmail().equalsIgnoreCase(user.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS, "Email already exists");
        }

        userMapper.updateUser(user, request);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(UUID id) {
        User user = getUserEntity(id);
        userRepository.delete(user);
    }
}
