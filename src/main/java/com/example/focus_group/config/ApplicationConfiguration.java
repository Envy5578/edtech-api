package com.example.focus_group.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

// import com.example.focusgroup.auth.AuthenticationService;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.focus_group.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public KeyPairGenerator keyPairGenerator() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        return keyGenerator;
    }

    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        return keyPairGenerator().generateKeyPair();
    }

    @Bean
    public Algorithm signInAlgorithm() throws NoSuchAlgorithmException {
        return Algorithm.RSA256((RSAPublicKey) keyPair().getPublic(), (RSAPrivateKey) keyPair().getPrivate());
    }
    // Этот бин использовался, как я понял, для создания моковых пользователей чата
    // @Bean
    // public CommandLineRunner commandLineRunner(
    //         AuthenticationService service,
    //         ChatRoomService chatRoomService,
    //         UserRepository userRepository,
    //         ChatRoomRepository chatRoomRepository
    // ) {
    //     return args -> {
    //         if (userRepository.count() == 0) {
    //             var admin = RegisterRequest.builder()
    //                     .firstname("name")
    //                     .lastname("surname")
    //                     .email("useremail@gmail.com")
    //                     .password("qwerty")
    //                     .role(Role.ADMIN)
    //                     .build();

    //             var manager = RegisterRequest.builder()
    //                     .firstname("John")
    //                     .lastname("Doe")
    //                     .email("adminemail@mail.ru")
    //                     .password("qwerty")
    //                     .role(Role.ADMIN)
    //                     .build();

    //             var student = RegisterRequest.builder()
    //                     .firstname("Ivan")
    //                     .lastname("Ivanov")
    //                     .email("ivanov@mail.ru")
    //                     .password("qwerty")
    //                     .role(Role.USER)
    //                     .build();

    //             System.out.println("User token: " + service.register(admin).getAccessToken());
    //             System.out.println("Admin token: " + service.register(manager).getAccessToken());
    //             System.out.println("Student token: " + service.register(student).getAccessToken());

    //         }

    //         if (chatRoomRepository.count() == 0) {
    //             List<UserDTO> userDTOS = new ArrayList<>();
    //             userDTOS.add(new UserDTO("useremail@gmail.com"));
    //             userDTOS.add(new UserDTO("adminemail@mail.ru"));
    //             chatRoomService.createRoom(userDTOS, "Personal chat", Type.PERSONAL);
    //             chatRoomService.createRoom(userDTOS, "Group chat - WORKERS", Type.GROUP);
    //         }
    //     };
}

