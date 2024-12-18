package com.duong.identityservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.duong.identityservice.dto.request.UserCreationRequest;
import com.duong.identityservice.dto.response.UserResponse;
import com.duong.identityservice.entity.User;
import com.duong.identityservice.exception.AppException;
import com.duong.identityservice.repository.UserRepository;

@SpringBootTest
@TestPropertySource("/test.properties")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2000, 1, 1);
        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .password("123456")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("28ee99770832")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();

        user = User.builder()
                .id("28ee99770832")
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
                .thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

        // WHEN
        var response = userService.creatUser(request);

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo("28ee99770832");
        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    void createUser_userExists_fail() {
        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
                .thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class, () -> {
            userService.creatUser(request);
        });

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User Existed!");
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_validRequest_success() {
        Mockito.when(userRepository.findByUsername((ArgumentMatchers.anyString())))
                .thenReturn(Optional.of(user));

        // WHEN
        var response = userService.getMyInfo();

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo("28ee99770832");
        Assertions.assertThat(response.getUsername()).isEqualTo("john");
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_userNotFound_fail() {
        Mockito.when(userRepository.findByUsername((ArgumentMatchers.anyString())))
                .thenReturn(Optional.ofNullable(null));

        var exception = assertThrows(AppException.class, () -> {
            userService.getMyInfo();
        });

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User Not Existed!");
    }
}
