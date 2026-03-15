package com.jsb.boilerplate.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsb.boilerplate.dto.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("JWT 없이 보호된 API 요청 시 실패해야 한다")
    void testProtectedWithoutJwt() throws Exception {
        mockMvc.perform(get("/api/test/protected"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("JWT와 함께 보호된 API 요청 시 성공해야 한다")
    void testProtectedWithJwt() throws Exception {
        // 1. 회원가입
        MemberRequest joinRequest = new MemberRequest();
        joinRequest.setLoginId("testuser");
        joinRequest.setPassword("password123");
        joinRequest.setName("테스트유저");
        joinRequest.setEmail("test@test.com");

        mockMvc.perform(post("/api/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andExpect(status().isOk());

        // 2. 로그인하여 토큰 획득
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("loginId", "testuser");
        loginRequest.put("password", "password123");

        MvcResult loginResult = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = loginResult.getResponse().getContentAsString();
        String token = objectMapper.readTree(responseBody).path("data").path("token").asText();

        // 3. 토큰과 함께 보호된 API 요청
        mockMvc.perform(get("/api/test/protected")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("JWT Authentication Success! This is a protected message from TestService."))
                .andDo(print());
    }
}
