package com.jsb.boilerplate.mapper;

import com.jsb.boilerplate.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    Optional<Member> findByLoginId(String loginId);

    void save(Member member);
}