package com.jsb.boilerplate.api;

import com.jsb.boilerplate.dto.TestDataRequest;
import com.jsb.boilerplate.global.common.ApiResponse;
import com.jsb.boilerplate.model.TestData;
import com.jsb.boilerplate.service.TestDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Test Data API", description = "API for CRUD operations using rowStatus")
@RestController
@RequestMapping("/api/test-data")
@RequiredArgsConstructor
public class TestDataController {

    private final TestDataService testDataService;

    @Operation(summary = "테스트 데이터 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TestData>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Successfully fetched all test data", testDataService.getAllTestData()));
    }

    @Operation(summary = "테스트 데이터 CRUD", description = "rowStatus : I, U, D")
    @PostMapping("/process")
    public ResponseEntity<ApiResponse<Void>> process(@Valid @RequestBody List<TestDataRequest> requests) {
        testDataService.processTestData(requests);
        return ResponseEntity.ok(ApiResponse.success("Successfully processed test data", null));
    }
}
