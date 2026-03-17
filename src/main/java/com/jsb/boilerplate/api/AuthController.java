package com.jsb.boilerplate.api;

import com.jsb.boilerplate.dto.LoginRequest;
import com.jsb.boilerplate.dto.LoginResponseDto;
import com.jsb.boilerplate.dto.RefreshTokenRequest;
import com.jsb.boilerplate.global.common.ApiResponse;
import com.jsb.boilerplate.global.error.CustomException;
import com.jsb.boilerplate.global.util.JwtUtil;
import com.jsb.boilerplate.mapper.MemberMapper;
import com.jsb.boilerplate.model.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsb.boilerplate.global.error.ErrorCode;

@Tag(name = "Auth API", description = "인증 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Login", description = "로그인 (JWT 토큰 발급)")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequest request) {
        String loginId = request.getLoginId();
        String password = request.getPassword();

        Member member = memberMapper.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtUtil.createAccessToken(member.getLoginId());
        String refreshToken = jwtUtil.createRefreshToken(member.getLoginId());

        memberMapper.updateRefreshToken(member.getLoginId(), refreshToken);

        return ResponseEntity.ok(ApiResponse.success(
                "Login Success",
                LoginResponseDto
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build())
        );
    }

    @Operation(summary = "Refresh Token", description = "액세스토큰 재발급")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Member member = memberMapper.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TOKEN));

        String newAccessToken = jwtUtil.createAccessToken(member.getLoginId());
        String newRefreshToken = jwtUtil.createRefreshToken(member.getLoginId());

        memberMapper.updateRefreshToken(member.getLoginId(), newRefreshToken);

        return ResponseEntity.ok(ApiResponse.success(
                "Token Refreshed",
                LoginResponseDto
                        .builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build())
        );
    }

}
