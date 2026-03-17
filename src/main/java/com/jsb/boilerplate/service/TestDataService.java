package com.jsb.boilerplate.service;

import com.jsb.boilerplate.dto.TestDataRequest;
import com.jsb.boilerplate.global.error.CustomException;
import com.jsb.boilerplate.global.error.ErrorCode;
import com.jsb.boilerplate.mapper.TestDataMapper;
import com.jsb.boilerplate.model.TestData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDataService {

    private final TestDataMapper testDataMapper;

    public List<TestData> getAllTestData() {
        return testDataMapper.findAll();
    }

    @Transactional
    public void processTestData(List<TestDataRequest> requests) {
        for (TestDataRequest request : requests) {
            String status = request.getRowStatus();

            if ("I".equals(status)) {
                TestData data = TestData.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .build();
                testDataMapper.insert(data);
            } else if ("U".equals(status)) {
                if (request.getId() == null) throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
                
                TestData data = TestData.builder()
                        .id(request.getId())
                        .title(request.getTitle())
                        .content(request.getContent())
                        .build();
                testDataMapper.update(data);
            } else if ("D".equals(status)) {
                if (request.getId() == null) throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
                testDataMapper.delete(request.getId());
            } else {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
            }
        }
    }
}
