package com.turanbalayev.simpleblogapp.service.impl;

import com.turanbalayev.simpleblogapp.entity.Role;
import com.turanbalayev.simpleblogapp.entity.User;
import com.turanbalayev.simpleblogapp.exception.BlogAPIException;
import com.turanbalayev.simpleblogapp.payload.LoginDto;
import com.turanbalayev.simpleblogapp.payload.RegisterDto;
import com.turanbalayev.simpleblogapp.repository.RoleRepository;
import com.turanbalayev.simpleblogapp.repository.UserRepository;
import com.turanbalayev.simpleblogapp.security.JwtTokenProvider;
import com.turanbalayev.simpleblogapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider
                           )
    {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username is already exist.");
        }

        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"This email is already exist.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role user_role = roleRepository.findByName("ROLE_USER").get();
        roles.add(user_role);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }
}
