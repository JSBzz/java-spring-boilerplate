package com.jsb.boilerplate.api;

import com.jsb.boilerplate.global.common.ApiResponse;
import com.jsb.boilerplate.service.TestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test API", description = "JWT Authentication Test API")
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/protected")
    public ResponseEntity<ApiResponse<String>> getProtectedRoute() {
        String message = testService.getTestMessage();
        return ResponseEntity.ok(ApiResponse.success("Success", message));
    }
}
