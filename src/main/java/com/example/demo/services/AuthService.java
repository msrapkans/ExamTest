package com.example.demo.services;

import com.example.demo.dtos.UserDto;

public interface AuthService {
    UserDto registerUser(UserDto userDto);
}

