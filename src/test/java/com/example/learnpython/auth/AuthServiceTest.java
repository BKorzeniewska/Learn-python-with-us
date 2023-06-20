package com.example.learnpython.auth;

import com.example.learnpython.config.JwtService;
import com.example.learnpython.mail.EmailSenderService;
import com.example.learnpython.token.TokenRepository;
import com.example.learnpython.user.exception.UserNotFoundException;
import com.example.learnpython.user.model.entity.Role;
import com.example.learnpython.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private EmailSenderService emailSenderService;


    private AuthenticationService mockAuthenticationService;
    private static final String email = "email@gmail.com";


    @BeforeEach
    void setUp() {
        mockAuthenticationService= new AuthenticationService(repository, tokenRepository, passwordEncoder, jwtService, authenticationManager, emailSenderService);
    }

    @Test
    public void registerTest() {
        AuthenticationService mockAuthenticationService = Mockito.mock(AuthenticationService.class);

        AuthenticationResponse expected = AuthenticationResponse.builder()
                .role(Role.USER)
                .userId(1L)
                .email("tets@o2.pl")
                .nickname("nickname")
                .build();

        Mockito.when(mockAuthenticationService.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(expected);

        AuthenticationResponse response = mockAuthenticationService.register(new RegisterRequest("firstname", "nickname", "lastname", "tets@o2.pl", "test"));

        assertEquals(response.getEmail(), expected.getEmail());
        assertEquals(response.getUserId(), expected.getUserId());
        assertEquals(response.getRole(), expected.getRole());
    }

    @Test
    public void authenticateUserNotFoundTest() {
        String email = "nonexistent@example.com";
        String password = "test123";

        Mockito.when(repository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            mockAuthenticationService.authenticate(new AuthenticationRequest(email, password));
        });
    }







}