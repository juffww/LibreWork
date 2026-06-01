package com.librework.modules.identity.service.impl;

import com.librework.common.enums.ProfileType;
import com.librework.common.enums.UserStatus;
import com.librework.exception.AppException;
import com.librework.modules.identity.dto.request.UserCreationRequest;
import com.librework.modules.identity.dto.response.UserResponse;
import com.librework.modules.identity.entity.User;
import com.librework.modules.identity.repository.UserRepository;
import com.librework.modules.identity.service.impl.AuthServiceImpl;
import com.librework.modules.profile.service.impl.ProfileInitializationService;
import com.librework.modules.identity.service.impl.UserSettingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class AuthServiceImplTest {

    @Autowired
    private AuthServiceImpl authService;

    @MockitoBean
    private UserRepository repository;

    @MockitoBean
    private ProfileInitializationService profileInitializationService;

    @MockitoBean
    private UserSettingServiceImpl userSettingService;

    private User user;
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

        user = User.builder()
                .id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .fullName("Minh Nguyen")
                .email("min@gmail.com")
                .username("Minh")
                .status(UserStatus.ACTIVE)
                .build();
    }

    @Test
    void register_validRequest_success()
    {
        //GIVEN
        Mockito.when(repository.existsByEmail(anyString()))
                .thenReturn(false); // giả lập luôn trả về false

        Mockito.when(repository.existsByUsername(anyString()))
                .thenReturn(false);

        Mockito.when(repository.save(any()))
                .thenReturn(user);

        Mockito.doNothing().when(profileInitializationService).initializeForNewUser(any(), any());
        Mockito.doNothing().when(userSettingService).initDefaultSetting(any(), any());

        //when: run register
        var response = authService.register(request);

        //Then
        assertThat(response.getId()).isEqualTo(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        assertThat(response.getUsername()).isEqualTo("Minh");
    }

    @Test
    void register_userExisted_fail()
    {
        //GIVEN
        Mockito.when(repository.existsByEmail(anyString()))
                .thenReturn(true);

        var exception = assertThrows(AppException.class,
                () -> authService.register(request));

        assertThat(exception.getErrorCode().getMessage()).isEqualTo("Email is exited");
    }
}
