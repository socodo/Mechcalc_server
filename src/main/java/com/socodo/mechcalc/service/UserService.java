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

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    private String getCurrentUserEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); 
    }

    private User getCurrentUserEntity() {
        return userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    // USER

    public UserResponse getMyInfo() {
        return userMapper.toResponse(getCurrentUserEntity());
    }

    @Transactional
    public UserResponse updateMyInfo(UserUpdateRequest request) {
        User user = getCurrentUserEntity();

        if (request.getEmail() != null
                && !request.getEmail().equalsIgnoreCase(user.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        userMapper.updateUser(user, request);
        return userMapper.toResponse(userRepository.save(user));
    }

    //AUTH & ADMIN

    @Transactional
    public UserResponse registerUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER"); 

        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public UserResponse createAdmin(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN"); // ĐẶC QUYỀN: Gán quyền ADMIN

        return userMapper.toResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    public UserResponse getUser(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}