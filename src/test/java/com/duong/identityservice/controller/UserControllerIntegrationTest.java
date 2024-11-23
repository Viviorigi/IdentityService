package com.duong.identityservice.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.duong.identityservice.dto.request.UserCreationRequest;
import com.duong.identityservice.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerIntegrationTest {

    @Container
    static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0.30");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> MYSQL_CONTAINER.getJdbcUrl() + "?useSSL=false");
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQLDialect");
    }

    @Autowired
    private MockMvc mockMvc;

    private UserCreationRequest request;

    private UserResponse userResponse;

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
    }

    @Test
    // Test CreateUser success
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String content = mapper.writeValueAsString(request);

        // WHEN, THEN
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("john"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.lastName").value("Doe"));

        log.info("Result: {}", response.andReturn().getResponse().getContentAsString());
    }
}
