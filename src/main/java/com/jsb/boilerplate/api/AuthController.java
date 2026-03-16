package com.jsb.boilerplate.api;

import com.jsb.boilerplate.dto.LoginRequest;
import com.jsb.boilerplate.dto.LoginResponseDto;
import com.jsb.boilerplate.global.common.ApiResponse;
import com.jsb.boilerplate.global.error.CustomException;
import com.jsb.boilerplate.global.util.JwtUtil;
import com.jsb.boilerplate.mapper.MemberMapper;
import com.jsb.boilerplate.model.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsb.boilerplate.global.error.ErrorCode;

@Tag(name = "Auth API", description = "Authentication API for login and token management")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequest request) {
        String loginId = request.getLoginId();
        String password = request.getPassword();

        Member member = memberMapper.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtUtil.createToken(member.getLoginId());

        return ResponseEntity.ok(ApiResponse.success(
                "Login Success",
                LoginResponseDto
                        .builder()
                        .token(token)
                        .build())
        );
    }

}
