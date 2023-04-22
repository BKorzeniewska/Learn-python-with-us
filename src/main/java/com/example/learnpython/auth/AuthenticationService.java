package com.example.learnpython.auth;


import com.example.learnpython.config.JwtService;
import com.example.learnpython.token.Token;
import com.example.learnpython.token.TokenRepository;
import com.example.learnpython.token.TokenType;
import com.example.learnpython.user.Role;
import com.example.learnpython.user.User;
import com.example.learnpython.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

      if (repository.findByEmail(request.getEmail()).isPresent()) {
          throw new IllegalArgumentException("Email already exists");
      }

      var user = User.builder()
          .firstname(request.getFirstname())
          .lastname(request.getLastname())
          .email(request.getEmail())
          .password(passwordEncoder.encode(request.getPassword()))
          .level(0)
          .exp(0)
          .role(Role.USER)
          .build();

      var savedUser = repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      saveUserToken(savedUser, jwtToken);

      return AuthenticationResponse.builder()
          .token(jwtToken)
          .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getEmail(),
              request.getPassword()
          )
      );
      var user = repository.findByEmail(request.getEmail())
          .orElseThrow();
      var jwtToken = jwtService.generateToken(user);
      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);
      return AuthenticationResponse.builder()
          .token(jwtToken)
          .build();
    }

    private void saveUserToken(User user, String jwtToken) {
      var token = Token.builder()
          .user(user)
          .token(jwtToken)
          .tokenType(TokenType.BEARER)
          .expired(false)
          .revoked(false)
          .build();
      tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
      var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
      if (validUserTokens.isEmpty())
        return;
      validUserTokens.forEach(token -> {
        token.setExpired(true);
        token.setRevoked(true);
      });
      tokenRepository.saveAll(validUserTokens);
    }
}