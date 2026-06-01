package com.librework.modules.identity.controller;

import com.librework.common.enums.ProfileType;
import com.librework.common.enums.UserStatus;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.dto.response.UserResponse;
import com.librework.modules.identity.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    private UserCreationRequest request;
    private UserResponse response;

    @BeforeEach
    void initData() {
        request = UserCreationRequest.builder()
                .email("min@gmail.com")
                .accountType(ProfileType.FREELANCER)
                .userName("Minh")
                .password("123456")
                .fullName("Minh Nguyen")
                .build();

        response = UserResponse.builder()
                .fullName("Minh Nguyen")
                .email("Minh@gmail.com")
                .status(UserStatus.ACTIVE)
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .username("Minh")
                .build();
    }

    @Test
    void register_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(authService.register(any()))
                .thenReturn(response);
        //WHEN
        //Taoj request
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))

        //THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("success").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("data.id").value("123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    void register_usernameInvalid_fail() throws Exception {
        //GIVEN
        request.setUserName("Jo");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("success").value("false"));
    }
}
