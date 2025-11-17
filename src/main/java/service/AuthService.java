package service;

import dto.LoginRequest;
import dto.RegisterRequest;
import dto.JwtResponse;

public interface AuthService {
    
    JwtResponse login(LoginRequest loginRequest);
    JwtResponse register(RegisterRequest registerRequest);
}
