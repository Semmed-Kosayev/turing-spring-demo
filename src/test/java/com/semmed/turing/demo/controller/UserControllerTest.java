package com.semmed.turing.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semmed.turing.demo.constant.TestConstant;
import com.semmed.turing.demo.model.dto.CreateUserRequest;
import com.semmed.turing.demo.model.dto.UpdateStatusRequest;
import com.semmed.turing.demo.model.dto.UpdateUserRequest;
import com.semmed.turing.demo.model.dto.UserDto;
import com.semmed.turing.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.semmed.turing.demo.constant.TestConstant.getCreateUserRequest;
import static com.semmed.turing.demo.constant.TestConstant.getCreatedUserDto;
import static com.semmed.turing.demo.constant.TestConstant.getStatusRequest;
import static com.semmed.turing.demo.constant.TestConstant.getUpdateUserDto;
import static com.semmed.turing.demo.constant.TestConstant.getUpdateUserDtoInactive;
import static com.semmed.turing.demo.constant.TestConstant.getUpdateUserRequest;
import static com.semmed.turing.demo.constant.TestConstant.getUserDto1;
import static com.semmed.turing.demo.constant.TestConstant.getUserDtoList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    @Test
    void getAll_shouldReturnListOfUserDtos() throws Exception {

        when(service.findAll()).thenReturn(getUserDtoList());

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(TestConstant.getUserDtoList())));
    }

    @Test
    void getById_shouldReturnUserDto_whenIdIsValid() throws Exception {
        when(service.findById(1L)).thenReturn(getUserDto1());

        mockMvc.perform(get("/api/v1/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(getUserDto1())));
    }

    @Test
    void getById_shouldReturnBadRequest_whenIdIsInvalid() throws Exception {
        long invalidUserId = 0L;

        mockMvc.perform(get("/api/v1/users/{id}", invalidUserId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_shouldReturnCreatedUserDto_whenRequestIsValid() throws Exception {
        CreateUserRequest request = getCreateUserRequest();
        UserDto userDto = getCreatedUserDto();

        when(service.create(request)).thenReturn(userDto);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    void update_shouldReturnUpdatedUserDto_whenRequestIsValid() throws Exception {
        long userId = 3L;
        UpdateUserRequest request = getUpdateUserRequest();
        UserDto userDto = getUpdateUserDto();

        when(service.update(userId, request)).thenReturn(userDto);

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    void updateStatus_shouldReturnUpdatedUserDto_whenRequestIsValid() throws Exception {
        long userId = 3L;
        UpdateStatusRequest request = getStatusRequest();
        UserDto userDto = getUpdateUserDtoInactive();

        when(service.updateStatus(userId, request.status())).thenReturn(userDto);

        mockMvc.perform(patch("/api/v1/users/{id}/status", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    void deleteById_shouldReturnNoContent_whenIdIsValid() throws Exception {
        long userId = 1L;

        doNothing().when(service).deleteById(userId);

        mockMvc.perform(delete("/api/v1/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteById_shouldReturnBadRequest_whenIdIsInvalid() throws Exception {
        long invalidUserId = 0L;

        mockMvc.perform(delete("/api/v1/users/{id}", invalidUserId))
                .andExpect(status().isBadRequest());
    }
}