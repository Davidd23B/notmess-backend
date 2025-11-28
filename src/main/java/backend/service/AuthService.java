package backend.service;

import backend.dto.LoginRequest;
import backend.dto.RegisterRequest;
import backend.dto.JwtResponse;

public interface AuthService {
    
    JwtResponse login(LoginRequest loginRequest);
    JwtResponse register(RegisterRequest registerRequest);
    void logout(String token);
    boolean isTokenValid(String token);
}
