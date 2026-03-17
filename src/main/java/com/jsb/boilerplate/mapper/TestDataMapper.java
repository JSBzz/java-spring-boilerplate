package com.jsb.boilerplate.mapper;

import com.jsb.boilerplate.model.TestData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface TestDataMapper {
    List<TestData> findAll();
    Optional<TestData> findById(Long id);
    void insert(TestData testData);
    void update(TestData testData);
    void delete(Long id);
}
