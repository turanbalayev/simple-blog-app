package com.turanbalayev.simpleblogapp.service;

import com.turanbalayev.simpleblogapp.payload.LoginDto;
import com.turanbalayev.simpleblogapp.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
